package com.lacuevita305.adPublisher.services.serviceInterfaces;

import com.lacuevita305.adPublisher.persistence.entities.Ad;
import com.lacuevita305.adPublisher.services.dto.PublishAdRequest;

import java.util.List;

public interface AdService extends BaseService<Ad, Long> {

    public String loadAllAdsFromFileByProduct (String product) throws Exception;
    public List<Ad> publishAd (PublishAdRequest request) throws Exception;
    public List<String> loadAllAdsFromFile() throws Exception;

}
