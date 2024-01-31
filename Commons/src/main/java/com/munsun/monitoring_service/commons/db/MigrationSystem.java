package com.munsun.monitoring_service.commons.db;

import com.munsun.monitoring_service.commons.db.queries.Query;
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

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MigrationSystem {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileReader("Commons/src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSchema(String nameSchema) {
        try(var connection = Database.getConnection();
            var statement = connection.createStatement())
        {
            statement.executeQuery(Query.getQueryCreateNewSchema(nameSchema));
        } catch (SQLException e) {
            System.out.println("Error create schema!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkExistsSchema(String nameSchema) {
        try(var connection = Database.getConnection();
            var statement = connection.createStatement())
        {
            var res = statement.executeQuery(Query.getQueryCreateNewSchema(nameSchema));
            return res.next();
        } catch (SQLException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void initSchema(Connection connection) throws LiquibaseException, SQLException {
        if(!checkExistsSchema(properties.getProperty("liquibase.default-schema")))
            createSchema(properties.getProperty("liquibase.default-schema"));

        var database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(properties.getProperty("liquibase.changelog.path"),
                new ClassLoaderResourceAccessor(),
                database);
        update(liquibase);

        liquibase.close();
        connection.close();
    }

    private static void update(Liquibase liquibase) throws CommandExecutionException {
        CommandScope commandScope = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            commandScope.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, liquibase.getDatabase());
            commandScope.addArgumentValue(UpdateToTagCommandStep.CHANGELOG_FILE_ARG, liquibase.getChangeLogFile());
        commandScope.execute();
    }
}