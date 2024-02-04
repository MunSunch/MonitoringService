package com.munsun.monitoring_service.backend.services.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.MeterReadingNotFoundException;
import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.dao.AccountRepository;
import com.munsun.monitoring_service.backend.dao.MeterReadingsRepository;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>MonitoringServiceImpl class.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {
    private final MeterReadingsRepository readingsRepository;
    private final AccountRepository accountRepository;
    private final MeterReadingMapper readingsMapper;
    private static final List<String> DEFAULT_NAMES_METER_READINGS = List.of("отопление", "горячая вода", "холодная вода");
    private List<String> namesReadingsMeters = new ArrayList<>(DEFAULT_NAMES_METER_READINGS);

    /** {@inheritDoc} */
    @Override
    public void expandMeterReading(String nameNewMeterReading) {
        namesReadingsMeters.add(nameNewMeterReading);
    }

    /** {@inheritDoc} */
    public List<String> getNamesReadingsMeters() {
        return namesReadingsMeters;
    }

    /** {@inheritDoc} */
    @Override
    public MeterReadingDtoOut getActualMeterReadings(Long idAccount) throws AccountNotFoundException {
        var meterReading = readingsRepository.getLastMeterReadingByAccount_Id(idAccount)
                .orElse(null);
        return readingsMapper.toMeterReadingDtoOut(meterReading);
    }

    /** {@inheritDoc} */
    @Override
    public List<LongMeterReadingDtoOut> getActualMeterReadingsAllUsers() {
        var meterReadings = readingsRepository.getLastMetersReadingsAllAccounts();
        return meterReadings.stream()
                .map(readingsMapper::toLongMeterReadingDtoOut)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public List<LongMeterReadingDtoOut> getHistoryMonthAllUsers(Month month) {
        return readingsRepository.getAllMeterReadings_MonthAllAccounts(month).stream()
                .map(readingsMapper::toLongMeterReadingDtoOut)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public List<LongMeterReadingDtoOut> getAllMeterReadingsAllUsers() {
        return readingsRepository.getAllMeterReadingsAllAccounts().stream()
                .map(readingsMapper::toLongMeterReadingDtoOut)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReadingDtoOut> getHistoryMonth(Long idAccount, Month month) {
        return readingsRepository.getMeterReadingsByMonthAndAccount_Id(idAccount, month).stream()
                .map(readingsMapper::toMeterReadingDtoOut)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public MeterReadingDtoOut addMeterReadings(Long idAccount, MeterReadingsDtoIn dtoIn) throws AccountNotFoundException, MeterReadingNotFoundException {
        Date now = new Date();
        checkRepeatAddMeterReadingPerMonth(idAccount);
        try {
            MeterReading newMeterReading = new MeterReading();
                newMeterReading.setAccount(accountRepository.getById(idAccount).orElseThrow(AccountNotFoundException::new));
                newMeterReading.setDate(new java.sql.Date(now.getTime()));
                newMeterReading.setReadings(dtoIn.readings());
            Long idSavedMeterReading = readingsRepository.save(newMeterReading);
            return readingsMapper.toMeterReadingDtoOut(readingsRepository.getById(idSavedMeterReading).orElseThrow(MeterReadingNotFoundException::new));
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkRepeatAddMeterReadingPerMonth(Long idAccount) {
        var actualMeterReading = readingsRepository.getLastMeterReadingByAccount_Id(idAccount);
        if(actualMeterReading.isEmpty())
            return;
        if(actualMeterReading.get().getDate().getMonth() == (new Date()).getMonth())
            throw new IllegalArgumentException("Добавление невозможно: запись за текущий месяц уже существует");
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReadingDtoOut> getAllHistory(Long idAccount) {
        return readingsRepository.getAllMeterReadingsByAccount_Id(idAccount).stream()
                .map(readingsMapper::toMeterReadingDtoOut)
                .collect(Collectors.toList());
    }
}
