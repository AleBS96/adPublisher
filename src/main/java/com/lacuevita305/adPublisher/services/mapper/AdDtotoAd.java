package com.lacuevita305.adPublisher.services.mapper;

import com.lacuevita305.adPublisher.enums.PublishStatus;
import com.lacuevita305.adPublisher.persistence.entities.Ad;
import com.lacuevita305.adPublisher.services.dto.AdDto;
import org.springframework.stereotype.Component;


@Component
public class AdDtotoAd implements MapperInterface <AdDto, Ad> {
    @Override
    public Ad map(AdDto in) {

        Ad ad = new Ad();

       ad.setPrice(in.getPrice());
       ad.setCurrency(in.getCurrency());
       ad.setTitle(in.getTitle());
       ad.setBody(in.getBody());
       ad.setCategory("");
       ad.setPublishStatus(PublishStatus.NOT_PUBLISHED);

       return ad;
    }
}
