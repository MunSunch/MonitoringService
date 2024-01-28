package com.munsun.monitoring_service.frontend.in.enums.menu;

/**
 * Enumeration for displaying menu items displayed at application start
 */
public enum ItemsStartMenu{
    LOGIN("Login"),
    REGISTER("Register");

    private String title;

    ItemsStartMenu(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
