package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.exceptions.AuthorizationException;
import com.munsun.monitoring_service.backend.security.SimpleTokenProvider;
import com.munsun.monitoring_service.backend.security.SecurityFilter;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;
import com.munsun.monitoring_service.commons.enums.Endpoints;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RequiredArgsConstructor
public class SecurityFilterImpl implements SecurityFilter {
    private final SimpleTokenProvider jwtProvider;
    private final SecurityService securityService;

    @Override
    public void isAllowed(HttpServletRequest request) throws AuthenticationException, AuthorizationException {
        var endpoint = Endpoints.define(request);
        if(endpoint.isEmpty() || securityService.authorization(endpoint.get())) {
            return;
        }
        String token = jwtProvider.resolveToken(request);
        if(jwtProvider.validateToken(token)) {
            try {
                SecurityUser securityUser = jwtProvider.getSecurityUser(token);
                if(!securityService.authorization(securityUser, endpoint.get())) {
                    throw new AuthorizationException();
                }
                SecurityContext.setSecurityUser(securityUser);
            } catch (AccountNotFoundException e) {
                throw new AuthenticationException(e.getMessage());
            }
        }
    }
}
