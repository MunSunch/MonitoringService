package com.munsun.utils.logger.tests.repositories;

import com.munsun.monitoring_service.commons.db.impl.MigrationSystem;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresContainer {
    private PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres");
    private Properties properties = new Properties();
    private static final String pathOtResources = "src/test/resources/test-app.properties";

    @BeforeEach
    public void initSchema() throws SQLException, FileNotFoundException, LiquibaseException {
        container.start();
        setProperty();
        new MigrationSystem(properties).initSchema(DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword()
        ));
    }

    private void setProperty() {
        try {
            properties.load(new FileReader(pathOtResources));
            properties.setProperty("datasource.url", container.getJdbcUrl());
            properties.setProperty("datasource.username", container.getUsername());
            properties.setProperty("datasource.password", container.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    @AfterEach
    public void stop() {
        container.stop();
    }
}
