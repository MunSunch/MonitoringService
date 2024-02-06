package com.munsun.monitoring_service.commons.db.impl;

import com.munsun.monitoring_service.commons.db.Database;

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
    private Properties property = new Properties();
    private static final String PATH_TO_RESOURCES = "Commons/src/main/resources/app.properties";

    public DatabaseImpl(Properties properties) {
        property = properties;
    }

    public DatabaseImpl() {
        try {
            property.load(new FileReader(PATH_TO_RESOURCES));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(property.getProperty("datasource.url"),
                property.getProperty("datasource.username"),
                property.getProperty("datasource.password"));
    }
}