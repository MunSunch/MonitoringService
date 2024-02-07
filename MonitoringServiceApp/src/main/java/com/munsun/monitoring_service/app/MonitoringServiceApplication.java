package com.munsun.monitoring_service.app;

import com.munsun.monitoring_service.backend.dao.impl.MeterReadingsDaoImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcAccountMapperImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcMeterReadingsMapperImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcPlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.mapping.impl.*;
import com.munsun.monitoring_service.backend.dao.impl.AccountDaoImpl;
import com.munsun.monitoring_service.backend.security.impl.SecurityContextImpl;
import com.munsun.monitoring_service.backend.security.impl.SecurityServiceImpl;
import com.munsun.monitoring_service.backend.services.impl.MonitoringServiceImpl;
import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.db.impl.DatabaseImpl;
import com.munsun.monitoring_service.commons.db.impl.MigrationSystem;
import com.munsun.monitoring_service.commons.utils.PropertyService;
import com.munsun.monitoring_service.commons.utils.impl.PropertyServiceImpl;
import com.munsun.monitoring_service.frontend.in.service.impl.Console;
import com.munsun.monitoring_service.presenter.service.Presenter;
import com.munsun.monitoring_service.presenter.service.impl.MainPresenter;
import com.munsun.utils.logger.dao.impl.JournalDaoImpl;
import com.munsun.utils.logger.dao.impl.mapping.impl.JdbcJournalMapperImpl;
import com.munsun.utils.logger.service.impl.LoggerServiceImpl;
import liquibase.exception.LiquibaseException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The entry point to the application
 *
 * @author apple
 * @version $Id: $Id
 */
public class MonitoringServiceApplication {
    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) throws SQLException, LiquibaseException, IOException {
        PropertyService properties = new PropertyServiceImpl(MonitoringServiceApplication.class);

        Database database = new DatabaseImpl(properties);
        new MigrationSystem(properties).initSchema(database.getConnection());

        var jdbcAccountMapper = new JdbcAccountMapperImpl(new JdbcPlaceLivingMapperImpl());
        var accountRepository = new AccountDaoImpl(database, jdbcAccountMapper);

        Presenter presenter = new MainPresenter(new Console(),
                                                new MonitoringServiceImpl(new MeterReadingsDaoImpl(database,
                                                                                                  new JdbcMeterReadingsMapperImpl(jdbcAccountMapper)),
                                                                          accountRepository,
                                                                          new MeterReadingMapperImpl()),
                                                new SecurityServiceImpl(accountRepository,
                                                                        new AccountMapperImpl(new PlaceLivingMapperImpl()),
                                                                        new SecurityContextImpl()),
                                                new LoggerServiceImpl(MainPresenter.class,
                                                                      new JournalDaoImpl(database,
                                                                                         new JdbcJournalMapperImpl())));
        presenter.start();
    }
}
