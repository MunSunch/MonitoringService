package com.munsun.monitoring_service.commons.dto.out;

import java.util.List;

/**
 * DTO for displaying information about meter readings to the user
 * @param date
 * @param meterReadings
 */
public record MeterReadingDtoOut(
        String date,
        List<String> meterReadings
) {}