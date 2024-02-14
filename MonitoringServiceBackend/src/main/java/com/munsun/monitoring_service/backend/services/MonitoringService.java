package com.munsun.monitoring_service.backend.services;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.MeterReadingNotFoundException;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;

import java.time.Month;
import java.util.List;

/**
 * Abstraction for working with meter reading data
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface MonitoringService {
    /**
     * Return the current meter readings for the current month for a specific account by its ID.
     *
     * @param idAccount a {@link java.lang.Long} number
     * @return a {@link MeterReadingDtoOut} object
     * @throws AccountNotFoundException if any.
     */
    MeterReadingDtoOut getActualMeterReadings(Long idAccount) throws AccountNotFoundException;

    /**
     * Return current meter readings for the current month for all accounts with information about the account
     *
     * @return a {@link java.util.List}
     */
    List<LongMeterReadingDtoOut> getActualMeterReadingsAllUsers();

    /**
     * Return all meter readings for a specific {@link Month month} for all accounts with information about the account
     *
     * @param month a {@link java.time.Month}
     * @return a {@link java.util.List}
     */
    List<LongMeterReadingDtoOut> getHistoryMonthAllUsers(Month month);

    /**
     * Return all meter readings from all accounts for the entire time with information about the account
     *
     * @return a {@link java.util.List} object
     */
    List<LongMeterReadingDtoOut> getAllMeterReadingsAllUsers();

    /**
     * Return all meter readings for a specific {@link Month month} of a specific {@link Account account} by its ID
     *
     * @param idAccount a {@link java.lang.Long} number
     * @param month a {@link java.time.Month} object
     * @return a {@link java.util.List} object
     */
    List<MeterReadingDtoOut> getHistoryMonth(Long idAccount, Month month);

    /**
     * Record a new {@link MeterReadingsDtoIn meter reading} and record it for an account with an ID
     *
     * @param idAccount a {@link java.lang.Long} number
     * @param dtoIn a {@link com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn} object
     * @return a {@link com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut} object
     * @throws com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException if any.
     */
    MeterReadingDtoOut addMeterReadings(Long idAccount, MeterReadingsDtoIn dtoIn) throws AccountNotFoundException, MeterReadingNotFoundException, MissingKeyReadingException;

    /**
     * Return all meter readings of a specific {@link Account account} by its ID
     * @param idAccount a {@link java.lang.Long} number
     * @return a {@link java.util.List}
     */
    List<MeterReadingDtoOut> getAllHistory(Long idAccount);

    /**
     * Return a list of names of counter indicators
     * @return a {@link java.util.List}
     */
    List<String> getNamesReadingsMeters();

    /**
     * Expand the list of indicators with a new name. Ideally, the value of such an indicator should be a numeric type object
     * @param nameNewMeterReading a {@link java.lang.String} object
     */
    void expandMeterReading(String nameNewMeterReading);
}
