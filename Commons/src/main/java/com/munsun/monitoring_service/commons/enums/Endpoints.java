package com.munsun.monitoring_service.commons.enums;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public enum Api {
    LOGIN("/login", "POST"),
    REGISTER("/register", "POST"),
    GET_ALL_HISTORY("/get", "GET"),
    GET_ALL_HISTORY_BY_MONTH("/get/\\d", "GET"),
    GET_ACTUAL_METER_READING("/get/actual", "GET"),
    GET_ALL_HISTORY_ALL_USERS("/get/all", "GET"),
    GET_ALL_HISTORY_ALL_USERS_BY_MONTH("/get/all/\\d", "GET"),
    GET_ACTUAL_METER_READINGS_ALL_USERS("/get/all/actual", "GET"),
    SAVE_NEW_METER_READING("/save", "POST"),
    EXPAND_METER_READING("/add/\\w+", "PUT");

    private String uri;
    private String typeMethod;

    Api(String uri, String typeMethod) {
        this.uri = uri;
        this.typeMethod = typeMethod;
    }

    public String getUri() {
        return uri;
    }

    public String getTypeMethod() {
        return typeMethod;
    }

    public static Optional<Api> define(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String typeMethod = request.getMethod();

        return Arrays.stream(values())
                .filter(x -> x.uri.equals(uri) && x.typeMethod.equals(typeMethod))
                .findFirst();
    }
}