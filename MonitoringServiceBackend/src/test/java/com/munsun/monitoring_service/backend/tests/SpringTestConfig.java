package com.munsun.monitoring_service.backend.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.monitoring_service.backend.controllers.SecurityController;
import com.munsun.monitoring_service.backend.security.JwtProvider;
import com.munsun.monitoring_service.backend.security.impl.DefaultJwtProvider;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.backend.services.SecurityService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SpringTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Primary
    @Bean
    public SecurityController securityController() {
        return new SecurityController(securityService());
    }

    @Primary
    @Bean
    public SecurityService securityService() {
        return Mockito.mock(SecurityService.class);
    }

    @Primary
    @Bean
    public MonitoringService monitoringService() {
        return Mockito.mock(MonitoringService.class);
    }

    @Primary
    @Bean
    public JwtProvider jwtProvider() {
        return Mockito.mock(JwtProvider.class);
    }
}