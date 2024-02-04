package com.munsun.utils.logger.repositories.impl;

import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.repositories.JournalRepository;
import com.munsun.utils.logger.repositories.impl.queries.Query;
import com.munsun.utils.logger.repositories.impl.mapping.JdbcJournalMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * A class for storing and outputting Log entities from the database. The concept of a synthetic key is supported
 *
 * @author apple
 * @version $Id: $Id
 */
@RequiredArgsConstructor
public class JournalRepositoryImpl implements JournalRepository {
    private final Database database;
    private final JdbcJournalMapper mapper;

    /**
     * {@inheritDoc}
     *
     * Get a list of logs from the database
     */
    @Override
    public List<JournalRecord> getAllJournalRecords() {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_ALL_JOURNALS))
        {
            var res = preparedStatement.executeQuery();
            return mapper.toJournalsRecord(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * Save the new log to the database and return it from the database
     */
    @Override
    public JournalRecord save(JournalRecord journalRecord) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.SAVE_JOURNAL))
        {
            mapper.preparedSaveStatement(preparedStatement, journalRecord);
            preparedStatement.executeUpdate();
            return getLastRow(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JournalRecord getLastRow(Connection connection) {
        try(var preparedStatement = connection.prepareStatement(Query.GET_ALL_JOURNALS,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            var result = preparedStatement.executeQuery();
            result.absolute(-1);
            return mapper.toJournalRecord(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
