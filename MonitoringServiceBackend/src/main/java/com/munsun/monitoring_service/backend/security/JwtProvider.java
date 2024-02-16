package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String resolveHeader(HttpServletRequest servletRequest);

    boolean validateAccessToken(String token);

    Authentication getAuthentification(String token);

    String generateAccessToken(LoginPasswordDtoIn loginPassword);
}
