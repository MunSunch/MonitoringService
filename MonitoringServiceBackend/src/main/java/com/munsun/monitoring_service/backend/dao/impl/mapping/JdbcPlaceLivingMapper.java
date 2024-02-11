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
public interface JdbcPlaceLivingMapper {

    /**
     * Match the query results to the PlaceLivingEmbedded entity
     * @param result {@link ResultSet}
     * @return entity {@link PlaceLivingEmbedded}
     * @throws SQLException
     */
    PlaceLivingEmbedded toPlaceLiving(ResultSet result);

    /**
     * Put the values of the arguments in the account save request
     * @param preparedStatement {@link PreparedStatement}
     * @param account {@link Account}
     * @throws SQLException
     */
    void preparedSaveStatement(PreparedStatement preparedStatement, Account account);
}
