package com.munsun.monitoring_service.backend.repositories;

import com.munsun.monitoring_service.backend.models.MeterReading;
import java.time.Month;
import java.util.List;
import java.util.Optional;

/**
 * Interface for storing and retrieving an entity MeterReading
 */
public interface MeterReadingsRepository extends CrudRepository<MeterReading, Long>{
    Optional<MeterReading> getLastMeterReadingByAccount_Id(Long idAccount);
    List<MeterReading> getAllMeterReadingsByAccount_Id(Long idAccount);
    List<MeterReading> getMeterReadingsByMonthAndAccount_Id(Long idAccount, Month month);

    List<MeterReading> getLastMetersReadingsAllAccounts();
    List<MeterReading> getAllMeterReadingsAllAccounts();
    List<MeterReading> getMeterReadingsByMonthAllAccounts(Month month);
}