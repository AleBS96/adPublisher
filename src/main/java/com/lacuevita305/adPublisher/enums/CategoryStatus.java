package com.lacuevita305.adPublisher.enums;

import lombok.Getter;

@Getter
public enum CategoryStatus {
    AVAILABLE("AVAILABLE"),
    OUT_OF_STOCK("OUT_OF_STOCK");

    private final String value;

    CategoryStatus(String value) {
        this.value = value;
    }

}
