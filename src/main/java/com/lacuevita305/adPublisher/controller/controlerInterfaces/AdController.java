package com.lacuevita305.adPublisher.controller.controlerInterfaces;

import com.lacuevita305.adPublisher.services.dto.PublishAdRequest;
import org.springframework.http.ResponseEntity;

public interface AdController {

    public ResponseEntity<?> loadAllAdsFromFileByProduct (String product) throws Exception;
    public ResponseEntity<?> loadAllAdsFromFile () throws Exception;
    public ResponseEntity<?> publishAd(PublishAdRequest request) throws Exception;

}
