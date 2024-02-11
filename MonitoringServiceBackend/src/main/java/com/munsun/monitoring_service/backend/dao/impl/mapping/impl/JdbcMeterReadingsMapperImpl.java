package com.munsun.monitoring_service.backend.dao.impl.mapping.impl;

import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableMeterReadings;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableReadings;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcAccountMapper;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcMeterReadingsMapper;
import com.munsun.monitoring_service.backend.dao.impl.queries.MeterReadingsQueries;
import com.munsun.monitoring_service.backend.models.MeterReading;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcMeterReadingsMapperImpl implements JdbcMeterReadingsMapper {
    private final JdbcAccountMapper jdbcAccountMapper;

    @Override
    public void preparedSaveMeterReadingStatement(PreparedStatement preparedStatementSaveMeterReadings, MeterReading meterReading) throws SQLException {
        preparedStatementSaveMeterReadings.setDate(MeterReadingsQueries.SAVE_METER_READING.Arguments.DATE.getPositionInQuery(), meterReading.getDate());
        preparedStatementSaveMeterReadings.setLong(MeterReadingsQueries.SAVE_METER_READING.Arguments.ACCOUNT_ID.getPositionInQuery(), meterReading.getAccount().getId());
    }

    @Override
    public void preparedSaveReadingsStatement(PreparedStatement saveReadings, Map.Entry<String, Long> entrySet, Long idMeterReading) throws SQLException {
        saveReadings.setLong(MeterReadingsQueries.SAVE_READING.Arguments.METER_READINGS_ID.getPositionInQuery(), idMeterReading);
        saveReadings.setString(MeterReadingsQueries.SAVE_READING.Arguments.NAME.getPositionInQuery(), entrySet.getKey());
        saveReadings.setLong(MeterReadingsQueries.SAVE_READING.Arguments.VALUE.getPositionInQuery(), entrySet.getValue());
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

    @Override
    public void preparedGetByIdStatement(PreparedStatement preparedStatement, Long aLong) throws SQLException {
        preparedStatement.setLong(MeterReadingsQueries.GET_METER_READING_BY_ID.Arguments.ID.getPositionInQuery(), aLong);
    }

    @Override
    public void preparedDeleteReadingById(PreparedStatement deleteReadings, Long aLong) throws SQLException {
        deleteReadings.setLong(MeterReadingsQueries.DELETE_READING_BY_ID.Arguments.METER_READINGS_ID.getPositionInQuery(), aLong);
    }

    @Override
    public void preparedDeleteMeterReadingById(PreparedStatement deleteMeterReading, Long aLong) throws SQLException {
        deleteMeterReading.setLong(MeterReadingsQueries.DELETE_METER_READING_BY_ID.Arguments.ID.getPositionInQuery(), aLong);
    }

    @Override
    public void preparedGetLastMeterReadingStatement(PreparedStatement preparedStatement, Long idAccount, int nowMonth) throws SQLException {
        preparedStatement.setLong(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH.Arguments.MONTH.getPositionInQuery(), nowMonth);
        preparedStatement.setLong(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH.Arguments.ID.getPositionInQuery(), idAccount);
    }

    @Override
    public void preparedGetAllMetersReadingsByAccountId(PreparedStatement preparedStatement, Long idAccount) throws SQLException {
        preparedStatement.setLong(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID.Arguments.ID.getPositionInQuery(), idAccount);
    }

    @Override
    public void preparedGetAllMetersReadingsByMonth(PreparedStatement preparedStatement, Month month) throws SQLException {
        preparedStatement.setLong(MeterReadingsQueries.GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH.Arguments.MONTH.getPositionInQuery(), month.getValue());
    }

    @Override
    public void preparedGetMeterReadingsByAccountIdAndMonth(PreparedStatement preparedStatement, Long idAccount, Month month) throws SQLException {
        preparedStatement.setLong(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH.Arguments.MONTH.getPositionInQuery(), month.getValue());
        preparedStatement.setLong(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH.Arguments.ID.getPositionInQuery(), idAccount);
    }
}