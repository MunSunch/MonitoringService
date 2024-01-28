package com.munsun.monitoring_service.backend.mapping;

import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;

/**
 * An abstraction that contains functionality for converting DTO objects into entities
 */
public interface MeterReadingMapper {
    /**
     * converts the MeterReading entity to the MeterReadingDtoOut output object
     * @param meterReadings entity MeterReading.java
     * @return MeterReadingDtoOut
     */
    MeterReadingDtoOut toMeterReadingDtoOut(MeterReading meterReadings);

    /**
     * converts the MeterReading entity into an output LongMeterReadingDtoOut object containing the user
     * @param meterReading entity MeterReading.java
     * @return LongMeterReadingDtoOut
     */
    LongMeterReadingDtoOut toLongMeterReadingDtoOut(MeterReading meterReading);
}
