package com.munsun.monitoring_service.backend.controllers;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.security.impl.SecurityContext;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import lombok.RequiredArgsConstructor;

import java.time.Month;
import java.util.List;

@RequiredArgsConstructor
public class MeterReadingsController {
    private final MonitoringService monitoringService;

    // GET /get
    public List<MeterReadingDtoOut> getHistory() {
        var currentAuthorizationAccount = SecurityContext.getSecurityUser();
        return monitoringService.getAllHistory(currentAuthorizationAccount.getId());
    }

    // GET /get/{month}
    public List<MeterReadingDtoOut> getMeterReadingByMonth(Month month) {
        var currentAuthorizationAccount = SecurityContext.getSecurityUser();;
        return monitoringService.getHistoryMonth(currentAuthorizationAccount.getId(), month);
    }

    // GET /get/actual
    public MeterReadingDtoOut getActualMeterReading() throws AccountNotFoundException {
        var currentAuthorizationAccount = SecurityContext.getSecurityUser();;
        return monitoringService.getActualMeterReadings(currentAuthorizationAccount.getId());
    }

    // GET /get/all
    public List<LongMeterReadingDtoOut> getHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    // GET /get/all/{month}
    public List<LongMeterReadingDtoOut> getHistoryAllUsersByMonth(Month month) {
        return monitoringService.getHistoryMonthAllUsers(month);
    }

    // GET /get/all/actual
    public List<LongMeterReadingDtoOut> getActualHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    // POST /save       ---> Response: 201
    public void saveNewMeterReadings(MeterReadingsDtoIn meterReadingsDtoIn) throws AccountNotFoundException, MissingKeyReadingException {
        var currentAuthorizationAccount = SecurityContext.getSecurityUser();;
        monitoringService.addMeterReadings(currentAuthorizationAccount.getId(), meterReadingsDtoIn);
    }

    //PUT /add/{name_new_parametr}      ---> Response: 201
    public void createNewMeterReading(String newMeterReading) {
        monitoringService.expandMeterReading(newMeterReading);
    }
}
