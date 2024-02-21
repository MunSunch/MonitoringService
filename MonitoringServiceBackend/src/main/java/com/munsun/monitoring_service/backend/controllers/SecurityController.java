package com.munsun.monitoring_service.backend.controllers;

import com.munsun.monitoring_service.backend.aspects.annotations.Journal;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.services.SecurityService;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.AuthorizationTokenDtoOut;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("/login")
    public AuthorizationTokenDtoOut authenticateUser(@RequestBody @Valid LoginPasswordDtoIn loginPasswordDtoIn) throws AuthenticationException, AccountNotFoundException {
        return securityService.authenticate(loginPasswordDtoIn);
    }

    @PostMapping("/register")
    public ResponseEntity<AccountDtoOut> registration(@RequestBody @Valid AccountDtoIn accountDtoIn) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(securityService.register(accountDtoIn));
    }
}
