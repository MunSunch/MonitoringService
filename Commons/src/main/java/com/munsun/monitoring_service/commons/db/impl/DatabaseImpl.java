package com.munsun.monitoring_service.commons.db.impl;

import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.utils.PropertyService;
import com.munsun.monitoring_service.commons.utils.impl.PropertyServiceImpl;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
public class DatabaseImpl implements Database {
    private PropertyService property;

    public DatabaseImpl(PropertyService property) {
        this.property = property;
    }

    /**
     * Reconnect to the database. It is important that after use, the connection is closed using the close() method or specified in try-with-resources
     * @return {@link Connection connection}
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(property.getProperty("datasource.url"),
                property.getProperty("datasource.username"),
                property.getProperty("datasource.password"));
    }
}