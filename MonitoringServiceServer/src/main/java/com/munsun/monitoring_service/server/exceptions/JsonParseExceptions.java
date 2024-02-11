package com.munsun.monitoring_service.server.exceptions;

public class JsonParseExceptions extends Exception{
    private static final String DEFAULT_MESSAGE = "Parse exception";

    public JsonParseExceptions() {
        super(DEFAULT_MESSAGE);
    }

    public JsonParseExceptions(String message) {
        super(message);
    }
}
