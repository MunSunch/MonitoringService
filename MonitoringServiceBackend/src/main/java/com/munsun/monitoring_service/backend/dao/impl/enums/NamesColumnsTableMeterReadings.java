package com.munsun.monitoring_service.backend.dao.impl.enums;

/**
 * Column names of the table meter readings
 */
public enum NamesColumnsTableMeterReadings {
    ID("id"),
    DATE("date"),
    ACCOUNT_ID("account_id");

    private String title;

    NamesColumnsTableMeterReadings(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
