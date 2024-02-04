package com.munsun.monitoring_service.backend.repositories.impl;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.backend.repositories.MeterReadingsRepository;

import java.time.Month;
import java.util.stream.Collectors;

import java.util.*;

/**
 * <p>MeterReadingsRepositoryImpl class.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
public class MeterReadingsRepositoryImpl implements MeterReadingsRepository {
    private final Map<Long, MeterReading> listReadings = new HashMap<>();
    private Long counterId = 0L;

    /** {@inheritDoc} */
    @Override
    public Optional<MeterReading> getById(Long id) {
        return Optional.of(listReadings.get(id));
    }

    /** {@inheritDoc} */
    @Override
    public MeterReading save(MeterReading meterReading) throws DatabaseConstraintException {
        meterReading.setId(counterId);
        listReadings.put(counterId++, meterReading);
        return meterReading;
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MeterReading> deleteById(Long id) {
        return Optional.of(listReadings.remove(id));
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReading> getLastMetersReadingsAllAccounts() {
        Date now = new Date();
        return listReadings.values().stream()
                .filter(x -> x.getDate().getMonth() == now.getMonth())
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MeterReading> getLastMeterReadingByAccount_Id(Long idAccount) {
        return listReadings.values().stream()
                    .filter(x -> x.getAccount().getId() == idAccount)
                    .max((x, y) -> x.getDate().compareTo(y.getDate()));
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReading> getMeterReadingsByMonthAllAccounts(Month month) {
        return listReadings.values().stream()
                .filter(x -> (x.getDate().getMonth()+1)==month.getValue())
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReading> getAllMeterReadingsAllAccounts() {
        return new ArrayList<>(listReadings.values());
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReading> getMeterReadingsByMonthAndAccount_Id(Long idAccount, Month month) {
        return listReadings.values().stream()
                .filter(x -> x.getAccount().getId() == idAccount
                            && (x.getDate().getMonth()+1)==month.getValue())
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public List<MeterReading> getAllMeterReadingsByAccount_Id(Long idAccount) {
        return listReadings.values().stream()
                .filter(x -> x.getAccount().getId() == idAccount)
                .collect(Collectors.toList());
    }
}
