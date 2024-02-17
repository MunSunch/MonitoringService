package com.munsun.monitoring_service.backend.controllers;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.security.models.SecurityUser;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "MeterReadingsController", description = "Bus")
public class MeterReadingsController {
    private final MonitoringService monitoringService;

    @GetMapping("/get")
    public List<MeterReadingDtoOut> getHistory(Authentication authentication) {
       var user = (SecurityUser)authentication.getPrincipal();
       return monitoringService.getAllHistory(user.getId());
    }

    @GetMapping("/get/{month}")
    public List<MeterReadingDtoOut> getMeterReadingByMonth(@PathVariable Month month, Authentication authentication) {
        var user = (SecurityUser)authentication.getPrincipal();
        return monitoringService.getHistoryMonth(user.getId(), month);
    }

    @GetMapping("/get/actual")
    public MeterReadingDtoOut getActualMeterReading(Authentication authentication) throws AccountNotFoundException {
        var user = (SecurityUser)authentication.getPrincipal();
        return monitoringService.getActualMeterReadings(user.getId());
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
                                                     Authentication authentication) throws AccountNotFoundException, MissingKeyReadingException
    {
        var user = (SecurityUser)authentication.getPrincipal();
        monitoringService.addMeterReadings(user.getId(), meterReadingsDtoIn);
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
