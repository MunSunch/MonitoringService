package com.munsun.monitoring_service.backend.mapping.impl;

import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter from the {@linkplain MeterReading} entity to the output DTO objects
 * {@linkplain LongMeterReadingDtoOut}, {@linkplain MeterReadingDtoOut}
 * @author MunSun
 * @version $Id: 1.0-SNAPSHOT
 */
public class MeterReadingMapperImpl implements MeterReadingMapper {
    private static final String SEPARATOR = ":";

    /**
     * {@inheritDoc}
     */
    @Override
    public MeterReadingDtoOut toMeterReadingDtoOut(MeterReading meterReadings) {
        List<String> readings = meterReadings.getReadings().entrySet().stream()
                .map(x -> x.getKey() + SEPARATOR + x.getValue())
                .collect(Collectors.toList());
        return new MeterReadingDtoOut(meterReadings.getDate().toString(), readings);
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public LongMeterReadingDtoOut toLongMeterReadingDtoOut(MeterReading meterReading) {
        var readings = meterReading.getReadings().entrySet().stream()
                .map(x -> x.getKey() + SEPARATOR + x.getValue()).toList();
        return new LongMeterReadingDtoOut(meterReading.getAccount().getLogin(),
                                          meterReading.getDate().toString(),
                                          readings);
    }
}
