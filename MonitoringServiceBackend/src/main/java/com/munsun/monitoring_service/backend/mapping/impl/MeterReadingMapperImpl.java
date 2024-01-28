package com.munsun.monitoring_service.backend.mapping.impl;

import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter from the MeterReading entity to the output DTO objects LongMeterReadingDtoOut, MeterReadingDtoOut
 */
public class MeterReadingMapperImpl implements MeterReadingMapper {

    /**
     * Converts MeterReadingDtoOut to a DTO object MeterReadingDtoOut
     * @param meterReadings entity MeterReading.java
     * @return MeterReadingDtoOut DTO object
     */
    @Override
    public MeterReadingDtoOut toMeterReadingDtoOut(MeterReading meterReadings) {
        String separator = ":";
        List<String> readings = meterReadings.getReadings().entrySet().stream()
                .map(x -> x.getKey() + separator + x.getValue())
                .collect(Collectors.toList());
        return new MeterReadingDtoOut(meterReadings.getDate().toString(), readings);
    }

    /**
     * Converts MeterReadingDtoOut to a DTO object LongMeterReadingDtoOut
     * @param meterReading entity MeterReading.java
     * @return LongMeterReadingDtoOut DTO object
     */
    @Override
    public LongMeterReadingDtoOut toLongMeterReadingDtoOut(MeterReading meterReading) {
        String separator = ":";
        var readings = meterReading.getReadings().entrySet().stream()
                .map(x -> x.getKey() + separator + x.getValue()).toList();
        return new LongMeterReadingDtoOut(meterReading.getAccount().getLogin(),
                                          meterReading.getDate().toString(),
                                          readings);
    }
}
