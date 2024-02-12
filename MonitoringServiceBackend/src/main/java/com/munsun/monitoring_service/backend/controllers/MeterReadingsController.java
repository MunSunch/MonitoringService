package com.munsun.monitoring_service.backend.controllers;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.security.impl.SecurityContext;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import com.munsun.utils.logger.aspects.annotations.Loggable;
import lombok.RequiredArgsConstructor;

import java.time.Month;
import java.util.List;

@RequiredArgsConstructor
public class MeterReadingsController {
    private final MonitoringService monitoringService;

    // GET /get
    @Loggable(message = "User get all history")
    public List<MeterReadingDtoOut> getHistory() {
        SecurityUser currentAuthorizationAccount = SecurityContext.getSecurityUser();
        return monitoringService.getAllHistory(currentAuthorizationAccount.getId());
    }

    // GET /get/{month}
    @Loggable(message = "User get meter reading by month")
    public List<MeterReadingDtoOut> getMeterReadingByMonth(Month month) {
        SecurityUser currentAuthorizationAccount = SecurityContext.getSecurityUser();
        return monitoringService.getHistoryMonth(currentAuthorizationAccount.getId(), month);
    }

    // GET /get/actual
    @Loggable(message = "User get actual meter reading")
    public MeterReadingDtoOut getActualMeterReading() throws AccountNotFoundException {
        SecurityUser currentAuthorizationAccount = SecurityContext.getSecurityUser();
        return monitoringService.getActualMeterReadings(currentAuthorizationAccount.getId());
    }

    // GET /get/all
    @Loggable(message = "User get all history all users")
    public List<LongMeterReadingDtoOut> getHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    // GET /get/all/{month}
    @Loggable(message = "User get all history all users by month")
    public List<LongMeterReadingDtoOut> getHistoryAllUsersByMonth(Month month) {
        return monitoringService.getHistoryMonthAllUsers(month);
    }

    // GET /get/all/actual
    @Loggable(message = "User get actual history all users by month")
    public List<LongMeterReadingDtoOut> getActualHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    // POST /save       ---> Response: 201
    @Loggable(message = "User save new meter reading")
    public void saveNewMeterReadings(MeterReadingsDtoIn meterReadingsDtoIn) throws AccountNotFoundException, MissingKeyReadingException {
        var currentAuthorizationAccount = SecurityContext.getSecurityUser();
        monitoringService.addMeterReadings(currentAuthorizationAccount.getId(), meterReadingsDtoIn);
    }

    //PUT /add/{name_new_parametr}      ---> Response: 201
    @Loggable(message = "User created new name meter reading")
    public void createNewMeterReading(String newMeterReading) {
        monitoringService.expandMeterReading(newMeterReading);
    }
}
