package com.munsun.monitoring_service.frontend.in.enums.forms;

/**
 * Enumeration for requesting data entry for registration of a new user
 */
public enum ItemsRegisterForm {
    FIELD_LOGIN("login"),
    FIELD_PASSWORD("password"),
    FIELD_COUNTRY("country"),
    FIELD_CITY("city"),
    FIELD_STREET("street"),
    FIELD_HOUSE("house"),
    FIELD_LEVEL("level"),
    FIELD_APARTMENT_NUMBER("apartmentNumber");

    private String title;

    ItemsRegisterForm(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
