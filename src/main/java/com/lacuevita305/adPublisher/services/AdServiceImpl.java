package com.lacuevita305.adPublisher.services;

import com.lacuevita305.adPublisher.enums.GeneralUseEnum;
import com.lacuevita305.adPublisher.enums.PublishStatus;
import com.lacuevita305.adPublisher.exception.UserException;
import com.lacuevita305.adPublisher.persistence.entities.Ad;
import com.lacuevita305.adPublisher.persistence.repositories.AdRepository;
import com.lacuevita305.adPublisher.services.dto.AdDTO;
import com.lacuevita305.adPublisher.services.dto.CategoryDTO;
import com.lacuevita305.adPublisher.services.dto.PublishAdRequest;
import com.lacuevita305.adPublisher.services.dto.User;
import com.lacuevita305.adPublisher.services.mapper.AdDtotoAd;
import com.lacuevita305.adPublisher.services.serviceInterfaces.AdService;
import com.lacuevita305.adPublisher.utils.Utils;

import com.lacuevita305.adPublisher.utils.UtilsHumanActions;
import com.lacuevita305.adPublisher.utils.UtilsRevolico;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Service
@Getter
public class AdServiceImpl extends BaseServiceImpl <Ad, Long> implements AdService {

    private final AdRepository adRepository;
    private final AdDtotoAd mapper;
    private final AdManagerClientService adManagerClientService;
    private WebDriver webDriver;

    public AdServiceImpl(AdRepository adRepository, AdDtotoAd mapper, AdManagerClientService adManagerClientService){
        super(adRepository);
        this.adRepository = adRepository;
        this.mapper = mapper;
        this.adManagerClientService = adManagerClientService;
    }

    @Override
    public String loadAllAdsFromFileByProduct(String product) throws Exception {
        long loadedAds = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(GeneralUseEnum.RESOURCES_FOLDER_PATH.getValue() + product + "/Anuncios.txt"))) {

            CsvToBean<AdDTO> csvToBean = new CsvToBeanBuilder<AdDTO>(reader)
                    .withType(AdDTO.class)
                    .withSeparator('|')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (AdDTO dto : csvToBean) {

                if (!adRepository.existsByTitleAndBody(dto.getTitle(), dto.getBody())) {
                    Ad ad = mapper.map(dto);
                    ad.setProductName(product);
                    adRepository.save(ad);
                    loadedAds ++;
                }
            }
        }
        return "Anuncios cargados " + loadedAds;
    }

    @Override
    public List<String> loadAllAdsFromFile() throws Exception {
        long loadedAds = 0;
        List<String> loadedAdsByProduct = new ArrayList<>();
        File carpetaRaiz = new File(GeneralUseEnum.RESOURCES_FOLDER_PATH.getValue());

        if (!carpetaRaiz.exists() || !carpetaRaiz.isDirectory()) {
            throw new RuntimeException("La carpeta resources no existe");
        }

        for (File subCarpeta : Objects.requireNonNull(carpetaRaiz.listFiles())) {
            if (subCarpeta.isDirectory()) {

                File archivoAnuncio = new File(subCarpeta, "Anuncios.txt");
                if (archivoAnuncio.exists() && archivoAnuncio.isFile()) {
                    String product = subCarpeta.getName();
                    try (Reader reader = Files.newBufferedReader(Paths.get(GeneralUseEnum.RESOURCES_FOLDER_PATH.getValue() + product + "/Anuncios.txt"))) {

                        CsvToBean<AdDTO> csvToBean = new CsvToBeanBuilder<AdDTO>(reader)
                                .withType(AdDTO.class)
                                .withSeparator('|')
                                .withIgnoreLeadingWhiteSpace(true)
                                .build();

                        for (AdDTO dto : csvToBean) {

                            if (!adRepository.existsByTitleAndBody(dto.getTitle(), dto.getBody())) {
                                Ad ad = mapper.map(dto);
                                ad.setProductName(product);
                                adRepository.save(ad);
                                loadedAds ++;
                            }
                        }
                        loadedAdsByProduct.add("Anuncios cargados de " + product + ": " + loadedAds);
                        loadedAds = 0;
                    }
                }
            }
        }
        return loadedAdsByProduct;
    }

    @Override
    public List<Ad> publishAd(PublishAdRequest request) throws Exception {
        List<Ad> adsToPublish = List.of();
        List<String> usersName = Optional.ofNullable(request.getUserName())
                .orElse(Collections.emptyList());

        if (usersName.isEmpty()){
            Optional<List<User>> users = this.adManagerClientService.getUsers(GeneralUseEnum.GET_USERS_ENDPOINT_URL.getValue());
            if(users.isPresent()){
                usersName = users.get().stream().map(User::getName).toList();
            }
        }
        for (String userName:usersName) {
            Optional<User> user = this.adManagerClientService.findUserByName(GeneralUseEnum.GET_USER_ENDPOINT_URL.getValue(), userName);
            if (user.isEmpty()) {
                throw new UserException("El usuario para el que se intentan cargar los anuncios no existe", HttpStatus.NOT_FOUND);
            } else {
                try {
                    webDriver = Utils.initWebDriver(user.get().getPort(), user.get().getName());
                    Pageable limit = PageRequest.of(0, request.getAdsToPublishCount());

                    List<String> productsName = Optional.ofNullable(request.getProducts())
                            .orElse(Collections.emptyList());

                    if (productsName.isEmpty()) {
                        Optional<List<CategoryDTO>> categories = this.adManagerClientService.getCategories(GeneralUseEnum.GET_CATEGORIES_ENDPOINT_URL.getValue());
                        if (categories.isPresent()) {
                            productsName = categories.get().stream().map(CategoryDTO::getName).toList();
                        }
                    }

                    for (String product : productsName) {
                        adsToPublish = adRepository.findByProductNameAndPublishStatus(product,PublishStatus.NOT_PUBLISHED.getValue(), limit);
                        int adsPublichedCount = 0;
                        int adsToPublishCount = request.getAdsToPublishCount();

                        if (adsToPublish.size() < adsToPublishCount) {
                            adsToPublishCount = adsToPublish.size();
                        }

                        while (adsPublichedCount < adsToPublishCount) {
                            Ad adToPublish = adsToPublish.get(adsPublichedCount);

                            if (UtilsHumanActions.openLinkInNewWindowAsHuman(this.webDriver, GeneralUseEnum.REVOLICO_PUBLISH_PATH.getValue())) {
                                Utils.closeOldWindows(webDriver);

                                if (UtilsRevolico.publishAd(webDriver, adToPublish, request.getRevolicoCategory())) {
                                    adRepository.updatePublishStatusById(adToPublish.getId(), PublishStatus.PUBLISHED.getValue());
                                }
                            }

                            adsPublichedCount++;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return adsToPublish;
    }

}

