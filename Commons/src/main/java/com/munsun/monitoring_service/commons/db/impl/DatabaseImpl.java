package com.munsun.monitoring_service.commons.db.impl;

import com.munsun.monitoring_service.commons.db.Database;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database interface implementation.
 * The work requires paperwork related to the distribution.property. Required list of properties:
 * <ul>
 * <li>data source.url</li>
 * <li>data source.username</li>
 * <li>data source.password</li>
 * <li>data source.the default scheme</li>
 * </ul
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@Component
public class DatabaseImpl implements Database {
    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String user;

    @Value("${datasource.password}")
    private String password;

    /**
     * Reconnect to the database. It is important that after use, the connection is closed using the close() method or specified in try-with-resources
     * @return {@link Connection connection}
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}