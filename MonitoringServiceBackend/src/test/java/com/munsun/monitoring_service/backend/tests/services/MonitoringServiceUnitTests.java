package com.munsun.monitoring_service.backend.tests.services;

import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.dao.MeterReadingsRepository;
import com.munsun.monitoring_service.backend.services.impl.MonitoringServiceImpl;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitoringServiceUnitTests {
    @Mock
    private MeterReadingsRepository repository;
    @Mock
    private MeterReadingMapper meterReadingMapper;
    @InjectMocks
    private MonitoringServiceImpl service;

    @DisplayName("Failed attempt to add a second record in a month")
    @Test
    public void negativeTestAddMeterReadings() {
        MeterReading meterReading = new MeterReading();
            meterReading.setDate(new Date(new java.util.Date().getTime()));

        when(repository.getLastMeterReadingByAccount_Id(anyLong()))
                .thenReturn(Optional.of(meterReading));

        assertThatThrownBy(() ->
                service.addMeterReadings(0L, new MeterReadingsDtoIn(Map.of("a", 1L)))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Добавление невозможно: запись за текущий месяц уже существует");
    }
}