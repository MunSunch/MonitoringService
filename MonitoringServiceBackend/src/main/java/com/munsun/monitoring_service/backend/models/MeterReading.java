package com.munsun.monitoring_service.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Map;

/**
 * An entity for storing meter readings
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
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
