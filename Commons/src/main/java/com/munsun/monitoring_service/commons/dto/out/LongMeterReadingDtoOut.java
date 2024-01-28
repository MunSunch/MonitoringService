package com.munsun.monitoring_service.commons.dto.out;

import java.util.List;

/**
 * A DTO is an object for the output of a part of an account for further transfer
 * to the administrator for the withdrawal of funds, indicating the user who owns this information
 * @param login
 * @param date
 * @param meterReadings
 */
public record LongMeterReadingDtoOut(
        String login,
        String date,
        List<String> meterReadings
) {}