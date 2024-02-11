package com.munsun.monitoring_service.commons.dto.in;

import java.util.Map;

/**
 * DTO for key-value string pairs transmitted from an external input when adding a new record of readings
 * @param readings
 */
public record MeterReadingsDtoIn(
        Map<String, Long> readings
){}