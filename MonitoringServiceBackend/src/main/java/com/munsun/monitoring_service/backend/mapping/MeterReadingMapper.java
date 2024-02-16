package com.munsun.monitoring_service.backend.mapping;

import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An abstraction that contains functionality for converting DTO objects into entities
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@Mapper(imports = Arrays.class, componentModel = "spring")
public interface MeterReadingMapper {
    MeterReadingMapper instance = Mappers.getMapper(MeterReadingMapper.class);

    /**
     * converts the {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading} entity
     * to the {@linkplain com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut MeterReadingDtoOut} output object
     *
     * @param meterReadings entity {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading}
     * @return {@linkplain com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut MeterReadingDtoOut}
     */
    @Mapping(target = "meterReadings", expression = "java(map(meterReadings.getReadings()))")
    MeterReadingDtoOut toMeterReadingDtoOut(MeterReading meterReadings);

    default List<String> map(Map<String, Long> map) {
        return map.entrySet().stream()
                .map(x -> x.getKey() + ":" + x.getValue())
                .collect(Collectors.toList());
    }
    /**
     * converts the {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading} entity
     * into an output {@linkplain com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut LongMeterReadingDtoOut} object containing the user
     *
     * @param meterReading entity {@linkplain com.munsun.monitoring_service.backend.models.MeterReading MeterReading}
     * @return {@linkplain com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut LongMeterReadingDtoOut}
     */
    @Mapping(target = "meterReadings", expression = "java(map(meterReading.getReadings()))")
    @Mapping(target = "login", source = "meterReading.account.login")
    LongMeterReadingDtoOut toLongMeterReadingDtoOut(MeterReading meterReading);
}
