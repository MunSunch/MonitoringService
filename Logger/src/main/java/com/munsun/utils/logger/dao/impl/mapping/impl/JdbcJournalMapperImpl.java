package com.munsun.utils.logger.dao.impl.mapping.impl;

import com.munsun.monitoring_service.commons.exceptions.MappingSqlEntityException;
import com.munsun.monitoring_service.commons.exceptions.ParseSqlQueryException;
import com.munsun.utils.logger.dao.impl.queries.JournalQueries;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.dao.impl.enums.NamesColumnsTableJournals;
import com.munsun.utils.logger.dao.impl.mapping.JdbcJournalMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcJournalMapperImpl implements JdbcJournalMapper {
    @Override
    public JournalRecord toJournalRecord(ResultSet result) {
        try {
            var journalRecord = new JournalRecord();
            journalRecord.setId(result.getLong(NamesColumnsTableJournals.ID.getTitle()));
            journalRecord.setDate(result.getDate(NamesColumnsTableJournals.DATE.getTitle()));
            journalRecord.setMessage(result.getString(NamesColumnsTableJournals.MESSAGE.getTitle()));
            return journalRecord;
        } catch (SQLException e) {
            throw new ParseSqlQueryException(e);
        }
    }

    @Override
    public List<JournalRecord> toJournalsRecord(ResultSet res){
        try {
            List<JournalRecord> journalRecords = new ArrayList<>();
            while (res.next()) {
                journalRecords.add(toJournalRecord(res));
            }
            return journalRecords;
        } catch (SQLException e) {
            throw new MappingSqlEntityException(e);
        }
    }

    @Override
    public void preparedSaveStatement(PreparedStatement preparedStatement, JournalRecord journalRecord) {
        try {
            preparedStatement.setDate(JournalQueries.SAVE_JOURNAL.Arguments.DATE.getPositionInQuery(), journalRecord.getDate());
            preparedStatement.setString(JournalQueries.SAVE_JOURNAL.Arguments.MESSAGE.getPositionInQuery(), journalRecord.getMessage());
        } catch (SQLException e) {
            throw new ParseSqlQueryException(e);
        }
    }
}