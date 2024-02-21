package com.munsun.monitoring_service.backend.tests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.monitoring_service.backend.configurations.root.SpringContextBackendConfig;
import com.munsun.monitoring_service.backend.security.JwtProvider;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.security.models.SecurityUser;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.backend.tests.SpringTestConfig;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.ErrorDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextBackendConfig.class, SpringTestConfig.class})
@WebAppConfiguration(value = "target/MonitoringServiceBackend-1.0-SNAPSHOT/WEB-INF")
public class MeterReadingsUnitTests {
    @Autowired
    private MonitoringService monitoringService;
    private MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private JwtProvider jwtProvider;

    private Authentication userAuthentication;
    private Authentication adminAuthentication;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        var securityUser = new SecurityUser(1L, "user", "user", Role.USER);
        userAuthentication = new UsernamePasswordAuthenticationToken(securityUser, "", securityUser.getAuthorities());
        var securityAdmin = new SecurityUser(2L, "admin", "admin", Role.ADMIN);
        adminAuthentication = new UsernamePasswordAuthenticationToken(securityAdmin, "", securityAdmin.getAuthorities());
    }

    @DisplayName("Sending a request to save a new meter reading with the response code 201")
    @Test
    public void positiveTestRequestSaveEndpoint_201() throws Exception {
        var dto = new MeterReadingsDtoIn(Map.of("test1", 10L,
                                                "test2", 20L));

        when(jwtProvider.resolveHeader(any(HttpServletRequest.class)))
                .thenReturn("token");
        when(jwtProvider.validateAccessToken("token"))
                .thenReturn(true);
        when(jwtProvider.getAuthentification("token"))
                .thenReturn(userAuthentication);
        var result = mockMvc.perform(post("/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(dto)))
                            .andExpect(status().isCreated());
        verify(monitoringService, atLeastOnce()).addMeterReadings(anyLong(), eq(dto));
    }

    @DisplayName("Sending a request to save a new meter reading with the response code 201")
    @Test
    public void negativeTestRequestSaveEndpoint_BusinessLogicException400() throws Exception {
        var dto = new MeterReadingsDtoIn(Map.of("test1", 10L,
                "test2", 20L));
        var expectedException = new MissingKeyReadingException("Missing parameters: heating");

        when(jwtProvider.resolveHeader(any(HttpServletRequest.class)))
                .thenReturn("token");
        when(jwtProvider.validateAccessToken("token"))
                .thenReturn(true);
        when(jwtProvider.getAuthentification("token"))
                .thenReturn(userAuthentication);
        when(monitoringService.addMeterReadings(anyLong(), eq(dto)))
                .thenThrow(expectedException);
        var result = mockMvc.perform(post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest()).andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDtoOut.class);

        verify(monitoringService, atLeastOnce()).addMeterReadings(anyLong(), eq(dto));
        assertThat(actual)
                .extracting(ErrorDtoOut::message)
                .isEqualTo(expectedException.getMessage());
    }

    @DisplayName("Sending a request to save the second meter readings for the current month with the response code 500")
    @Test
    public void negativeTestRequestSaveEndpoint_IllegalArgumentException500() throws Exception {
        var dto = new MeterReadingsDtoIn(Map.of("test1", 10L,
                "test2", 20L));
        var expectedException = new IllegalArgumentException("Adding is not possible: the record for the current month already exists");

        when(jwtProvider.resolveHeader(any(HttpServletRequest.class)))
                .thenReturn("token");
        when(jwtProvider.validateAccessToken("token"))
                .thenReturn(true);
        when(jwtProvider.getAuthentification("token"))
                .thenReturn(userAuthentication);
        when(monitoringService.addMeterReadings(anyLong(), eq(dto)))
                .thenThrow(expectedException);
        var result = mockMvc.perform(post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isInternalServerError()).andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDtoOut.class);

        verify(monitoringService, atLeastOnce()).addMeterReadings(anyLong(), eq(dto));
        assertThat(actual)
                .extracting(ErrorDtoOut::message)
                .isEqualTo(expectedException.getMessage());
    }
}