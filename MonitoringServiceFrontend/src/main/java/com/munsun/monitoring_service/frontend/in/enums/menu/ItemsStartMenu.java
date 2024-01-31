package com.munsun.monitoring_service.frontend.in.enums.menu;

/**
 * Enumeration for displaying menu items displayed at application start
 *
 * @author apple
 * @version $Id: $Id
 */
public enum ItemsStartMenu{
    LOGIN("Login"),
    REGISTER("Register");

    private String title;

    ItemsStartMenu(String title) {
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
