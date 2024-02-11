package com.munsun.monitoring_service.backend.dao.impl;

import com.munsun.monitoring_service.backend.dao.MeterReadingsDao;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableMeterReadings;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcMeterReadingsMapper;
import com.munsun.monitoring_service.backend.dao.impl.queries.MeterReadingsQueries;
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
public class MeterReadingsDaoImpl implements MeterReadingsDao {
    private final Database database;
    private final JdbcMeterReadingsMapper mapper;

    @Override
    public Optional<MeterReading> getById(Long aLong) throws SQLException {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(MeterReadingsQueries.GET_METER_READING_BY_ID.QUERY.getDescription()))
        {
            mapper.preparedGetByIdStatement(preparedStatement, aLong);
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
            var saveMeterReading = connection.prepareStatement(MeterReadingsQueries.SAVE_METER_READING.QUERY.getDescription()))
        {
            connection.setAutoCommit(false);
            mapper.preparedSaveMeterReadingStatement(saveMeterReading, meterReading);
            saveMeterReading.executeUpdate();
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
                    connection.prepareStatement(MeterReadingsQueries.GET_LAST_SAVED_METER_READINGS.QUERY.getDescription()))
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
        try(var saveReadings = connection.prepareStatement(MeterReadingsQueries.SAVE_READING.QUERY.getDescription())) {
            mapper.preparedSaveReadingsStatement(saveReadings, entrySet, idMeterReading);
            saveReadings.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer deleteById(Long aLong) throws SQLException {
        try(var connection = database.getConnection();
            var deleteReadings = connection.prepareStatement(MeterReadingsQueries.DELETE_READING_BY_ID.QUERY.getDescription());
            var deleteMeterReading = connection.prepareStatement(MeterReadingsQueries.DELETE_METER_READING_BY_ID.QUERY.getDescription()))
        {
            connection.setAutoCommit(false);

            mapper.preparedDeleteReadingById(deleteReadings, aLong);
            deleteReadings.executeUpdate();
            mapper.preparedDeleteMeterReadingById(deleteMeterReading, aLong);
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
            var preparedStatement = connection.prepareStatement(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH.QUERY.getDescription()))
        {
            var nowMonth = new Date().getMonth()+1;
            mapper.preparedGetLastMeterReadingStatement(preparedStatement, idAccount, nowMonth);
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
            var preparedStatement = connection.prepareStatement(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID.QUERY.getDescription(),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            mapper.preparedGetAllMetersReadingsByAccountId(preparedStatement, idAccount);
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
            var preparedStatement = connection.prepareStatement(MeterReadingsQueries.GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH.QUERY.getDescription()))
        {
            mapper.preparedGetMeterReadingsByAccountIdAndMonth(preparedStatement, idAccount, month);
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
            var preparedStatement = connection.prepareStatement(MeterReadingsQueries.GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH.QUERY.getDescription(),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            var nowMonth = new Date().getMonth()+1;
            mapper.preparedGetAllMetersReadingsByMonth(preparedStatement, Month.of(nowMonth));
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
            var preparedStatement = connection.prepareStatement(MeterReadingsQueries.GET_ALL_METER_READINGS_ALL_ACCOUNTS.QUERY.getDescription(),
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
            var preparedStatement = connection.prepareStatement(MeterReadingsQueries.GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH.QUERY.getDescription(),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            mapper.preparedGetAllMetersReadingsByMonth(preparedStatement, month);
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
