package com.munsun.monitoring_service.backend.dao.impl;

import com.munsun.monitoring_service.backend.dao.MeterReadingsRepository;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableMeterReadings;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableReadings;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcMeterReadingsMapper;
import com.munsun.monitoring_service.backend.dao.impl.queries.Query;
import com.munsun.monitoring_service.backend.models.MeterReading;
import com.munsun.monitoring_service.commons.db.Database;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class MeterReadingsRepositoryImpl implements MeterReadingsRepository {
    private final Database database;
    private final JdbcMeterReadingsMapper mapper;

    @Override
    public Optional<MeterReading> getById(Long aLong) throws SQLException {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_METER_READING_BY_ID))
        {
            preparedStatement.setLong(NamesColumnsTableMeterReadings.ID.ordinal()+1, aLong);
            var result = preparedStatement.executeQuery();
            if(result.next()) {
                return Optional.of(mapper.toMeterReading(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Long save(MeterReading meterReading) throws SQLException {
        try(var connection = database.getConnection();
            var saveMeterReading = connection.prepareStatement(Query.SAVE_METER_READING))
        {
            connection.setAutoCommit(false);
            mapper.preparedSaveMeterReadingStatement(saveMeterReading, meterReading);
            int res = saveMeterReading.executeUpdate();
            Long idMeterReading = getIdSavedMeterReading(connection);
            for (var entrySet: meterReading.getReadings().entrySet()) {
                saveReadings(connection, entrySet, idMeterReading);
            }
            connection.commit();
            return idMeterReading;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private Long getIdSavedMeterReading(Connection connection) {
        try(var prepared =
                    connection.prepareStatement(Query.GET_LAST_SAVED_METER_READINGS))
        {
            var res = prepared.executeQuery();
            if(res.next()) {
                return res.getLong(NamesColumnsTableMeterReadings.ID.getTitle());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private void saveReadings(Connection connection, Map.Entry<String, Long> entrySet, Long idMeterReading) {
        try(var saveReadings = connection.prepareStatement(Query.SAVE_READING)) {
            mapper.preparedSaveReadingsStatement(saveReadings, entrySet, idMeterReading);
            saveReadings.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // cascade delete
    @Override
    public Integer deleteById(Long aLong) throws SQLException {
        try(var connection = database.getConnection();
            var deleteReadings = connection.prepareStatement(Query.DELETE_READING_BY_ID);
            var deleteMeterReading = connection.prepareStatement(Query.DELETE_METER_READING_BY_ID))
        {
            connection.setAutoCommit(false);

            deleteReadings.setLong(NamesColumnsTableReadings.METER_READINGS_ID.ordinal(), aLong);
            deleteReadings.executeUpdate();

            deleteMeterReading.setLong(NamesColumnsTableMeterReadings.ID.ordinal()+1, aLong);
            int res = deleteMeterReading.executeUpdate();

            connection.commit();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Optional<MeterReading> getLastMeterReadingByAccount_Id(Long idAccount) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH))
        {
            var nowMonth = new Date().getMonth()+1;
            preparedStatement.setLong(1, nowMonth);
            preparedStatement.setLong(2, idAccount);
            var res = preparedStatement.executeQuery();
            if(res.next()) {
                return Optional.of(mapper.toMeterReading(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<MeterReading> getAllMeterReadingsByAccount_Id(Long idAccount) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_METER_READINGS_BY_ACCOUNT_ID,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            preparedStatement.setLong(1, idAccount);
            var res = preparedStatement.executeQuery();
            if(res.next()) {
                return mapper.toMetersReadings(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Optional<MeterReading> getMeterReadingsByMonthAndAccount_Id(Long idAccount, Month month) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH))
        {
            preparedStatement.setLong(1, month.getValue());
            preparedStatement.setLong(2, idAccount);
            var res = preparedStatement.executeQuery();
            if(res.next()) {
                return Optional.of(mapper.toMeterReading(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<MeterReading> getLastMetersReadingsAllAccounts() {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            var nowMonth = new Date().getMonth()+1;
            preparedStatement.setLong(1, nowMonth);
            var res = preparedStatement.executeQuery();
            if(res.next()) {
                return mapper.toMetersReadings(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<MeterReading> getAllMeterReadingsAllAccounts() {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_ALL_METER_READINGS_ALL_ACCOUNTS,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            var res = preparedStatement.executeQuery();
            if(res.next()) {
                return mapper.toMetersReadings(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<MeterReading> getAllMeterReadings_MonthAllAccounts(Month month) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            preparedStatement.setLong(1, month.getValue());
            var res = preparedStatement.executeQuery();
            if(res.next()) {
                return mapper.toMetersReadings(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
