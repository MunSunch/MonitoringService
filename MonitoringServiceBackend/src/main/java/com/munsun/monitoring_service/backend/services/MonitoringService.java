package com.munsun.monitoring_service.backend.services;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;

import java.time.Month;
import java.util.List;

public interface MonitoringService {
    MeterReadingDtoOut getActualMeterReadings(Long idAccount) throws AccountNotFoundException;
    List<LongMeterReadingDtoOut> getActualMeterReadingsAllUsers();
    List<LongMeterReadingDtoOut> getHistoryMonthAllUsers(Month month);
    List<LongMeterReadingDtoOut> getAllMeterReadingsAllUsers();
    List<MeterReadingDtoOut> getHistoryMonth(Long idAccount, Month month);
    MeterReadingDtoOut addMeterReadings(Long idAccount, MeterReadingsDtoIn dtoIn) throws DatabaseConstraintException, AccountNotFoundException;
    List<MeterReadingDtoOut> getAllHistory(Long idAccount);
    List<String> getNamesReadingsMeters();
    void expandMeterReading(String nameNewMeterReading);
}