package com.munsun.monitoring_service.backend.exceptions;

/**
 * Exceptions thrown when an unsuccessful attempt is made to search for meter readings by its identifier
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public class MeterReadingNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "MeterReading id not exists!";

    public MeterReadingNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
