package com.lacuevita305.adPublisher.enums;

import lombok.Getter;

@Getter
public enum PublishStatus {
    PUBLISHED ("Published"),
    NOT_PUBLISHED ("Not Published");

    private final String value;

    PublishStatus(String value) {
        this.value = value;
    }

}
