package com.munsun.monitoring_service.frontend.in.enums.forms;


/**
 * Enumeration for requesting data entry for user authentication
 */
public enum ItemsLoginForm {
    FIELD_LOGIN("login"),
    FIELD_PASSWORD("password");

    private String title;

    ItemsLoginForm(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
