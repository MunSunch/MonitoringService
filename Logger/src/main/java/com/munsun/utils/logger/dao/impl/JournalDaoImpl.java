package com.munsun.utils.logger.dao.impl;

import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.utils.logger.dao.impl.queries.JournalQueries;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.dao.JournalDao;
import com.munsun.utils.logger.dao.impl.mapping.JdbcJournalMapper;
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
public class JournalDaoImpl implements JournalDao {
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
            var preparedStatement = connection.prepareStatement(JournalQueries.GET_ALL_JOURNALS.QUERY.getDescription()))
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
            var preparedStatement = connection.prepareStatement(JournalQueries.SAVE_JOURNAL.QUERY.getDescription()))
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
        try(var preparedStatement = connection.prepareStatement(JournalQueries.GET_ALL_JOURNALS.QUERY.getDescription(),
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
