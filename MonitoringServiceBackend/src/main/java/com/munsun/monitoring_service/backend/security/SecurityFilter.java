package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.exceptions.AuthorizationException;

import javax.servlet.http.HttpServletRequest;

public interface SecurityFilter {
    void isAllowed(HttpServletRequest servletRequest) throws AuthenticationException, AuthorizationException;
}
