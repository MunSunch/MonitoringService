package com.munsun.monitoring_service.backend.controllers;

import com.munsun.monitoring_service.backend.aspects.annotations.Journal;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.security.models.SecurityUser;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
public class MeterReadingsController {
    private final MonitoringService monitoringService;

    @GetMapping("/get")
    @Journal(message = "get history")
    public List<MeterReadingDtoOut> getHistory(Authentication authentication) {
       var user = (SecurityUser)authentication.getPrincipal();
       return monitoringService.getAllHistory(user.getId());
    }

    @GetMapping("/get/{month}")
    @Journal(message = "get the history for a specific month")
    public List<MeterReadingDtoOut> getMeterReadingByMonth(@PathVariable("month") @Positive Integer month, Authentication authentication) {
        var user = (SecurityUser)authentication.getPrincipal();
        return monitoringService.getHistoryMonth(user.getId(), Month.of(month));
    }

    @GetMapping("/get/actual")
    @Journal(message = "get up-to-date meter readings")
    public MeterReadingDtoOut getActualMeterReading(Authentication authentication) throws AccountNotFoundException {
        var user = (SecurityUser)authentication.getPrincipal();
        return monitoringService.getActualMeterReadings(user.getId());
    }

    @GetMapping("/get/all")
    @Journal(message = "get up-to-date meter readings")
    public List<LongMeterReadingDtoOut> getHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    @GetMapping("/get/all/{month}")
    @Journal(message = "get up-to-date meter readings")
    public List<LongMeterReadingDtoOut> getHistoryAllUsersByMonth(@PathVariable("month") @Positive Integer month) {
        return monitoringService.getHistoryMonthAllUsers(Month.of(month));
    }

    @GetMapping("/get/all/actual")
    @Journal(message = "get up-to-date meter readings")
    public List<LongMeterReadingDtoOut> getActualHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    @PostMapping("/save")
    @Journal(message = "get up-to-date meter readings")
    public ResponseEntity<Void> saveNewMeterReadings(@RequestBody MeterReadingsDtoIn meterReadingsDtoIn,
                                                     Authentication authentication) throws AccountNotFoundException, MissingKeyReadingException
    {
        var user = (SecurityUser)authentication.getPrincipal();
        monitoringService.addMeterReadings(user.getId(), meterReadingsDtoIn);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/add/{name}")
    @Journal(message = "get up-to-date meter readings")
    public ResponseEntity<Void> createNewMeterReading(@PathVariable(name = "name") @NotBlank String newMeterReading) {
        monitoringService.expandMeterReading(newMeterReading);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
