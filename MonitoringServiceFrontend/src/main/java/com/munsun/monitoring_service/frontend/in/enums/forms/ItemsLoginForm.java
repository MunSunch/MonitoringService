package com.munsun.monitoring_service.frontend.in.enums.forms;


/**
 * Enumeration for requesting data entry for user authentication
 *
 * @author apple
 * @version $Id: $Id
 */
public enum ItemsLoginForm {
    FIELD_LOGIN("login"),
    FIELD_PASSWORD("password");

    private String title;

    ItemsLoginForm(String title) {
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
