package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface JwtProvider {
    boolean validateToken(String token) throws AuthenticationException;
    String generateToken(Account account);
    SecurityUser getSecurityUser(String token) throws SQLException;
    String resolveToken(HttpServletRequest request);
}
