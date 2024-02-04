package com.munsun.utils.logger.dao.impl.enums;

public enum NamesColumnsTableJournals {
    ID("id"),
    DATE("date"),
    MESSAGE("message");

    private String title;

    NamesColumnsTableJournals(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
