package com.munsun.monitoring_service.backend.controllers;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.backend.security.impl.SecurityContext;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.AuthorizationTokenDtoOut;
import com.munsun.utils.logger.aspects.annotations.Loggable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @Loggable(message = "User logining")
    public AuthorizationTokenDtoOut authenticateUser(LoginPasswordDtoIn loginPasswordDtoIn) throws AuthenticationException, AccountNotFoundException {
        return securityService.authenticate(loginPasswordDtoIn);
    }

    @Loggable(message = "User registration")
    public AccountDtoOut registration(AccountDtoIn accountDtoIn) {
        return securityService.register(accountDtoIn);
    }
}
