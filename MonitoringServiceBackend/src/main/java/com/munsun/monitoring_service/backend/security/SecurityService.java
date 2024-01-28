package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;

public interface SecurityService {
    void authenticate(LoginPasswordDtoIn loginPassword) throws AccountNotFoundException, AuthenticationException;
    AccountDtoOut register(AccountDtoIn accountDtoIn) throws DatabaseConstraintException;
    void logout();
    boolean getAccess(Role role, ItemsMainMenu item);
    SecurityContext getSecurityContext();
}