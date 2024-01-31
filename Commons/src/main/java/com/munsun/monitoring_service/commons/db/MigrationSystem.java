package com.munsun.monitoring_service.commons.db;

import com.munsun.monitoring_service.commons.db.queries.Query;
import liquibase.Liquibase;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.UpdateToTagCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

public class MigrationSystem {
    private static final String NAME_SCHEMA = "monitoring_service";

    private static void createSchema(String nameSchema) {
        try(var connection = Database.getConnection();
            var statement = connection.createStatement())
        {
            statement.executeQuery(Query.getQueryCreateNewSchema(nameSchema));
        } catch (SQLException e) {
            System.out.println("Error create schema!");
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
        }
    }

    public static void initSchema(Connection connection) throws LiquibaseException, SQLException {
        if(!checkExistsSchema(NAME_SCHEMA))
            createSchema(NAME_SCHEMA);

        var database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("db.changelogs/db.changelog-master.xml",
                new ClassLoaderResourceAccessor(),
                database);
        liquibase.setShowSummary(UpdateSummaryEnum.OFF);
        liquibase.setShowSummaryOutput(UpdateSummaryOutputEnum.ALL);
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