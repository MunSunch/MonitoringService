package com.munsun.monitoring_service.backend.dao.impl;

import com.munsun.monitoring_service.backend.dao.impl.queries.Query;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcAccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.dao.AccountRepository;
import com.munsun.monitoring_service.commons.db.Database;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.Optional;

/**
 * <p>AccountRepositoryImpl class.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final Database database;
    private final JdbcAccountMapper mapper;

    /** {@inheritDoc} */
    @Override
    public Optional<Account> findByAccount_Login(String login) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.FIND_ACCOUNT_BY_LOGIN))
        {
            mapper.preparedFindByLoginStatement(preparedStatement, login);
            var result = preparedStatement.executeQuery();
            if(!result.next()){
                return Optional.empty();
            }
            return Optional.of(mapper.toAccount(result));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Account> getById(Long key) {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.GET_ACCOUNT_BY_ID);)
        {
            mapper.preparedFindByIdStatement(preparedStatement, key);
            var res = preparedStatement.executeQuery();
            if(!res.next()) {
                return Optional.empty();
            }
            return Optional.of(mapper.toAccount(res));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /** {@inheritDoc}
     * @return*/
    @Override
    public Long save(Account account) throws SQLException {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.SAVE_ACCOUNT))
        {
            mapper.preparedSaveStatement(preparedStatement, account);
            int res = preparedStatement.executeUpdate();
            return (long) res;
        } catch (SQLException e) {
            throw e;
        }
    }

    /** {@inheritDoc}
     * @return*/
    @Override
    public Integer deleteById(Long key) throws SQLException {
        try(var connection = database.getConnection();
            var preparedStatement = connection.prepareStatement(Query.DELETE_BY_ID))
        {
            mapper.preparedDeleteByIdStatement(preparedStatement, key);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
