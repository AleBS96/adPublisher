package com.lacuevita305.adPublisher.services.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublishAdRequest {
    private List<String> products;
    private String revolicoCategory;
    private List<String> userName;
    private int adsToPublishCount;
}
