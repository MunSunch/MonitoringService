package com.munsun.utils.logger.dao.impl.mapping.impl;

import com.munsun.utils.logger.dao.impl.queries.JournalQueries;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.dao.impl.enums.NamesColumnsTableJournals;
import com.munsun.utils.logger.dao.impl.mapping.JdbcJournalMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcJournalMapperImpl implements JdbcJournalMapper {
    @Override
    public JournalRecord toJournalRecord(ResultSet result) throws SQLException {
        var journalRecord = new JournalRecord();
            journalRecord.setId(result.getLong(NamesColumnsTableJournals.ID.getTitle()));
            journalRecord.setDate(result.getDate(NamesColumnsTableJournals.DATE.getTitle()));
            journalRecord.setMessage(result.getString(NamesColumnsTableJournals.MESSAGE.getTitle()));
        return journalRecord;
    }

    @Override
    public List<JournalRecord> toJournalsRecord(ResultSet res) throws SQLException {
        List<JournalRecord> journalRecords = new ArrayList<>();
        while(res.next()) {
            journalRecords.add(toJournalRecord(res));
        }
        return journalRecords;
    }

    @Override
    public void preparedSaveStatement(PreparedStatement preparedStatement, JournalRecord journalRecord) throws SQLException {
        preparedStatement.setDate(JournalQueries.SAVE_JOURNAL.Arguments.DATE.getPositionInQuery(), journalRecord.getDate());
        preparedStatement.setString(JournalQueries.SAVE_JOURNAL.Arguments.MESSAGE.getPositionInQuery(), journalRecord.getMessage());
    }
}
