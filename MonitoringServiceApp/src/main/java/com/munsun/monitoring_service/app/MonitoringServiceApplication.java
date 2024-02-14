package com.munsun.monitoring_service.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.monitoring_service.backend.controllers.MeterReadingsController;
import com.munsun.monitoring_service.backend.controllers.SecurityController;
import com.munsun.monitoring_service.backend.controllers.advice.ErrorController;
import com.munsun.monitoring_service.backend.dao.impl.MeterReadingsDaoImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcAccountMapperImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcMeterReadingsMapperImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcPlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.mapping.MeterReadingMapper;
import com.munsun.monitoring_service.backend.dao.impl.AccountDaoImpl;
import com.munsun.monitoring_service.backend.security.impl.SimpleTokenProviderImpl;
import com.munsun.monitoring_service.backend.security.impl.SecurityFilterImpl;
import com.munsun.monitoring_service.backend.security.impl.SecurityServiceImpl;
import com.munsun.monitoring_service.backend.services.impl.MonitoringServiceImpl;
import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.db.impl.DatabaseImpl;
import com.munsun.monitoring_service.commons.db.impl.MigrationSystem;
import com.munsun.monitoring_service.commons.utils.property.PropertyService;
import com.munsun.monitoring_service.commons.utils.property.impl.PropertyServiceImpl;
import com.munsun.monitoring_service.commons.utils.validator.impl.DefaultValidator;
import com.munsun.monitoring_service.server.Server;
import com.munsun.monitoring_service.server.filters.DelegateFilter;
import com.munsun.monitoring_service.server.mapping.impl.JsonMapperImpl;
import com.munsun.monitoring_service.server.servlets.DispatcherServlet;

/**
 * The entry point to the application
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public class MonitoringServiceApplication {
    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) throws Exception {
        PropertyService properties = new PropertyServiceImpl(MonitoringServiceApplication.class);

        Database database = new DatabaseImpl(properties);
        new MigrationSystem(properties).initSchema(database.getConnection());

        var objectMapper = new ObjectMapper();
        var jdbcAccountMapper = new JdbcAccountMapperImpl(new JdbcPlaceLivingMapperImpl());
        var jdbcMeterReadingsMapper = new JdbcMeterReadingsMapperImpl(jdbcAccountMapper);
        var accountMapper = AccountMapper.instance;
        var accountRepository = new AccountDaoImpl(database, jdbcAccountMapper);
        var monitoringService = new MonitoringServiceImpl(new MeterReadingsDaoImpl(database, jdbcMeterReadingsMapper),
                accountRepository, MeterReadingMapper.instance);
        var errorController = new ErrorController();
        var jwtProvider = new SimpleTokenProviderImpl(properties, accountRepository);
        var securityController = new SecurityController(new SecurityServiceImpl(accountRepository, accountMapper, jwtProvider));
        var meterReadingsController = new MeterReadingsController(monitoringService);
        var securityService = new SecurityServiceImpl(accountRepository, accountMapper, jwtProvider);
        var delegateFilter = new DelegateFilter(new SecurityFilterImpl(jwtProvider, securityService), errorController, objectMapper);
        var dispatcherServlet = new DispatcherServlet(securityController, meterReadingsController,errorController, new JsonMapperImpl(new DefaultValidator()));
        var server = new Server(dispatcherServlet, delegateFilter);
        server.start(Integer.parseInt(properties.getProperty("server.port")));
    }
}