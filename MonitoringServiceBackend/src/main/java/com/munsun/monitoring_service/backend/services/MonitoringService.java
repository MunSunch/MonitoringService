package com.munsun.monitoring_service.backend.services;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.exceptions.MeterReadingNotFoundException;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;

import java.time.Month;
import java.util.List;

/**
 * <p>MonitoringService interface.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface MonitoringService {
    /**
     * <p>getActualMeterReadings.</p>
     *
     * @param idAccount a {@link java.lang.Long} object
     * @return a {@link MeterReadingDtoOut} object
     * @throws AccountNotFoundException if any.
     */
    MeterReadingDtoOut getActualMeterReadings(Long idAccount) throws AccountNotFoundException;
    /**
     * <p>getActualMeterReadingsAllUsers.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<LongMeterReadingDtoOut> getActualMeterReadingsAllUsers();
    /**
     * <p>getHistoryMonthAllUsers.</p>
     *
     * @param month a {@link java.time.Month} object
     * @return a {@link java.util.List} object
     */
    List<LongMeterReadingDtoOut> getHistoryMonthAllUsers(Month month);
    /**
     * <p>getAllMeterReadingsAllUsers.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<LongMeterReadingDtoOut> getAllMeterReadingsAllUsers();
    /**
     * <p>getHistoryMonth.</p>
     *
     * @param idAccount a {@link java.lang.Long} object
     * @param month a {@link java.time.Month} object
     * @return a {@link java.util.List} object
     */
    List<MeterReadingDtoOut> getHistoryMonth(Long idAccount, Month month);
    /**
     * <p>addMeterReadings.</p>
     *
     * @param idAccount a {@link java.lang.Long} object
     * @param dtoIn a {@link com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn} object
     * @return a {@link com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut} object
     * @throws com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException if any.
     * @throws com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException if any.
     */
    MeterReadingDtoOut addMeterReadings(Long idAccount, MeterReadingsDtoIn dtoIn) throws DatabaseConstraintException, AccountNotFoundException, MeterReadingNotFoundException;
    /**
     * <p>getAllHistory.</p>
     *
     * @param idAccount a {@link java.lang.Long} object
     * @return a {@link java.util.List} object
     */
    List<MeterReadingDtoOut> getAllHistory(Long idAccount);
    /**
     * <p>getNamesReadingsMeters.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<String> getNamesReadingsMeters();
    /**
     * <p>expandMeterReading.</p>
     *
     * @param nameNewMeterReading a {@link java.lang.String} object
     */
    void expandMeterReading(String nameNewMeterReading);
}
