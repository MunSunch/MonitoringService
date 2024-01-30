package com.munsun.monitoring_service.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * An entity for storing meter readings
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class MeterReading {
    private Long id;
    private Date date;
    private Map<String, Long> readings;
    private Account account;
}