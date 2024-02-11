package com.munsun.monitoring_service.commons.exceptions;

public class ParseSqlQueryException extends RuntimeException{
    public ParseSqlQueryException() {
    }

    public ParseSqlQueryException(String message) {
        super(message);
    }

    public ParseSqlQueryException(Throwable cause) {
        super(cause);
    }
}
