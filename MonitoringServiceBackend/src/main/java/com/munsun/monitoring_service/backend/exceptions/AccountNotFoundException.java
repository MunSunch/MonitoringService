package com.munsun.monitoring_service.backend.exceptions;

/**
 * An exception thrown when the user is not in the database
 */
public class AccountNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE = "Account is not exists!";

    public AccountNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
