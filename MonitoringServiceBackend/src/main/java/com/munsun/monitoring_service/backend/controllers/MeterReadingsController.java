package com.munsun.monitoring_service.backend.controllers;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Month;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
public class MeterReadingsController {
    private final MonitoringService monitoringService;

    @GetMapping("/get")
    public List<MeterReadingDtoOut> getHistory(Principal principal) {
       return monitoringService.getAllHistory(Long.parseLong(principal.getName()));
    }

    @GetMapping("/get/{month}")
    public List<MeterReadingDtoOut> getMeterReadingByMonth(@PathVariable Month month, Principal principal) {
        return monitoringService.getHistoryMonth(Long.parseLong(principal.getName()), month);
    }

    @GetMapping("/get/actual")
    public MeterReadingDtoOut getActualMeterReading(Principal principal) throws AccountNotFoundException {
        return monitoringService.getActualMeterReadings(Long.parseLong(principal.getName()));
    }

    @GetMapping("/get/all")
    public List<LongMeterReadingDtoOut> getHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    @GetMapping("/get/all/{month}")
    public List<LongMeterReadingDtoOut> getHistoryAllUsersByMonth(@PathVariable Month month) {
        return monitoringService.getHistoryMonthAllUsers(month);
    }

    @GetMapping("/get/all/actual")
    public List<LongMeterReadingDtoOut> getActualHistoryAllUsers() {
        return monitoringService.getAllMeterReadingsAllUsers();
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveNewMeterReadings(@RequestBody MeterReadingsDtoIn meterReadingsDtoIn,
                                                     Principal principal) throws AccountNotFoundException, MissingKeyReadingException
    {
        monitoringService.addMeterReadings(Long.parseLong(principal.getName()), meterReadingsDtoIn);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/add/{name}")
    public ResponseEntity<Void> createNewMeterReading(@PathVariable(name = "name") @NotBlank String newMeterReading) {
        monitoringService.expandMeterReading(newMeterReading);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
