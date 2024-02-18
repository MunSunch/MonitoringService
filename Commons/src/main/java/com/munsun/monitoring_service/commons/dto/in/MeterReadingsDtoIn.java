package com.munsun.monitoring_service.commons.dto.in;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO for key-value string pairs transmitted from an external input when adding a new record of readings
 * @param readings
 */
public record MeterReadingsDtoIn(
        Map<String, Long> readings
){
        public MeterReadingsDtoIn {
                readings = new HashMap<>();
        }

        @JsonAnySetter
        public void addPair(String key, Long value) {
                readings.put(key, value);
        }
}