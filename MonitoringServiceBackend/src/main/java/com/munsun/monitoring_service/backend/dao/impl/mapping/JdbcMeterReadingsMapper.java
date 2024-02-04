package com.munsun.monitoring_service.backend.dao.impl.mapping;

import com.munsun.monitoring_service.backend.models.MeterReading;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface JdbcMeterReadingsMapper {
    void preparedSaveMeterReadingStatement(PreparedStatement preparedStatementSaveMeterReadings, MeterReading meterReading) throws SQLException;

    void preparedSaveReadingsStatement(PreparedStatement saveReadings, Map.Entry<String, Long> entrySet, Long idMeterReading) throws SQLException;

    MeterReading toMeterReading(ResultSet result) throws SQLException;

    List<MeterReading> toMetersReadings(ResultSet res) throws SQLException;
}
