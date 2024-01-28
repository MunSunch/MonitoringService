package com.munsun.monitoring_service.backend.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * An entity for storing meter readings
 */
public class MeterReading {
    private Long id;
    private Date date;
    private Map<String, Long> readings;
    private Account account;

    public MeterReading(Long id, Date date, Map<String, Long> readings, Account account) {
        this.id = id;
        this.date = date;
        this.readings = readings;
        this.account = account;
    }

    public MeterReading(Date date) {
        this();
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeterReading() {
        this.readings = new HashMap<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Long> getReadings() {
        return readings;
    }

    public void setReadings(Map<String, Long> readings) {
        this.readings = readings;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
