package com.munsun.monitoring_service.backend.dao.impl.enums;

/**
 * Column names of the table accounts
 */
public enum NamesColumnsTableAccounts {
    ID("id"),
    LOGIN("login"),
    PASSWORD("password"),
    ROLE("role"),
    IS_BLOCKED("is_blocked"),
    COUNTRY("country"),
    CITY("city"),
    STREET("street"),
    HOUSE("house"),
    LEVEL("level"),
    APARTMENT_NUMBER("apartmentnumber");

    private String title;

    NamesColumnsTableAccounts(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
