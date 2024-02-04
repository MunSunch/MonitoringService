package com.munsun.monitoring_service.backend.dao.impl.mapping.impl;

import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableMeterReadings;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableReadings;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcAccountMapper;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcMeterReadingsMapper;
import com.munsun.monitoring_service.backend.models.MeterReading;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcMeterReadingsMapperImpl implements JdbcMeterReadingsMapper {
    private final JdbcAccountMapper jdbcAccountMapper;

    @Override
    public void preparedSaveMeterReadingStatement(PreparedStatement preparedStatementSaveMeterReadings, MeterReading meterReading) throws SQLException {
        preparedStatementSaveMeterReadings.setDate(NamesColumnsTableMeterReadings.DATE.ordinal(), meterReading.getDate());
        preparedStatementSaveMeterReadings.setLong(NamesColumnsTableMeterReadings.ACCOUNT_ID.ordinal(), meterReading.getAccount().getId());
    }

    @Override
    public void preparedSaveReadingsStatement(PreparedStatement saveReadings, Map.Entry<String, Long> entrySet, Long idMeterReading) throws SQLException {
        saveReadings.setLong(NamesColumnsTableReadings.METER_READINGS_ID.ordinal(), idMeterReading);
        saveReadings.setString(NamesColumnsTableReadings.NAME.ordinal(), entrySet.getKey());
        saveReadings.setLong(NamesColumnsTableReadings.VALUE.ordinal(), entrySet.getValue());
    }

    @Override
    public List<MeterReading> toMetersReadings(ResultSet res) throws SQLException {
        List<MeterReading> meterReadings = new ArrayList<>();
        do {
            meterReadings.add(toMeterReading(res));
            res.previous();
        } while(res.next());
        return meterReadings;
    }

    @Override
    public MeterReading toMeterReading(ResultSet result) throws SQLException {
        var meterReading = new MeterReading();
            meterReading.setId(result.getLong(NamesColumnsTableMeterReadings.ID.getTitle()));
            meterReading.setDate(result.getDate(NamesColumnsTableMeterReadings.DATE.getTitle()));
            meterReading.setAccount(jdbcAccountMapper.toAccount(result));
            meterReading.setReadings(toReadings(result, meterReading.getId()));
        return meterReading;
    }

    private Map<String, Long> toReadings(ResultSet result, Long idMeterReading) throws SQLException {
        Map<String, Long> meterReadings = new HashMap<>();
        Long currentIdMeterReading;
        do {
            currentIdMeterReading = Long.parseLong(result.getString(NamesColumnsTableMeterReadings.ID.getTitle()));
            if(!currentIdMeterReading.equals(idMeterReading))
                break;
            meterReadings.put(result.getString(NamesColumnsTableReadings.NAME.getTitle()),
                              result.getLong(NamesColumnsTableReadings.VALUE.getTitle()));
        } while(result.next());
        return meterReadings;
    }
}
