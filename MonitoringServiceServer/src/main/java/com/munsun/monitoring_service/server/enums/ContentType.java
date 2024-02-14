package com.munsun.monitoring_service.server.enums;

public enum ContentType {
    JSON("application/json");

    private String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
