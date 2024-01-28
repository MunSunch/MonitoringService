package com.munsun.utils.logger.model;

import java.util.Date;

/**
 * The essence for storing the service information of user actions is more necessary for specialists who are not related to software development
 */
public class JournalRecord {
    private Long id;
    private Date date;
    private String message;

    public JournalRecord(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    public JournalRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
