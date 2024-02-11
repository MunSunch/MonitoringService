package com.munsun.monitoring_service.server.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.monitoring_service.backend.controllers.advice.ErrorController;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.exceptions.AuthorizationException;
import com.munsun.monitoring_service.backend.security.SecurityFilter;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class DelegateFilter extends HttpFilter {
    private final SecurityFilter securityFilter;
    private final ErrorController errorController;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String response = "";
        Integer statusCode = HttpServletResponse.SC_OK;
        try {
            securityFilter.isAllowed(req);
        } catch (AuthenticationException e) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            response = objectMapper.writeValueAsString(errorController.handlerExceptions(e));
        } catch (AuthorizationException e) {
            statusCode = HttpServletResponse.SC_FORBIDDEN;
            response = objectMapper.writeValueAsString(errorController.handlerExceptions(e));
        }
        if("".equals(response)) {
            chain.doFilter(req, res);
            return;
        }

        res.setStatus(statusCode);
        res.getWriter().println(response);
    }
}
