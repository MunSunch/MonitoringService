package com.munsun.monitoring_service.commons.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstraction for working with DBMS
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface Database {
    /**
     * Establish and return a connection to the DBMS
     * @return {@link Connection}
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;
}
