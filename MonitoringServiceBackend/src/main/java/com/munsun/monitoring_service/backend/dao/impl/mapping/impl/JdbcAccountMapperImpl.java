package com.munsun.monitoring_service.backend.dao.impl.mapping.impl;

import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcPlaceLivingMapper;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcAccountMapper;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableAccounts;
import com.munsun.monitoring_service.backend.dao.impl.queries.AccountsQueries;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.enums.Role;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class JdbcAccountMapperImpl implements JdbcAccountMapper {
    private final JdbcPlaceLivingMapper placeLivingMapper;

    @Override
    public Account toAccount(ResultSet result) throws SQLException {
        var account = Account.builder()
                .id(result.getLong(NamesColumnsTableAccounts.ID.getTitle()))
                .login(result.getString(NamesColumnsTableAccounts.LOGIN.getTitle()))
                .password(result.getString(NamesColumnsTableAccounts.PASSWORD.getTitle()))
                .placeLiving(placeLivingMapper.toPlaceLiving(result))
                .role(Role.valueOf(result.getString(NamesColumnsTableAccounts.ROLE.getTitle())))
                .isBlocked(result.getBoolean(NamesColumnsTableAccounts.IS_BLOCKED.getTitle()))
                .build();
        return account;
    }

    @Override
    public void preparedSaveStatement(PreparedStatement preparedStatement, Account account) throws SQLException {
        preparedStatement.setString(AccountsQueries.SAVE_ACCOUNT.Arguments.LOGIN.getPositionInQuery(),
                                    String.valueOf(account.getLogin()));
        preparedStatement.setString(AccountsQueries.SAVE_ACCOUNT.Arguments.PASSWORD.getPositionInQuery(),
                                    String.valueOf(account.getPassword()));
        preparedStatement.setString(AccountsQueries.SAVE_ACCOUNT.Arguments.ROLE.getPositionInQuery(),
                                    String.valueOf(account.getRole()));
        preparedStatement.setBoolean(AccountsQueries.SAVE_ACCOUNT.Arguments.IS_BLOCKED.getPositionInQuery(),
                                     account.isBlocked());
        placeLivingMapper.preparedSaveStatement(preparedStatement, account);
    }

    @Override
    public void preparedFindByLoginStatement(PreparedStatement preparedStatement, String login) throws SQLException {
        preparedStatement.setString(AccountsQueries.FIND_ACCOUNT_BY_LOGIN.Arguments.LOGIN.getPositionInQuery(), login);
    }

    @Override
    public void preparedFindByIdStatement(PreparedStatement preparedStatement, Long key) throws SQLException {
        preparedStatement.setLong(AccountsQueries.GET_ACCOUNT_BY_ID.Arguments.ID.getPositionInQuery(), key);
    }

    @Override
    public void preparedDeleteByIdStatement(PreparedStatement preparedStatement, Long key) throws SQLException {
        preparedStatement.setLong(AccountsQueries.DELETE_BY_ID.Arguments.ID.getPositionInQuery(), key);
    }
}
