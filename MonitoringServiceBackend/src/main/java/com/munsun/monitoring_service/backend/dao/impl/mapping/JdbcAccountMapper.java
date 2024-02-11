package com.munsun.monitoring_service.backend.dao.impl.mapping;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstraction for working with JDBC
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface JdbcAccountMapper {
    /**
     * Match the query results to the PlaceLivingEmbedded entity
     * @param result {@link ResultSet}
     * @return entity {@link PlaceLivingEmbedded}
     * @throws SQLException
     */
    Account toAccount(ResultSet result);

    /**
     * Put the values of the arguments in the account save request
     * @param preparedStatement {@link PreparedStatement}
     * @param account {@link Account}
     * @throws SQLException
     */
    void preparedSaveStatement(PreparedStatement preparedStatement, Account account);

    /**
     * Match the values of the arguments with the account search query by its username
     * @param preparedStatement {@link PreparedStatement}
     * @param login {@link String}
     * @throws SQLException
     */
    void preparedFindByLoginStatement(PreparedStatement preparedStatement, String login);

    /**
     * Match the values of the arguments with the account search query by its ID
     * @param preparedStatement {@link PreparedStatement}
     * @param key {@link Long}
     * @throws SQLException
     */
    void preparedFindByIdStatement(PreparedStatement preparedStatement, Long key);

    /**
     * Match the values of the arguments with the request to delete the account by its ID
     * @param preparedStatement {@link PreparedStatement}
     * @param key {@link Long}
     * @throws SQLException
     */
    void preparedDeleteByIdStatement(PreparedStatement preparedStatement, Long key);
}
