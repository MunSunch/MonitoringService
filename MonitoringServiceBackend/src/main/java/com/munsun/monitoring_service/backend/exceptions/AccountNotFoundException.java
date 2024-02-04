package com.munsun.monitoring_service.backend.exceptions;

/**
 * An exception thrown when the user is not in the database
 *
 * @author apple
 * @version $Id: $Id
 */
public class AccountNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE = "Account is not exists!";

    /**
     * <p>Constructor for AccountNotFoundException.</p>
     */
    public AccountNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
