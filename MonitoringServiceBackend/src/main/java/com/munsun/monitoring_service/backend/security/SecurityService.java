package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;
import com.munsun.monitoring_service.commons.dto.out.AuthorizationTokenDtoOut;
import com.munsun.monitoring_service.commons.enums.Endpoints;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;

/**
 * Abstraction for working with users
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface SecurityService {
    /**
     * An attempt to authenticate the user. If the attempt is successful, the current user will be added to the Security Context for subsequent authorization to the resources
     *
     * @param loginPassword a {@link com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn} object
     * @throws com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException if any.
     * @throws com.munsun.monitoring_service.backend.exceptions.AuthenticationException if any.
     */
    AuthorizationTokenDtoOut authenticate(LoginPasswordDtoIn loginPassword) throws AccountNotFoundException, AuthenticationException;

    /**
     * Add a new user to the system with the predefined {@link Role role}=Role.USER.
     * @param accountDtoIn a {@link com.munsun.monitoring_service.commons.dto.in.AccountDtoIn} object
     * @return a {@link com.munsun.monitoring_service.commons.dto.out.AccountDtoOut} object
     */
    AccountDtoOut register(AccountDtoIn accountDtoIn);

    boolean authorization(SecurityUser securityUser, Endpoints api);
    boolean authorization(Endpoints api);
}
