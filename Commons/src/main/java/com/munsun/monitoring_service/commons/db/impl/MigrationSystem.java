package com.munsun.monitoring_service.commons.db.impl;

import com.munsun.monitoring_service.commons.db.impl.queries.PrevQueries;
import com.munsun.monitoring_service.commons.exceptions.InitSchemaLiquibaseException;
import com.munsun.monitoring_service.commons.utils.property.PropertyService;
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
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Migration system for automatic schema creation in DBMS.
 * For correct operation, it requires a connection to the DBMS to create the main and service schemas and a file.property with indication of properties:
 * <ul>
 * <li>liquibase.default-schema</li>
 * <li>liquibase.changelog.path</li>
 * </ul>
 *
 * The last property indicates the location of the set of changes applicable to the database. For more information, follow the {@link <a href="http://www.liquibase.org/">link</a>}
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public class MigrationSystem {
    private final PropertyService properties;

    public MigrationSystem(PropertyService properties) {
        this.properties = properties;
    }

    /**
     * Create a schema in the database based on a set of changes.
     *
     * @param connection a Connection object
     * @throws liquibase.exception.LiquibaseException
     * @throws java.sql.SQLException
     * @throws java.io.FileNotFoundException
     */
    public void initSchema(Connection connection) throws LiquibaseException, SQLException, FileNotFoundException, InitSchemaLiquibaseException {
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
        try(var statement = connection.prepareStatement(PrevQueries.IS_EXISTS_SCHEMA.QUERY.getDescription()))
        {
            statement.setString(PrevQueries.IS_EXISTS_SCHEMA.Arguments.NAME_SCHEMA.getPositionInQuery(), nameSchema);
            var res = statement.executeQuery();
            return res.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private void createSchema(String nameSchema, Connection connection) throws InitSchemaLiquibaseException {
        try(var statement = connection.createStatement())
        {
            statement.executeUpdate(String.format(PrevQueries.CREATE_NEW_SCHEMA.QUERY.getDescription(), nameSchema));
        } catch (SQLException e) {
            throw new InitSchemaLiquibaseException();
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
