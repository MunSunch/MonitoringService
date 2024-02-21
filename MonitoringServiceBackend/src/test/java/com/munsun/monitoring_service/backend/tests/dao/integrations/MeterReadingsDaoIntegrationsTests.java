package com.munsun.monitoring_service.backend.tests.dao.integrations;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.dao.MeterReadingsDao;
import com.munsun.monitoring_service.backend.dao.impl.AccountDaoImpl;
import com.munsun.monitoring_service.backend.dao.impl.MeterReadingsDaoImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcAccountMapperImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcMeterReadingsMapperImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcPlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.tests.dao.PostgresContainer;
import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.db.impl.DatabaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Month;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class MeterReadingsDaoIntegrationsTests extends PostgresContainer {
    private MeterReadingsDao meterReadingsRepository;
    private AccountDao accountRepository;

    private Account testAccount1 = Account.builder()
            .id(1L)
            .login("user")
            .password("user")
            .role(Role.USER)
            .placeLiving(PlaceLivingEmbedded.builder()
                    .country("Russia")
                    .city("Moscow")
                    .street("Red")
                    .house("silver")
                    .level("8")
                    .apartmentNumber("12/22")
                    .build())
            .build();
    private Account testAccount2 = Account.builder()
            .id(2L)
            .login("andrey")
            .password("andrey")
            .role(Role.USER)
            .placeLiving(PlaceLivingEmbedded.builder()
                    .country("Russia")
                    .city("Moscow")
                    .street("Red")
                    .house("silver")
                    .level("8")
                    .apartmentNumber("12/22")
                    .build())
            .build();

    @BeforeEach
    public void addAccounts() throws SQLException {
        accountRepository.save(testAccount1);
        accountRepository.save(testAccount2);
    }

    @DisplayName("search for a non-existent counter record by ID")
    @Test
    public void negativeTestGetMeterReadingById() throws SQLException {
        var actual = meterReadingsRepository.getById(111L);

        assertThat(actual).isEmpty();
    }

    @DisplayName("successfully saving a new counter record without overwriting account information")
    @Test
    public void positiveTestSaveMeterReading() throws SQLException {
        var meterReading = new MeterReading();
            meterReading.setDate(new Date(new java.util.Date().getTime()));
            meterReading.setAccount(testAccount1);
            meterReading.setReadings(Map.of("электричество", 11L,
                                            "отопление", 100L));

        meterReadingsRepository.save(meterReading);
        var actual = meterReadingsRepository.getById(1L);

        assertThat(actual)
                .isPresent();
    }

    @DisplayName("successful cascading deletion of existing meter readings along with records")
    @Test
    public void positiveTestDeleteMeterReadingById() throws SQLException {
        var meterReading = new MeterReading();
            meterReading.setDate(new Date(new java.util.Date().getTime()));
            meterReading.setAccount(testAccount1);
            meterReading.setReadings(Map.of("электричество", 11L,
                                            "отопление", 100L));
        int expectedCountDeletedRow = 1;

        meterReadingsRepository.save(meterReading);
        var actualCountDeleteRow = meterReadingsRepository.deleteById(1L);

        var actual = meterReadingsRepository.getById(1L);

        assertThat(actual).isEmpty();
        assertThat(actualCountDeleteRow).isEqualTo(expectedCountDeletedRow);
    }

    @DisplayName("failed cascade deletion of non-existent meter readings along with records")
    @Test
    public void negativeTestDeleteMeterReadingById() throws SQLException {
        var expectedCountDeleteRow = 0;

        var actualCountDeleteRow = meterReadingsRepository.deleteById(1L);

        assertThat(actualCountDeleteRow).isEqualTo(expectedCountDeleteRow);
    }

    @DisplayName("successful receipt of up-to-date counter indicators by account ID")
    @Test
    public void positiveTestGetActualMeterReadingByAccountId() throws SQLException {
        var now = new java.util.Date();
        var millisecondInMonth = 2764800000L;
        var meterReading1 = new MeterReading();
            meterReading1.setDate(new Date(now.getTime()));
            meterReading1.setAccount(testAccount1);
            meterReading1.setReadings(Map.of("электричество", 11L,
                "отопление", 100L));
        var meterReading2 = new MeterReading();
            meterReading2.setDate(new Date(now.getTime()-millisecondInMonth));
            meterReading2.setAccount(testAccount1);
            meterReading2.setReadings(Map.of("электричество", 11L,
                    "отопление", 100L));
        var meterReading3 = new MeterReading();
            meterReading3.setDate(new Date(now.getTime()-2*millisecondInMonth));
            meterReading3.setAccount(testAccount1);
            meterReading3.setReadings(Map.of("электричество", 11L,
                    "отопление", 100L));

        meterReadingsRepository.save(meterReading1);
        meterReadingsRepository.save(meterReading2);
        meterReadingsRepository.save(meterReading3);

        var actual = meterReadingsRepository.getLastMeterReadingByAccount_Id(testAccount1.getId());

        assertThat(actual)
                .isPresent().get()
                .extracting(MeterReading::getDate)
                .extracting(Date::getMonth)
                    .isEqualTo(meterReading1.getDate().getMonth());
    }

    @DisplayName("failed receipt of up-to-date counter indicators by account ID that have not yet been added this month")
    @Test
    public void negativeTestGetActualMeterReadingByAccountId() throws SQLException {
        var actual = meterReadingsRepository.getLastMeterReadingByAccount_Id(testAccount1.getId());

        assertThat(actual).isEmpty();
    }

    @DisplayName("successful receipt of counter indicators by account ID for all time")
    @Test
    public void positiveTestGetAllMeterReadingsByAccountId() throws SQLException {
        var now = new java.util.Date();
        var millisecondInMonth = 2764800000L;

        var meterReading1 = new MeterReading();
            meterReading1.setDate(new Date(now.getTime()));
            meterReading1.setAccount(testAccount1);
            meterReading1.setReadings(Map.of("электричество", 1L,
                "отопление", 1L, "аренда", 1L));
        var meterReading2 = new MeterReading();
            meterReading2.setDate(new Date(now.getTime()-millisecondInMonth));
            meterReading2.setAccount(testAccount1);
            meterReading2.setReadings(Map.of("электричество", 2L, "отопление", 2L));
        var meterReading3 = new MeterReading();
            meterReading3.setDate(new Date(now.getTime()-2*millisecondInMonth));
            meterReading3.setAccount(testAccount1);
            meterReading3.setReadings(Map.of("электричество", 3L));
        var expectedSize=3;

        meterReadingsRepository.save(meterReading1);
        meterReadingsRepository.save(meterReading2);
        meterReadingsRepository.save(meterReading3);
        var actual = meterReadingsRepository.getAllMeterReadingsByAccount_Id(testAccount1.getId());

        assertThat(actual)
                .hasSize(expectedSize)
                .allMatch(Objects::nonNull);
        assertThat(actual.get(0).getReadings())
                .isIn(meterReading1.getReadings());
        assertThat(actual.get(1).getReadings())
                .isIn(meterReading2.getReadings());
        assertThat(actual.get(2).getReadings())
                .isIn(meterReading3.getReadings());
    }

    @DisplayName("successful receipt of an empty list of counter indicators by account ID for the entire time")
    @Test
    public void positiveTestGetAllMeterReadingsByAccountId_EmptyList() {
        int expectedSize = 0;

        var actual = meterReadingsRepository.getAllMeterReadingsByAccount_Id(testAccount1.getId());

        assertThat(actual).hasSize(expectedSize);
    }

    @DisplayName("successful receipt of counter indicators for a specific month and for a specific account")
    @Test
    public void positiveTestGetMeterReadingsByMonthAndAccountId() throws SQLException {
        var now = new java.util.Date();
        var millisecondInMonth = 2764800000L;

        var meterReading1 = new MeterReading();
            meterReading1.setDate(new Date(now.getTime()));
            meterReading1.setAccount(testAccount1);
            meterReading1.setReadings(Map.of("электричество", 1L,
                    "отопление", 1L, "аренда", 1L));
        var meterReading2 = new MeterReading();
            meterReading2.setDate(new Date(now.getTime()-millisecondInMonth));
            meterReading2.setAccount(testAccount1);
            meterReading2.setReadings(Map.of("электричество", 2L, "отопление", 2L));
        var meterReading3 = new MeterReading();
            meterReading3.setDate(new Date(now.getTime()-2*millisecondInMonth));
            meterReading3.setAccount(testAccount1);
            meterReading3.setReadings(Map.of("электричество", 3L));

        meterReadingsRepository.save(meterReading1);
        meterReadingsRepository.save(meterReading2);
        meterReadingsRepository.save(meterReading3);
        var actual = meterReadingsRepository.getMeterReadingsByMonthAndAccount_Id(testAccount1.getId(), Month.JANUARY);

        assertThat(actual)
                .isPresent().get()
                .extracting(MeterReading::getDate)
                    .extracting(Date::getMonth)
                        .isEqualTo(meterReading2.getDate().getMonth());
    }

    @DisplayName("receipt of an empty list of counter indicators for a specific month and for a specific account")
    @Test
    public void testGetMeterReadingsByMonthAndAccountId() throws SQLException {
        var meterReading1 = new MeterReading();
            meterReading1.setDate(new Date(new java.util.Date().getTime()));
            meterReading1.setAccount(testAccount1);
            meterReading1.setReadings(Map.of("электричество", 1L,
                    "отопление", 1L, "аренда", 1L));

        meterReadingsRepository.save(meterReading1);
        var actual = meterReadingsRepository.getMeterReadingsByMonthAndAccount_Id(testAccount1.getId(), Month.MAY);

        assertThat(actual)
                .isEmpty();
    }

    @DisplayName("successful receipt of up-to-date counter indicators for all accounts for all time")
    @Test
    public void positiveTestGetLastMeterReadingsAllAccounts() throws SQLException {
        var now = new java.util.Date();
        var millisecondInMonth = 2764800000L;

        var meterReading1 = new MeterReading();
            meterReading1.setDate(new Date(now.getTime()));
            meterReading1.setAccount(testAccount1);
            meterReading1.setReadings(Map.of("электричество", 1L,
                "отопление", 1L, "аренда", 1L));
        var meterReading2 = new MeterReading();
            meterReading2.setDate(new Date(now.getTime()-millisecondInMonth));
            meterReading2.setAccount(testAccount1);
            meterReading2.setReadings(Map.of("электричество", 2L, "отопление", 2L));
        var meterReading3 = new MeterReading();
            meterReading3.setDate(new Date(now.getTime()-2*millisecondInMonth));
            meterReading3.setAccount(testAccount1);
            meterReading3.setReadings(Map.of("электричество", 3L));
        var meterReading4 = new MeterReading();
            meterReading4.setDate(new Date(now.getTime()));
            meterReading4.setAccount(testAccount2);
            meterReading4.setReadings(Map.of("электричество", 1L, "отопление", 1L));
        var expectedSize = 2;

        meterReadingsRepository.save(meterReading1);
        meterReadingsRepository.save(meterReading2);
        meterReadingsRepository.save(meterReading3);
        meterReadingsRepository.save(meterReading4);
        var actual = meterReadingsRepository.getLastMetersReadingsAllAccounts();

        assertThat(actual)
                .hasSize(expectedSize)
                .extracting(MeterReading::getDate)
                    .extracting(Date::getMonth)
                .allMatch(month -> month == meterReading1.getDate().getMonth());

        assertThat(actual)
                .extracting(MeterReading::getAccount)
                    .extracting(Account::getLogin)
                        .containsExactly(testAccount1.getLogin(), testAccount2.getLogin());
    }

    @DisplayName("successful receipt of counter metrics for all accounts for all time from all accounts")
    @Test
    public void positiveTestGetAllMeterReadingsAllAccounts() throws SQLException {
        var now = new java.util.Date();
        var millisecondInMonth = 2764800000L;

        var meterReading1 = new MeterReading();
            meterReading1.setDate(new Date(now.getTime()));
            meterReading1.setAccount(testAccount1);
            meterReading1.setReadings(Map.of("электричество", 1L,
                    "отопление", 1L, "аренда", 1L));
        var meterReading2 = new MeterReading();
            meterReading2.setDate(new Date(now.getTime()-millisecondInMonth));
            meterReading2.setAccount(testAccount1);
            meterReading2.setReadings(Map.of("электричество", 2L, "отопление", 2L));
        var meterReading3 = new MeterReading();
            meterReading3.setDate(new Date(now.getTime()-2*millisecondInMonth));
            meterReading3.setAccount(testAccount1);
            meterReading3.setReadings(Map.of("электричество", 3L));
        var meterReading4 = new MeterReading();
            meterReading4.setDate(new Date(now.getTime()));
            meterReading4.setAccount(testAccount2);
            meterReading4.setReadings(Map.of("электричество", 1L, "отопление", 1L));
        var expectedSize = 4;

        meterReadingsRepository.save(meterReading1);
        meterReadingsRepository.save(meterReading2);
        meterReadingsRepository.save(meterReading3);
        meterReadingsRepository.save(meterReading4);
        var actual = meterReadingsRepository.getAllMeterReadingsAllAccounts();

        assertThat(actual)
                .hasSize(expectedSize);
    }

    @Test
    public void positiveTestGetMeterReadingsByMonthAllAccounts() throws SQLException {
        var now = new java.util.Date();
        var millisecondInMonth = 2764800000L;
        var meterReading1 = new MeterReading();
            meterReading1.setDate(new Date(now.getTime()));
            meterReading1.setAccount(testAccount1);
            meterReading1.setReadings(Map.of("электричество", 1L,
                    "отопление", 1L, "аренда", 1L));
        var meterReading2 = new MeterReading();
            meterReading2.setDate(new Date(now.getTime()-millisecondInMonth));
            meterReading2.setAccount(testAccount1);
            meterReading2.setReadings(Map.of("электричество", 2L, "отопление", 2L));
        var meterReading3 = new MeterReading();
            meterReading3.setDate(new Date(now.getTime()-2*millisecondInMonth));
            meterReading3.setAccount(testAccount1);
            meterReading3.setReadings(Map.of("электричество", 3L));
        var meterReading4 = new MeterReading();
            meterReading4.setDate(new Date(now.getTime()));
            meterReading4.setAccount(testAccount2);
            meterReading4.setReadings(Map.of("электричество", 1L, "отопление", 1L));

        meterReadingsRepository.save(meterReading1);
        meterReadingsRepository.save(meterReading2);
        meterReadingsRepository.save(meterReading3);
        meterReadingsRepository.save(meterReading4);
        var actual1 = meterReadingsRepository.getAllMeterReadings_MonthAllAccounts(Month.FEBRUARY);

        assertThat(actual1)
                .hasSize(2)
                .extracting(MeterReading::getDate)
                        .extracting(Date::getMonth)
                        .containsExactly(Month.JANUARY.getValue(), Month.JANUARY.getValue());
    }
}