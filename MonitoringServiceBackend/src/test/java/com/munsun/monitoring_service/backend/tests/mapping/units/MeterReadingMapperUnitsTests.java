package com.munsun.monitoring_service.backend.tests.mapping.units;

import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.mapping.impl.MeterReadingMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MeterReadingMapperUnitsTests {
    private MeterReadingMapper meterReadingMapper = new MeterReadingMapperImpl();

    @DisplayName("Successful Entity and DTO conversion")
    @Test
    public void testConvertMeterReadingToMeterReadingDtoOut() {
        Date date = new Date();
        var expected = new MeterReadingDtoOut(date.toString(), List.of("отопление:10"));
        var meterReading = new MeterReading();
            meterReading.setDate(date);
            meterReading.setReadings(Map.of("отопление", 10L));

        var actual = meterReadingMapper.toMeterReadingDtoOut(meterReading);

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @DisplayName("Successful conversion of an entity to another DTO")
    @Test
    public void testConvertMeterReadingToLongMeterReadingDtoOut() {
        Date date = new Date();
        var expected = new LongMeterReadingDtoOut("testLogin", date.toString(), List.of("отопление:10"));
        var meterReading = new MeterReading();
            meterReading.setDate(date);
            var account = new Account();
                account.setLogin("testLogin");
            meterReading.setAccount(account);
            meterReading.setReadings(Map.of("отопление", 10L));

        var actual = meterReadingMapper.toLongMeterReadingDtoOut(meterReading);

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}