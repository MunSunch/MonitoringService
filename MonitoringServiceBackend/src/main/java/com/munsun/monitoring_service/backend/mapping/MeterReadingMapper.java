package com.munsun.monitoring_service.backend.mapping;

import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;

/**
 * An abstraction that contains functionality for converting DTO objects into entities
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
//@Mapper
public interface MeterReadingMapper {
    /**
     * converts the {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading} entity
     * to the {@linkplain com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut MeterReadingDtoOut} output object
     *
     * @param meterReadings entity {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading}
     * @return {@linkplain com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut MeterReadingDtoOut}
     */
    MeterReadingDtoOut toMeterReadingDtoOut(MeterReading meterReadings);

    /**
     * converts the {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading} entity
     * into an output {@linkplain com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut LongMeterReadingDtoOut} object containing the user
     *
     * @param meterReading entity {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading}
     * @return {@linkplain com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut LongMeterReadingDtoOut}
     */
    LongMeterReadingDtoOut toLongMeterReadingDtoOut(MeterReading meterReading);
}
