package com.munsun.monitoring_service.backend.services.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.repositories.AccountRepository;
import com.munsun.monitoring_service.backend.repositories.MeterReadingsRepository;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import lombok.RequiredArgsConstructor;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>MonitoringServiceImpl class.</p>
 *
 * @author MunSun
 * @version $Id: $Id
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

    /**
     * <p>Getter for the field <code>namesReadingsMeters</code>.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<String> getNamesReadingsMeters() {
        return namesReadingsMeters;
    }

    /** {@inheritDoc} */
    @Override
    public MeterReadingDtoOut getActualMeterReadings(Long idAccount) throws AccountNotFoundException {
        var meterReading = readingsRepository.getLastMeterReadingByAccount_Id(idAccount)
                .orElseThrow(AccountNotFoundException::new);
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
        return readingsRepository.getMeterReadingsByMonthAllAccounts(month).stream()
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
    public MeterReadingDtoOut addMeterReadings(Long idAccount, MeterReadingsDtoIn dtoIn) throws DatabaseConstraintException, AccountNotFoundException {
        Date now = new Date();
        checkRepeatAddMeterReadingPerMonth(idAccount);
        MeterReading newMeterReading = new MeterReading();
            newMeterReading.setAccount(accountRepository.getById(idAccount).orElseThrow(AccountNotFoundException::new));
            newMeterReading.setDate(now);
            newMeterReading.setReadings(dtoIn.readings());
        return readingsMapper.toMeterReadingDtoOut(readingsRepository.save(newMeterReading));
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
