package com.lacuevita305.adPublisher.enums;

public enum WebElementsEnum {
    BUTTON("button"),
    INPUT("input"),
    PUBLISH_BUTTON_TEXT("Publicar anuncio"),
    PRICE_INPUT_ID("price"),
    TITLE_INPUT_ID("title"),
    BODY_INPUT_ID("description"),
    CATEGORY_SELECTOR_ID("category-selector");


    private final String value;

    WebElementsEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
