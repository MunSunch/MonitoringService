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

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static HikariDataSource dataSource;

    private static final Integer PORT = 8081;
    private static final Integer POOL_SIZE = 32;
    private static final String NAME_DATABASE = "root";
    private static final String NAME_SCHEMA = "monitoring_service";
    private static final String POSTGRES_USERNAME = "root";
    private static final String POSTGRES_PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        if(dataSource == null) {
            var config = new HikariConfig();
                config.setJdbcUrl( "jdbc:postgresql://localhost:" + PORT + "/" + NAME_DATABASE);
                config.setUsername(POSTGRES_USERNAME);
                config.setPassword(POSTGRES_PASSWORD);
                config.setMaximumPoolSize(POOL_SIZE);
            dataSource = new HikariDataSource(config);
        }
        return dataSource.getConnection();
    }

    private Database() {}
}