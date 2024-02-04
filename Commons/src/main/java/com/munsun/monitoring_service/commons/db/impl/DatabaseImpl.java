package com.munsun.monitoring_service.commons.db.impl;

import com.munsun.monitoring_service.commons.db.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseImpl implements Database {
    private static Properties property = new Properties();
    private static final String pathToResources = "Commons/src/main/resources/app.properties";

    public DatabaseImpl(Properties properties) {
        property = properties;
    }

    public DatabaseImpl() {
        try {
            property.load(new FileReader(pathToResources));
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