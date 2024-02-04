package com.munsun.monitoring_service.frontend.in.enums.forms;


/**
 * Enumeration for requesting data entry to add a new record
 *
 * @author apple
 * @version $Id: $Id
 */
public enum ItemsAddMeterReadingsForm {
    FIELD_PASSWORD("password"),
    FIELD_COUNTRY("country"),
    FIELD_CITY("city"),
    FIELD_STREET("street"),
    FIELD_HOUSE("house"),
    FIELD_LEVEL("level"),
    FIELD_APARTMENT_NUMBER("apartmentNumber");

    private String title;

    ItemsAddMeterReadingsForm(String title) {
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
