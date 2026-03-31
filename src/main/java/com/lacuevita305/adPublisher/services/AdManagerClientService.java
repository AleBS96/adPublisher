package com.lacuevita305.adPublisher.services;

import com.lacuevita305.adPublisher.enums.GeneralUseEnum;
import com.lacuevita305.adPublisher.services.dto.CategoryDTO;
import com.lacuevita305.adPublisher.services.dto.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class AdManagerClientService {
    private final WebClient webClient;

    public AdManagerClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(GeneralUseEnum.ADMANAGER_BASE_URL.getValue()).build();
    }

    public Optional<User> findUserByName(String endpointUrl, String username) {

        User user = webClient
                .get()
                .uri(endpointUrl, username)
                .retrieve()
                .bodyToMono(User.class)
                .block();

        return Optional.ofNullable(user);
    }

    public Optional<List<CategoryDTO>> getCategories(String endpointUrl) {

        List<CategoryDTO> categories = webClient
                .get()
                .uri(endpointUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryDTO>>() {})
                .block();

        return Optional.ofNullable(categories);
    }


    public Optional<List<User>> getUsers(String endpointUrl) {
        List<User> users = webClient
                .get()
                .uri(endpointUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {})
                .block();

        return Optional.ofNullable(users);
    }
}
