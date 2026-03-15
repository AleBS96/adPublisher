package com.lacuevita305.adPublisher.enums;

public enum GeneralUseEnum {

    PATH(""),
    PIPE("|");


    private final String value;

    GeneralUseEnum(String value) {
        this.value = value;
    }

    public String getIdentificador(){
        return this.value;
    }
}
