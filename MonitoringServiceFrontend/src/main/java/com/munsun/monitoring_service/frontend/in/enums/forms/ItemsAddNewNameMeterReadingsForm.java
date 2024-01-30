package com.munsun.monitoring_service.frontend.in.enums.forms;

public enum ItemsAddNewNameMeterReadingsForm {
    FIELD_NAME("Название");

    private String title;

    ItemsAddNewNameMeterReadingsForm(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
