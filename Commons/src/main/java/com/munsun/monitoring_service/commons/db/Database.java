package com.munsun.monitoring_service.commons.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.*;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandScope;
import liquibase.command.CommandStep;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.UpdateToTagCommandStep;
import liquibase.command.core.helpers.*;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static HikariDataSource dataSource;
    private static final Properties properties = new Properties();

    public static Connection getConnection() throws SQLException, IOException {
        if(dataSource == null) {
            loadProperty();
            var config = new HikariConfig();
                config.setJdbcUrl(properties.getProperty("datasource.url"));
                config.setUsername(properties.getProperty("datasource.username"));
                config.setPassword(properties.getProperty("datasource.password"));
                config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("datasource.pool_size")));
            dataSource = new HikariDataSource(config);
        }
        return dataSource.getConnection();
    }

    private static void loadProperty() {
        try (var reader = new FileReader("Commons/src/main/resources/app.properties")) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Database() {}
}