package com.lacuevita305.adPublisher.enums;

public enum GeneralUseEnum {
    REVOLICO_PUBLISH_PATH("https://www.revolico.com/item/publish"),
    RESOURCES_FOLDER_PATH("../resources/"),
    ADMANAGER_BASE_URL("http://localhost:8000"),
    GET_USER_ENDPOINT_URL("api/v1/users/{username}"),
    GET_CATEGORIES_ENDPOINT_URL("api/v1/categories"),
    GET_USERS_ENDPOINT_URL("api/v1/users");

    private final String value;

    GeneralUseEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
