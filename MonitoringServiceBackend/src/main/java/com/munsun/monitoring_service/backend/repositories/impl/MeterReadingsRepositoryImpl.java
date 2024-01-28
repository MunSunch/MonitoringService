package com.munsun.monitoring_service.backend.repositories.impl;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.repositories.MeterReadingsRepository;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MeterReadingsRepositoryImpl implements MeterReadingsRepository {
    private final Map<Long, MeterReading> listReadings;
    private static Long COUNTER_ID = 0L;

    public MeterReadingsRepositoryImpl() {
        this.listReadings = new HashMap<>();
    }

    @Override
    public Optional<MeterReading> getById(Long id) {
        return Optional.of(listReadings.get(id));
    }

    @Override
    public MeterReading save(MeterReading meterReading) throws DatabaseConstraintException {
        meterReading.setId(COUNTER_ID);
        listReadings.put(COUNTER_ID++, meterReading);
        return meterReading;
    }

    @Override
    public Optional<MeterReading> deleteById(Long id) {
        return Optional.of(listReadings.remove(id));
    }

    @Override
    public List<MeterReading> getLastMetersReadingsAllAccounts() {
        Date now = new Date();
        return listReadings.values().stream()
                .filter(x -> x.getDate().getMonth() == now.getMonth())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MeterReading> getLastMeterReadingByAccount_Id(Long idAccount) {
        return listReadings.values().stream()
                    .filter(x -> x.getAccount().getId() == idAccount)
                    .max((x, y) -> x.getDate().compareTo(y.getDate()));
    }

    @Override
    public List<MeterReading> getMeterReadingsByMonthAllAccounts(Month month) {
        return listReadings.values().stream()
                .filter(x -> (x.getDate().getMonth()+1)==month.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public List<MeterReading> getAllMeterReadingsAllAccounts() {
        return new ArrayList<>(listReadings.values());
    }

    @Override
    public List<MeterReading> getMeterReadingsByMonthAndAccount_Id(Long idAccount, Month month) {
        return listReadings.values().stream()
                .filter(x -> x.getAccount().getId() == idAccount
                            && (x.getDate().getMonth()+1)==month.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public List<MeterReading> getAllMeterReadingsByAccount_Id(Long idAccount) {
        return listReadings.values().stream()
                .filter(x -> x.getAccount().getId() == idAccount)
                .collect(Collectors.toList());
    }
}
