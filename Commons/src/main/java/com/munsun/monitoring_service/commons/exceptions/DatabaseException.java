package com.munsun.monitoring_service.commons.exceptions;

public class DatabaseException extends RuntimeException{
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Exception e) {
        super(e);
    }
}
