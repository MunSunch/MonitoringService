package com.munsun.monitoring_service.backend.tests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.monitoring_service.backend.configurations.root.SpringContextBackendConfig;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.services.SecurityService;
import com.munsun.monitoring_service.backend.tests.SpringTestConfig;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.AuthorizationTokenDtoOut;
import com.munsun.monitoring_service.commons.dto.out.ErrorDtoOut;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextBackendConfig.class, SpringTestConfig.class})
@WebAppConfiguration(value = "target/MonitoringServiceBackend-1.0-SNAPSHOT/WEB-INF")
public class SecurityControllerUnitTests {
    @Autowired
    private SecurityService securityService;
    private MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("Log in to an existing user with a valid username/password with code 200")
    @Test
    public void positiveTestRequestLoginEndpoint_200() throws Exception {
        var dto = new LoginPasswordDtoIn("user", "user");
        var expected = new AuthorizationTokenDtoOut("token");

        when(securityService.authenticate(any()))
                .thenReturn(expected);
        var result = mockMvc.perform(post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                            .andExpect(status().isOk())
                            .andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), AuthorizationTokenDtoOut.class);

        verify(securityService, atLeastOnce()).authenticate(any());
        assertThat(actual)
                .extracting(AuthorizationTokenDtoOut::token)
                .isEqualTo(expected.token());
    }

    @DisplayName("Log in to an existing user with code 404")
    @Test
    public void negativeTestRequestLoginEndpoint_AccountIsNotExists404() throws Exception {
        var dto = new LoginPasswordDtoIn("test", "test");
        var expectedMessage = "Account is not exists!";
        var expectedStatus = HttpStatus.NOT_FOUND;

        when(securityService.authenticate(any()))
                .thenThrow(new AccountNotFoundException());
        var result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDtoOut.class);

        verify(securityService, atLeastOnce()).authenticate(any());
        assertThat(actual)
                .extracting(ErrorDtoOut::message)
                .isEqualTo(expectedMessage);
    }

    @DisplayName("Log in as an existing user with an invalid password with code 400")
    @Test
    public void negativeTestRequestLoginEndpoint_InvalidPassword400() throws Exception {
        var dto = new LoginPasswordDtoIn("test", "invalidPassword");
        var expectedMessage = "invalid login/password";
        var expectedStatus = HttpStatus.UNAUTHORIZED;

        when(securityService.authenticate(any()))
                .thenThrow(new AuthenticationException(expectedMessage));
        var result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDtoOut.class);

        verify(securityService, atLeastOnce()).authenticate(any());
        assertThat(actual)
                .extracting(ErrorDtoOut::message)
                .isEqualTo(expectedMessage);
    }

    @DisplayName("Registration of a new non-existent user with the code 201")
    @Test
    public void positiveTestRequestRegisterEndpoint_200() throws Exception {
        var dto = new AccountDtoIn("testLogin","testPassword",
                "testCountry", "testCity", "testStreet",
                "testHouse", "testLevel", "testApartment");
        var expected = new AccountDtoOut("testLogin");

        when(securityService.register(any()))
                .thenReturn(expected);
        var result = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), AccountDtoOut.class);

        verify(securityService, atLeastOnce()).register(any());
        assertThat(actual)
                .extracting(AccountDtoOut::login)
                .isEqualTo(expected.login());
    }

    @DisplayName("Registration of an existing user (by login) with the code 500")
    @Test
    public void negativeTestRequestRegisterEndpoint_AccountIsExists500() throws Exception {
        var dto = new AccountDtoIn("testLogin","testPassword",
                "testCountry", "testCity", "testStreet",
                "testHouse", "testLevel", "testApartment");
        var expectedMessage = "account is exists";
        var expectedStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        when(securityService.register(any()))
                .thenThrow(new IllegalArgumentException(expectedMessage));
        var result = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDtoOut.class);

        verify(securityService, atLeastOnce()).register(any());
        assertThat(actual)
                .extracting(ErrorDtoOut::message)
                .isEqualTo(expectedMessage);
    }
}