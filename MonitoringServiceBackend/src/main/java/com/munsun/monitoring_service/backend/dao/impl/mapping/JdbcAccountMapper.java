package com.munsun.monitoring_service.backend.dao.impl.mapping;

import com.munsun.monitoring_service.backend.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcAccountMapper {
    Account toAccount(ResultSet result) throws SQLException;
    void preparedSaveStatement(PreparedStatement preparedStatement, Account account) throws SQLException;
    void preparedFindByLoginStatement(PreparedStatement preparedStatement, String login) throws SQLException;
    void preparedFindByIdStatement(PreparedStatement preparedStatement, Long key) throws SQLException;
    void preparedDeleteByIdStatement(PreparedStatement preparedStatement, Long key) throws SQLException;
}
