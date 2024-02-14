package com.munsun.monitoring_service.backend.services.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.MeterReadingNotFoundException;
import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.dao.MeterReadingsDao;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * <p>MonitoringServiceImpl class.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {
    private final MeterReadingsDao readingsRepository;
    private final AccountDao accountRepository;
    private final MeterReadingMapper readingsMapper;
    private static final List<String> DEFAULT_NAMES_METER_READINGS = List.of("heating", "hot_water", "cold_water");
    private CopyOnWriteArraySet<String> namesReadingsMeters = new CopyOnWriteArraySet<>(DEFAULT_NAMES_METER_READINGS);

    /** {@inheritDoc} */
    @Override
    public void expandMeterReading(String nameNewMeterReading) {
        namesReadingsMeters.add(nameNewMeterReading);
    }

    /** {@inheritDoc} */
    public List<String> getNamesReadingsMeters() {
        return namesReadingsMeters.stream().toList();
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
    public MeterReadingDtoOut addMeterReadings(Long idAccount, MeterReadingsDtoIn dtoIn) throws AccountNotFoundException, MeterReadingNotFoundException, MissingKeyReadingException {
        checkRepeatAddMeterReadingPerMonth(idAccount);
        checkDefaultNamesMeterReadings(dtoIn);

        Date now = new Date();
        try {
            MeterReading newMeterReading = new MeterReading();
                newMeterReading.setAccount(accountRepository.getById(idAccount).orElseThrow(AccountNotFoundException::new));
                newMeterReading.setDate(new java.sql.Date(now.getTime()));
                newMeterReading.setReadings(dtoIn.readings());
            Long idSavedMeterReading = readingsRepository.save(newMeterReading);
            return readingsMapper.toMeterReadingDtoOut(readingsRepository.getById(idSavedMeterReading).orElseThrow(MeterReadingNotFoundException::new));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkDefaultNamesMeterReadings(MeterReadingsDtoIn dtoIn) throws MissingKeyReadingException {
        var keys = dtoIn.readings().keySet();
        if(keys.containsAll(namesReadingsMeters)) {
            return;
        }
        String message = namesReadingsMeters.stream()
                .filter(x -> !keys.contains(x))
                .collect(Collectors.joining(","));
        throw new MissingKeyReadingException("Missing parameters: " + message);
    }

    private void checkRepeatAddMeterReadingPerMonth(Long idAccount) {
        var actualMeterReading = readingsRepository.getLastMeterReadingByAccount_Id(idAccount);
        if(actualMeterReading.isEmpty())
            return;
        if(actualMeterReading.get().getDate().getMonth() == (new Date()).getMonth())
            throw new IllegalArgumentException("Adding is not possible: the record for the current month already exists");
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReadingDtoOut> getAllHistory(Long idAccount) {
        return readingsRepository.getAllMeterReadingsByAccount_Id(idAccount).stream()
                .map(readingsMapper::toMeterReadingDtoOut)
                .collect(Collectors.toList());
    }
}
