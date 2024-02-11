package com.munsun.monitoring_service.server.tests.server;

import com.munsun.monitoring_service.backend.controllers.MeterReadingsController;
import com.munsun.monitoring_service.backend.controllers.SecurityController;
import com.munsun.monitoring_service.backend.controllers.advice.ErrorController;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.commons.enums.Endpoints;
import com.munsun.monitoring_service.server.enums.ContentType;
import com.munsun.monitoring_service.server.exceptions.JsonParseExceptions;
import com.munsun.monitoring_service.server.mapping.JSONMapper;
import com.munsun.monitoring_service.server.servlets.DispatcherServlet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.StringReader;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;


@ExtendWith(MockitoExtension.class)
public class DispatcherServletUnitTests {
    @Mock
    private SecurityController securityController;
    @Mock
    private MeterReadingsController meterReadingsController;
    @Mock
    private ErrorController errorController;
    @Mock
    private JSONMapper jsonMapper;
    @InjectMocks
    private DispatcherServlet dispatcherServlet;

    @Mock
    HttpServletResponse response;
    @Mock
    HttpServletRequest request;

    @DisplayName("Switching to an undefined URI")
    @Test
    public void testServiceUnknownURI_NotFound404() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/unknown");
        when(request.getMethod()).thenReturn("GET");

        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Login of a user with a non-existent account")
    @Test
    public void testServiceLoginEndpoint_AccountNotFound404() throws ServletException, IOException, AuthenticationException, AccountNotFoundException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.LOGIN.getUri());
        when(request.getMethod()).thenReturn(Endpoints.LOGIN.getTypeMethod());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{}")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(securityController.authenticateUser(any())).thenThrow(AccountNotFoundException.class);
        when(jsonMapper.toLoginPassword(any())).thenReturn(any());
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Logging in an existing user with an invalid password")
    @Test
    public void testServiceLoginEndpoint_AuthenticationException401() throws ServletException, IOException, AuthenticationException, AccountNotFoundException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.LOGIN.getUri());
        when(request.getMethod()).thenReturn(Endpoints.LOGIN.getTypeMethod());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{}")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(securityController.authenticateUser(any())).thenThrow(AuthenticationException.class);
        when(jsonMapper.toLoginPassword(any())).thenReturn(any());
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Error or missing values in the request body")
    @Test
    public void testServiceLoginEndpoint_JsonParseException400() throws ServletException, IOException, AuthenticationException, AccountNotFoundException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.LOGIN.getUri());
        when(request.getMethod()).thenReturn(Endpoints.LOGIN.getTypeMethod());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{}")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(jsonMapper.toLoginPassword(any())).thenThrow(JsonParseExceptions.class);
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Registration of an existing account with a similar username")
    @Test
    public void testServiceRegisterEndpoint_RegisterExistsAccount500() throws ServletException, IOException, AuthenticationException, AccountNotFoundException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.REGISTER.getUri());
        when(request.getMethod()).thenReturn(Endpoints.REGISTER.getTypeMethod());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{}")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(securityController.registration(any())).thenThrow(IllegalArgumentException.class);
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Violation of the registration request body format")
    @Test
    public void testServiceRegisterEndpoint_JsonParseException400() throws ServletException, IOException, AuthenticationException, AccountNotFoundException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.REGISTER.getUri());
        when(request.getMethod()).thenReturn(Endpoints.REGISTER.getTypeMethod());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{}")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(jsonMapper.toAccountDtoIn(any())).thenThrow(JsonParseExceptions.class);
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Violation of the request body format when saving a new meter reading")
    @Test
    public void testServiceSaveMeterReading_JsonParseException400() throws ServletException, IOException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.SAVE_NEW_METER_READING.getUri());
        when(request.getMethod()).thenReturn(Endpoints.SAVE_NEW_METER_READING.getTypeMethod());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{}")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(jsonMapper.toMeterReadingsDtoIn(anyString())).thenThrow(JsonParseExceptions.class);
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Successful saving of new readings with code 201")
    @Test
    public void testServiceSaveMeterReading201() throws ServletException, IOException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.SAVE_NEW_METER_READING.getUri());
        when(request.getMethod()).thenReturn(Endpoints.SAVE_NEW_METER_READING.getTypeMethod());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{}")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_CREATED);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Successful receipt of updated readings with code 200")
    @Test
    public void testServiceGetActualMeterReading201() throws ServletException, IOException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.GET_ACTUAL_METER_READING.getUri());
        when(request.getMethod()).thenReturn(Endpoints.GET_ACTUAL_METER_READING.getTypeMethod());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Successful receipt of updated readings all users with code 200")
    @Test
    public void testServiceGetActualMeterReadingAllUsers200() throws ServletException, IOException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.GET_ACTUAL_METER_READINGS_ALL_USERS.getUri());
        when(request.getMethod()).thenReturn(Endpoints.GET_ACTUAL_METER_READINGS_ALL_USERS.getTypeMethod());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }

    @DisplayName("Successful receipt of all readings by all users with the code 200")
    @Test
    public void testServiceGetMeterReadingAllUsers200() throws ServletException, IOException, JsonParseExceptions {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn(Endpoints.GET_ALL_HISTORY_ALL_USERS.getUri());
        when(request.getMethod()).thenReturn(Endpoints.GET_ALL_HISTORY_ALL_USERS.getTypeMethod());
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        dispatcherServlet.service(request, response);

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(response, atLeastOnce()).setContentType(ContentType.JSON.getDescription());
    }


}
