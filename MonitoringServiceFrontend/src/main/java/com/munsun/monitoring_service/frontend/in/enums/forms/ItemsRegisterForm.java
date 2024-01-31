package com.munsun.monitoring_service.frontend.in.enums.forms;

/**
 * Enumeration for requesting data entry for registration of a new user
 *
 * @author apple
 * @version $Id: $Id
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

    /**
     * <p>Getter for the field <code>title</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getTitle() {
        return title;
    }
}
