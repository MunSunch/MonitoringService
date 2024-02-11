package com.munsun.monitoring_service.server.servlets;

import com.munsun.monitoring_service.backend.controllers.MeterReadingsController;
import com.munsun.monitoring_service.backend.controllers.SecurityController;
import com.munsun.monitoring_service.backend.controllers.advice.ErrorController;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.enums.Endpoints;
import com.munsun.monitoring_service.server.enums.ContentType;
import com.munsun.monitoring_service.server.exceptions.JsonParseExceptions;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import com.munsun.monitoring_service.server.mapping.JSONMapper;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DispatcherServlet extends HttpServlet {
    private final SecurityController securityController;
    private final MeterReadingsController meterReadingsController;
    private final ErrorController errorController;
    private final JSONMapper jsonMapper;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, IOException {
        String contentType = ContentType.JSON.getDescription();
        res.setContentType(contentType);

        var endpoint = Endpoints.define(req);
        if(endpoint.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            switch (endpoint.get()) {
                case LOGIN -> serviceLoginEndpoint(req, res);
                case REGISTER -> serviceRegisterEndpoint(req, res);
                case GET_HISTORY -> serviceGetAllHistoryEndpoint(req, res);
                case SAVE_NEW_METER_READING -> serviceSaveNewMeterReadingEndpoint(req, res);
                case GET_ACTUAL_METER_READING -> serviceGetActualMeterReadingEndpoint(req, res);
                case GET_ACTUAL_METER_READINGS_ALL_USERS -> serviceGetActualMeterReadingsAllUser(req, res);
                case GET_ALL_HISTORY_ALL_USERS -> serviceGetAllHistoryAllUsers(req, res);
                case EXPAND_METER_READING -> serviceExpandMeterReadingEndpoint(req, res);
                case GET_METER_READING_BY_MONTH -> serviceGetMeterReadingMonthEndpoint(req, res);
                case GET_ALL_HISTORY_ALL_USERS_BY_MONTH -> serviceGetAllHistoryAllUsersMonth(req,res);
            }
        } catch (JsonParseExceptions e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().println(jsonMapper.toJSON(errorController.handlerExceptions(e)));
            } catch (JsonParseExceptions ex) {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            try {
                res.getWriter().println(jsonMapper.toJSON(errorController.handlerExceptions(e)));
            } catch (JsonParseExceptions ex) {
                ex.printStackTrace();
            }
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void serviceGetAllHistoryAllUsersMonth(HttpServletRequest req, HttpServletResponse res) throws JsonParseExceptions, IOException {
        int indexLastSlash = getIndexLastSlash(req.getRequestURI());
        Month month = Month.of(Integer.parseInt(req.getRequestURI().substring(indexLastSlash+1)));
        var meterReadings = meterReadingsController.getHistoryAllUsersByMonth(month);
        String body = jsonMapper.toJSON(meterReadings);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(body);
    }

    private int getIndexLastSlash(String uri) {
        return uri.lastIndexOf("/");
    }

    private void serviceGetMeterReadingMonthEndpoint(HttpServletRequest req, HttpServletResponse res) throws JsonParseExceptions, IOException {
        int indexLastSlash = getIndexLastSlash(req.getRequestURI());
        Month month = Month.of(Integer.parseInt(req.getRequestURI().substring(getIndexLastSlash(req.getRequestURI())+1)) + 1);
        var meterReadings = meterReadingsController.getMeterReadingByMonth(month);
        String body = jsonMapper.toJSON(meterReadings);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(body);
    }

    private void serviceExpandMeterReadingEndpoint(HttpServletRequest req, HttpServletResponse res) {
        int indexLastSlash = getIndexLastSlash(req.getRequestURI());
        var nameNewParameter = req.getRequestURI().substring(indexLastSlash+1);
        meterReadingsController.createNewMeterReading(nameNewParameter);

        res.setStatus(HttpServletResponse.SC_CREATED);
    }

    private void serviceGetAllHistoryAllUsers(HttpServletRequest req, HttpServletResponse res) throws JsonParseExceptions, IOException {
        var history = meterReadingsController.getHistoryAllUsers();
        String body = jsonMapper.toJSON(history);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(body);
    }

    private void serviceGetActualMeterReadingsAllUser(HttpServletRequest req, HttpServletResponse res) throws JsonParseExceptions, IOException {
        var meterReadings = meterReadingsController.getActualHistoryAllUsers();
        String responseBody = jsonMapper.toJSON(meterReadings);
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(responseBody);
    }

    private void serviceGetActualMeterReadingEndpoint(HttpServletRequest req, HttpServletResponse res) throws JsonParseExceptions, IOException {
        String responseBody = "";
        int statusCode;
        try {
            var meterReading = meterReadingsController.getActualMeterReading();
            responseBody = jsonMapper.toJSON(meterReading);
            statusCode = HttpServletResponse.SC_OK;
        } catch (AccountNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            responseBody = jsonMapper.toJSON(errorController.handlerExceptions(e));
        }
        res.setStatus(statusCode);
        res.getWriter().println(responseBody);
    }

    private void serviceLoginEndpoint(HttpServletRequest req, HttpServletResponse res) throws IOException, JsonParseExceptions {
        LoginPasswordDtoIn loginPassword = jsonMapper.toLoginPassword(readAllLinesWithStream(req.getReader()));
        String response = "";
        int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        try {
            var token = securityController.authenticateUser(loginPassword);
            response = jsonMapper.toJSON(token);
        } catch (AuthenticationException e) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            response = jsonMapper.toJSON(errorController.handlerExceptions(e));
        } catch (AccountNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            response = jsonMapper.toJSON(errorController.handlerExceptions(e));
        }
        res.setStatus(statusCode);
        res.getWriter().println(response);
    }

    private void serviceRegisterEndpoint(HttpServletRequest req, HttpServletResponse res) throws IOException, JsonParseExceptions {
        String response = "";
        int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        AccountDtoIn accountDtoIn = jsonMapper.toAccountDtoIn(readAllLinesWithStream(req.getReader()));
        try {
            var accountDtoOut = securityController.registration(accountDtoIn);
            statusCode = HttpServletResponse.SC_CREATED;
            response = jsonMapper.toJSON(accountDtoOut);
        } catch (IllegalArgumentException e) {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            response = jsonMapper.toJSON(errorController.handlerExceptions(e));
        }
        res.setStatus(statusCode);
        res.getWriter().println(response);
    }

    private void serviceGetAllHistoryEndpoint(HttpServletRequest req, HttpServletResponse res) throws JsonParseExceptions, IOException {
        List<MeterReadingDtoOut> history = meterReadingsController.getHistory();
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(jsonMapper.toJSON(history));
    }

    private void serviceSaveNewMeterReadingEndpoint(HttpServletRequest req, HttpServletResponse res) throws JsonParseExceptions, IOException {
        MeterReadingsDtoIn dto = jsonMapper.toMeterReadingsDtoIn(readAllLinesWithStream(req.getReader()));
        String response = "";
        int statusCode;
        try {
            meterReadingsController.saveNewMeterReadings(dto);
            statusCode = HttpServletResponse.SC_CREATED;
        } catch (AccountNotFoundException e) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            response = jsonMapper.toJSON(errorController.handlerExceptions(e));
        } catch (MissingKeyReadingException e) {
            response = jsonMapper.toJSON(errorController.handlerExceptions(e));
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        }
        res.setStatus(statusCode);
        res.getWriter().println(response);
    }

    private String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
