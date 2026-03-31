package com.lacuevita305.adPublisher.controller;

import com.lacuevita305.adPublisher.controller.controlerInterfaces.AdController;
import com.lacuevita305.adPublisher.services.dto.PublishAdRequest;
import com.lacuevita305.adPublisher.services.serviceInterfaces.AdService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Getter
@Setter
@RestController
@RequestMapping("api/v1/ads")
public class AdControllerImpl implements AdController {

    private final AdService adService;

    public AdControllerImpl(AdService adService) {
        this.adService = adService;
    }

    @PutMapping("/load/{products}")
    public ResponseEntity<?> loadAllAdsFromFileByProduct (@PathVariable("products") String product) throws Exception {
        return ResponseEntity.ok().body(this.adService.loadAllAdsFromFileByProduct(product));
    }

    @PutMapping("/load/")
    public ResponseEntity<?> loadAllAdsFromFile () throws Exception {
        return ResponseEntity.ok().body(this.adService.loadAllAdsFromFile());
    }

    @PatchMapping("/publish")
    @Override
    public ResponseEntity<?> publishAd(@RequestBody PublishAdRequest request) throws Exception {
        return ResponseEntity.ok().body(this.adService.publishAd(request));
    }
}
