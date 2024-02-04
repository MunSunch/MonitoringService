package com.munsun.monitoring_service.backend.dao;

import com.munsun.monitoring_service.backend.models.MeterReading;
import java.time.Month;
import java.util.List;
import java.util.Optional;

/**
 * Interface for storing and retrieving an entity MeterReading
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface MeterReadingsRepository extends CrudRepository<MeterReading, Long>{
    /**
     * <p>getLastMeterReadingByAccount_Id.</p>
     *
     * @param idAccount a {@link java.lang.Long} object
     * @return a {@link java.util.Optional} object
     */
    Optional<MeterReading> getLastMeterReadingByAccount_Id(Long idAccount);
    /**
     * <p>getAllMeterReadingsByAccount_Id.</p>
     *
     * @param idAccount a {@link java.lang.Long} object
     * @return a {@link java.util.List} object
     */
    List<MeterReading> getAllMeterReadingsByAccount_Id(Long idAccount);
    /**
     * <p>getMeterReadingsByMonthAndAccount_Id.</p>
     *
     * @param idAccount a {@link Long} object
     * @param month a {@link Month} object
     * @return a {@link java.util.List} object
     */
    Optional<MeterReading> getMeterReadingsByMonthAndAccount_Id(Long idAccount, Month month);

    /**
     * <p>getLastMetersReadingsAllAccounts.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<MeterReading> getLastMetersReadingsAllAccounts();
    /**
     * <p>getAllMeterReadingsAllAccounts.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<MeterReading> getAllMeterReadingsAllAccounts();
    /**
     * <p>getMeterReadingsByMonthAllAccounts.</p>
     *
     * @param month a {@link java.time.Month} object
     * @return a {@link java.util.List} object
     */
    List<MeterReading> getAllMeterReadings_MonthAllAccounts(Month month);
}
