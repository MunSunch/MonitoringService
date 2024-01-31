package com.munsun.monitoring_service.app;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.mapping.impl.AccountMapperImpl;
import com.munsun.monitoring_service.backend.mapping.impl.MeterReadingMapperImpl;
import com.munsun.monitoring_service.backend.mapping.impl.PlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.repositories.impl.AccountRepositoryImpl;
import com.munsun.monitoring_service.backend.repositories.impl.MeterReadingsRepositoryImpl;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.security.impl.SecurityContextImpl;
import com.munsun.monitoring_service.backend.security.impl.SecurityServiceImpl;
import com.munsun.monitoring_service.backend.services.impl.MonitoringServiceImpl;
import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.db.MigrationSystem;
import com.munsun.monitoring_service.frontend.in.service.impl.Console;
import com.munsun.monitoring_service.presenter.service.Presenter;
import com.munsun.monitoring_service.presenter.service.impl.MainPresenter;
import liquibase.Liquibase;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;

import java.sql.SQLException;

/**
 * The entry point to the application
 *
 * @author apple
 * @version $Id: $Id
 */
public class MonitoringServiceApplication {
    private static final Account ADMIN = new Account(null,
            "admin",
            "admin",
            null,
            Role.ADMIN,
            false);

    private static final Account USER = new Account(null,
            "user",
            "user",
            null,
            Role.USER,
            false);

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     * @throws com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException if any.
     */
    public static void main(String[] args) throws DatabaseConstraintException, SQLException, LiquibaseException {
        MigrationSystem.initSchema(Database.getConnection());

        var accountRepository = new AccountRepositoryImpl();
            accountRepository.save(ADMIN);
            accountRepository.save(USER);

        Presenter presenter = new MainPresenter(new Console(),
                                                new MonitoringServiceImpl(new MeterReadingsRepositoryImpl(),
                                                                          accountRepository,
                                                                          new MeterReadingMapperImpl()),
                                                new SecurityServiceImpl(accountRepository,
                                                                        new AccountMapperImpl(new PlaceLivingMapperImpl()),
                                                                        new SecurityContextImpl()));
        presenter.start();
    }
}
