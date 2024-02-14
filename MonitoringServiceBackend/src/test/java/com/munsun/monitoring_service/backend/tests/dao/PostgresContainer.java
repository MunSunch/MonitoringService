package com.munsun.monitoring_service.backend.tests.dao;

import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.db.impl.DatabaseImpl;
import com.munsun.monitoring_service.commons.db.impl.MigrationSystem;
import com.munsun.monitoring_service.commons.exceptions.InitSchemaLiquibaseException;
import com.munsun.monitoring_service.commons.utils.property.PropertyService;
import com.munsun.monitoring_service.commons.utils.property.impl.PropertyServiceImpl;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class PostgresContainer {
    private PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres");
    private PropertyService properties = new PropertyServiceImpl(PostgresContainer.class);
    private Database database = new DatabaseImpl(properties);

    @BeforeEach
    public void initSchema() throws SQLException, FileNotFoundException, LiquibaseException, InitSchemaLiquibaseException {
        container.start();

        properties.setProperty("datasource.url", container.getJdbcUrl());
        properties.setProperty("datasource.username", container.getUsername());
        properties.setProperty("datasource.password", container.getPassword());

        new MigrationSystem(properties).initSchema(database.getConnection());
    }

    @AfterEach
    public void stop() {
        container.stop();
    }

    public Database getDatabase() {
        return database;
    }
}
