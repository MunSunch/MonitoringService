package com.munsun.monitoring_service.backend.dao.impl.mapping;

import com.munsun.monitoring_service.backend.models.MeterReading;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.List;
import java.util.Map;

/**
 * Abstraction for working with JDBC
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface JdbcMeterReadingsMapper {
    /**
     * Match the query results with the MeterReading entity
     * @param result {@link ResultSet}
     * @return entity {@link MeterReading}
     * @throws SQLException
     */
    MeterReading toMeterReading(ResultSet result) throws SQLException;

    /**
     * Match the query results with a list of MeterReading entities
     * @param result {@link ResultSet}
     * @return entity {@link List<MeterReading>}
     * @throws SQLException
     */
    List<MeterReading> toMetersReadings(ResultSet result) throws SQLException;

    /**
     * Match argument values with a request to save counter metrics
     * @param preparedStatementSaveMeterReadings {@link PreparedStatement}
     * @param meterReading {@link MeterReading}
     * @throws SQLException
     */
    void preparedSaveMeterReadingStatement(PreparedStatement preparedStatementSaveMeterReadings, MeterReading meterReading) throws SQLException;

    /**
     * Compare the values of the arguments with the request to save the indicators
     * @param saveReadings {@link PreparedStatement}
     * @param entrySet {@link Map.Entry<String, Long>}
     * @param idMeterReading {@link Long}
     * @throws SQLException
     */
    void preparedSaveReadingsStatement(PreparedStatement saveReadings, Map.Entry<String, Long> entrySet, Long idMeterReading) throws SQLException;

    /**
     * To match the values of the arguments with a request to receive meter readings by its identifier
     * @param preparedStatement {@link PreparedStatement}
     * @param aLong {@link Long}
     * @throws SQLException
     */
    void preparedGetByIdStatement(PreparedStatement preparedStatement, Long aLong) throws SQLException;

    /**
     * To match the values of the arguments with the request to delete readings by the identifier of the meter indicator
     * @param deleteReadings {@link PreparedStatement}
     * @param aLong {@link Long}
     * @throws SQLException
     */
    void preparedDeleteReadingById(PreparedStatement deleteReadings, Long aLong) throws SQLException;

    /**
     * To match the values of the arguments with a request to delete meter readings by its identifier
     * @param deleteMeterReading {@link PreparedStatement}
     * @param aLong {@link Long}
     * @throws SQLException
     */
    void preparedDeleteMeterReadingById(PreparedStatement deleteMeterReading, Long aLong) throws SQLException;

    /**
     * To match the values of the arguments with a request to receive current meter readings by account ID
     * @param preparedStatement {@link PreparedStatement}
     * @param idAccount {@link Long}
     * @param nowMonth int
     * @throws SQLException
     */
    void preparedGetLastMeterReadingStatement(PreparedStatement preparedStatement, Long idAccount, int nowMonth) throws SQLException;

    /**
     * Match the values of the arguments with a request to get meter readings by account ID
     * @param preparedStatement {@link PreparedStatement}
     * @param idAccount {@link Long}
     * @throws SQLException
     */
    void preparedGetAllMetersReadingsByAccountId(PreparedStatement preparedStatement, Long idAccount) throws SQLException;

    /**
     * Compare the values of the arguments with a request to receive meter readings for all accounts by month
     * @param preparedStatement {@link PreparedStatement}
     * @param month {@link Month}
     * @throws SQLException
     */
    void preparedGetAllMetersReadingsByMonth(PreparedStatement preparedStatement, Month month) throws SQLException;

    /**
     * Compare the values of the arguments with the request to get meter readings by account ID and month
     * @param preparedStatement {@link PreparedStatement}
     * @param idAccount {@link Long}
     * @param month {@link Month}
     * @throws SQLException
     */
    void preparedGetMeterReadingsByAccountIdAndMonth(PreparedStatement preparedStatement, Long idAccount, Month month) throws SQLException;
}
