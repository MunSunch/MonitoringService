package com.munsun.monitoring_service.backend.exceptions;

public class MeterReadingNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE = "MeterReading id not exists!";

    public MeterReadingNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
