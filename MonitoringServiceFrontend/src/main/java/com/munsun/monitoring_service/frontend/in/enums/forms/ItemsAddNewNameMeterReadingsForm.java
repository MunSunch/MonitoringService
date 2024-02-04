package com.munsun.monitoring_service.frontend.in.enums.forms;

/**
 * <p>ItemsAddNewNameMeterReadingsForm class.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
public enum ItemsAddNewNameMeterReadingsForm {
    FIELD_NAME("Название");

    private String title;

    ItemsAddNewNameMeterReadingsForm(String title) {
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
