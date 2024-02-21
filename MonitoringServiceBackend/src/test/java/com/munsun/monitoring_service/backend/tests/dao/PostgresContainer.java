package com.munsun.monitoring_service.backend.tests.dao;

import com.munsun.monitoring_service.commons.exceptions.InitSchemaLiquibaseException;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class PostgresContainer {
    private PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres");

    @BeforeEach
    public void initSchema() throws SQLException, FileNotFoundException, LiquibaseException, InitSchemaLiquibaseException {
        container.start();
    }

    @AfterEach
    public void stop() {
        container.stop();
    }
}
