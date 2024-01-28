package com.munsun.monitoring_service.backend.exceptions;

/**
 * The exception thrown by the database when adding new data
 */
public class DatabaseConstraintException extends Exception {
    public DatabaseConstraintException(String message) {
        super(message);
    }
}
