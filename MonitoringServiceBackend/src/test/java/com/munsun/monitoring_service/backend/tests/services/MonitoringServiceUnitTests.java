package com.munsun.monitoring_service.backend.tests.services;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.repositories.AccountRepository;
import com.munsun.monitoring_service.backend.repositories.MeterReadingsRepository;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.backend.services.impl.MonitoringServiceImpl;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.ArgumentMatchers.eq;

public class MonitoringServiceUnitTests {
    private MeterReadingsRepository repository = mock();
    private MonitoringService service = new MonitoringServiceImpl(repository,
            mock(AccountRepository.class), mock(MeterReadingMapper.class));

    @Disabled
    @Test
    public void negativeTestAddMeterReadings() throws DatabaseConstraintException, AccountNotFoundException {
        var meterReading = new MeterReading();
            meterReading.setDate(new Date(2024, Calendar.JANUARY, 28));

        when(repository.getLastMeterReadingByAccount_Id(eq(0L)))
                .thenReturn(Optional.of(meterReading));

        assertThatThrownBy(() ->
                service.addMeterReadings(eq(0L), any(MeterReadingsDtoIn.class))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Добавление невозможно: запись за текущий месяц уже существует");
    }
}
