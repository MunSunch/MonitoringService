package com.munsun.monitoring_service.commons.db.impl;

import com.munsun.monitoring_service.commons.db.impl.queries.Query;
import liquibase.Liquibase;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.UpdateToTagCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MigrationSystem {
    private Properties properties = new Properties();
    private static String pathToResources = "Commons/src/main/resources/app.properties";

    public MigrationSystem() {
        loadProperties();
    }

    public MigrationSystem(Properties properties) {
        this.properties = properties;
    }

    private void loadProperties() {
        try {
            properties.load(new FileReader(pathToResources));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initSchema(Connection connection) throws LiquibaseException, SQLException, FileNotFoundException {
        if(!checkExistsSchema(properties.getProperty("liquibase.default-schema"), connection))
            createSchema(properties.getProperty("liquibase.default-schema"), connection);
        if(!checkExistsSchema(properties.getProperty("datasource.default-schema"), connection))
            createSchema(properties.getProperty("datasource.default-schema"), connection);

        var database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(properties.getProperty("liquibase.changelog.path"),
                new ClassLoaderResourceAccessor(),
                database);
        liquibase.getDatabase().setDefaultSchemaName(properties.getProperty("liquibase.default-schema"));
        update(liquibase);

        liquibase.close();
        connection.close();
    }

    private boolean checkExistsSchema(String nameSchema, Connection connection) {
        try(var statement = connection.createStatement())
        {
            var res = statement.executeQuery(Query.isExistsSchemaInDatabase(nameSchema));
            return res.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private void createSchema(String nameSchema, Connection connection) {
        try(var statement = connection.createStatement())
        {
            statement.executeUpdate(Query.getQueryCreateNewSchema(nameSchema));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void update(Liquibase liquibase) throws CommandExecutionException, FileNotFoundException {
        CommandScope commandScope = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            commandScope.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, liquibase.getDatabase());
            commandScope.addArgumentValue(UpdateToTagCommandStep.CHANGELOG_FILE_ARG, liquibase.getChangeLogFile());
            commandScope.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DEFAULT_SCHEMA_NAME_ARG, liquibase.getDatabase().getDefaultSchemaName());
        commandScope.execute();
    }
}