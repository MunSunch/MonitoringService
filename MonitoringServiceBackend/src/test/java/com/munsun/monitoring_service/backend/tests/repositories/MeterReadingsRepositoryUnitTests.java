package com.munsun.monitoring_service.backend.tests.repositories;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.repositories.MeterReadingsRepository;
import com.munsun.monitoring_service.backend.repositories.impl.MeterReadingsRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class MeterReadingsRepositoryUnitTests {
    private MeterReadingsRepository repository = new MeterReadingsRepositoryImpl();

    @Test
    public void testGetLastMetersReadingsByAccount_Id() throws DatabaseConstraintException {
        var meterReading1 = new MeterReading();
            meterReading1.setAccount(new Account(0L));
            meterReading1.setDate(new Date());
            meterReading1.setReadings(Map.of("вода", 11L,
                                            "отопление", 22L));
        var meterReading2 = new MeterReading();
            meterReading2.setAccount(new Account(1L));
            meterReading2.setDate(new Date());
            meterReading2.setReadings(Map.of("вода", 11L,
                    "отопление", 22L));
        var meterReading3 = new MeterReading();
            meterReading3.setAccount(new Account(2L));
            meterReading3.setDate(new Date());
            meterReading3.setReadings(Map.of("вода", 11L,
                    "отопление", 22L));

        repository.save(meterReading1);
        repository.save(meterReading2);
        repository.save(meterReading3);
        var actual = repository.getLastMeterReadingByAccount_Id(1L);

        assertThat(actual)
                .isPresent()
                .get()
                .extracting(MeterReading::getAccount)
                    .extracting(Account::getId)
                        .isEqualTo(1L);
    }

    @Test
    public void testGetAllMeterReadingsByAccount_Id() throws DatabaseConstraintException {
        var meterReading = new MeterReading();
            meterReading.setAccount(new Account(0L));
            meterReading.setDate(new Date());
            meterReading.setReadings(Map.of("вода", 11L,
                                            "отопление", 22L));
        repository.save(meterReading);
        repository.save(meterReading);
        repository.save(meterReading);
        var actual = repository.getAllMeterReadingsByAccount_Id(0L);

        assertThat(actual.size())
                .isEqualTo(3);
    }

    @Test
    public void testGetMeterReadingsByMonthAndAccount_Id() throws DatabaseConstraintException {
        var meterReading1 = new MeterReading();
            meterReading1.setAccount(new Account(1l));
            meterReading1.setDate(new Date(2024, Calendar.JANUARY, 28));
        var meterReading2 = new MeterReading();
            meterReading2.setAccount(new Account(0l));
            meterReading2.setDate(new Date(2024, Calendar.FEBRUARY, 28));
        var meterReading3 = new MeterReading();
            meterReading3.setAccount(new Account(1l));
            meterReading3.setDate(new Date(2024, Calendar.APRIL, 28));
        var meterReading4 = new MeterReading();
            meterReading4.setAccount(new Account(0l));
            meterReading4.setDate(new Date(2024, Calendar.JANUARY, 28));

        repository.save(meterReading1);
        repository.save(meterReading2);
        repository.save(meterReading3);
        repository.save(meterReading4);
        var actual = repository.getMeterReadingsByMonthAndAccount_Id(0L, Month.FEBRUARY);

        assertThat(actual)
                .element(0).isNotNull()
                .extracting(MeterReading::getDate)
                    .extracting(Date::getMonth)
                        .isEqualTo(Month.FEBRUARY.getValue()-1);
        assertThat(actual)
                .element(0)
                .extracting(MeterReading::getAccount)
                    .extracting(Account::getId)
                        .isEqualTo(0L);
    }

    @Test
    public void testGetLastMetersReadingsAllAccounts() throws DatabaseConstraintException {
        var meterReading1 = new MeterReading();
            meterReading1.setAccount(new Account(1l));
            meterReading1.setDate(new Date(2023, Calendar.DECEMBER, 28));
        var meterReading2 = new MeterReading();
            meterReading2.setAccount(new Account(0l));
            meterReading2.setDate(new Date(2023, Calendar.DECEMBER, 11));
        var meterReading3 = new MeterReading();
            meterReading3.setAccount(new Account(1l));
            meterReading3.setDate(new Date(2024, Calendar.JANUARY, 28));
        var meterReading4 = new MeterReading();
            meterReading4.setAccount(new Account(0l));
            meterReading4.setDate(new Date(2024, Calendar.JANUARY, 28));

        repository.save(meterReading1);
        repository.save(meterReading2);
        repository.save(meterReading3);
        repository.save(meterReading4);
        var actual = repository.getLastMetersReadingsAllAccounts();

        assertThat(actual)
                .extracting(MeterReading::getDate)
                    .allMatch(x -> x.getMonth()+1 == Month.JANUARY.getValue());
    }

    @Test
    public void testGetAllMeterReadingsAllAccounts() throws DatabaseConstraintException {
        var meterReading1 = new MeterReading();
            meterReading1.setAccount(new Account(1l));
            meterReading1.setDate(new Date(2023, Calendar.DECEMBER, 28));
        var meterReading2 = new MeterReading();
            meterReading2.setAccount(new Account(2l));
            meterReading2.setDate(new Date(2023, Calendar.DECEMBER, 11));
        var meterReading3 = new MeterReading();
            meterReading3.setAccount(new Account(3l));
            meterReading3.setDate(new Date(2024, Calendar.JANUARY, 28));
        var meterReading4 = new MeterReading();
            meterReading4.setAccount(new Account(4l));
            meterReading4.setDate(new Date(2024, Calendar.JANUARY, 28));

        repository.save(meterReading1);
        repository.save(meterReading2);
        repository.save(meterReading3);
        repository.save(meterReading4);
        var actual = repository.getAllMeterReadingsAllAccounts();

        assertThat(actual)
                .hasSize(4);
    }

    @Test
    public void testGetMeterReadingsByMonthAllAccounts() throws DatabaseConstraintException {
        var meterReading1 = new MeterReading();
            meterReading1.setAccount(new Account(1l));
            meterReading1.setDate(new Date(2023, Calendar.JANUARY, 28));
        var meterReading2 = new MeterReading();
            meterReading2.setAccount(new Account(2l));
            meterReading2.setDate(new Date(2023, Calendar.DECEMBER, 11));
        var meterReading3 = new MeterReading();
            meterReading3.setAccount(new Account(3l));
            meterReading3.setDate(new Date(2024, Calendar.JANUARY, 28));
        var meterReading4 = new MeterReading();
            meterReading4.setAccount(new Account(4l));
            meterReading4.setDate(new Date(2024, Calendar.JANUARY, 28));

        repository.save(meterReading1);
        repository.save(meterReading2);
        repository.save(meterReading3);
        repository.save(meterReading4);
        var actual = repository.getMeterReadingsByMonthAllAccounts(Month.JANUARY);

        assertThat(actual)
                .hasSize(3)
                .allMatch(x -> x.getDate().getMonth()+1 == Month.JANUARY.getValue());
    }

    @Test
    public void testSave() throws DatabaseConstraintException {
        var meterReading = new MeterReading();
            meterReading.setDate(new Date());
            meterReading.setReadings(Map.of("вода", 11L,
                                            "отопление", 22L));
        var expected = new MeterReading();
            meterReading.setDate(meterReading.getDate());
            meterReading.setReadings(meterReading.getReadings());

        var id = repository.save(meterReading).getId();
        var actual = repository.getById(id);

        assertThat(actual)
                .isPresent()
                .get().extracting(MeterReading::getId).isNotNull();
    }
}
