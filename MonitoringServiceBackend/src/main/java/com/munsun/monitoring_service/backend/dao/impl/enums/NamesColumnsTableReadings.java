package com.munsun.monitoring_service.backend.dao.impl.enums;

/**
 * Column names of the table readings
 */
public enum NamesColumnsTableReadings {
    ID("id"),
    METER_READINGS_ID("meter_readings_id"),
    NAME("name"),
    VALUE("value");

    private String title;

    NamesColumnsTableReadings(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
