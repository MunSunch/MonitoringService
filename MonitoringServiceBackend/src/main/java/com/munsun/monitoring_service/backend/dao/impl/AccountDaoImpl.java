package com.munsun.monitoring_service.backend.dao.impl;

import com.munsun.monitoring_service.backend.dao.impl.queries.AccountsQueries;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcAccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.exceptions.DatabaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

/**
 * <p>AccountRepositoryImpl class.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@Repository
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {
    private final Database database;
    private final JdbcAccountMapper mapper;

    /** {@inheritDoc} */
    @Override
    public Optional<Account> findByAccount_Login(String login) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(AccountsQueries.FIND_ACCOUNT_BY_LOGIN.QUERY.getDescription()))
        {
            mapper.preparedFindByLoginStatement(preparedStatement, login);
            var result = preparedStatement.executeQuery();
            if(!result.next()){
                return Optional.empty();
            }
            return Optional.of(mapper.toAccount(result));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Account> getById(Long key) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(AccountsQueries.GET_ACCOUNT_BY_ID.QUERY.getDescription()))
        {
            mapper.preparedFindByIdStatement(preparedStatement, key);
            var res = preparedStatement.executeQuery();
            if(!res.next()) {
                return Optional.empty();
            }
            return Optional.of(mapper.toAccount(res));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /** {@inheritDoc}
     * @return*/
    @Override
    public Long save(Account account) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(AccountsQueries.SAVE_ACCOUNT.QUERY.getDescription()))
        {
            mapper.preparedSaveStatement(preparedStatement, account);
            int res = preparedStatement.executeUpdate();
            return (long) res;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /** {@inheritDoc}
     * @return*/
    @Override
    public Integer deleteById(Long key) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(AccountsQueries.DELETE_BY_ID.QUERY.getDescription()))
        {
            mapper.preparedDeleteByIdStatement(preparedStatement, key);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
