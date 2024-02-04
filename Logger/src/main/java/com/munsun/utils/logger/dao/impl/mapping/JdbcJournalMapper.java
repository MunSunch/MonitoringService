package com.munsun.utils.logger.dao.impl.mapping;

import com.munsun.utils.logger.model.JournalRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface JdbcJournalMapper {
    JournalRecord toJournalRecord(ResultSet result) throws SQLException;

    void preparedSaveStatement(PreparedStatement preparedStatement, JournalRecord journalRecord) throws SQLException;

    List<JournalRecord> toJournalsRecord(ResultSet res) throws SQLException;
}
