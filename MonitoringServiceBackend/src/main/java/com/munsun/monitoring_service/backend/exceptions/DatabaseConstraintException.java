package com.munsun.monitoring_service.backend.exceptions;

/**
 * The exception thrown by the database when adding new data
 *
 * @author apple
 * @version $Id: $Id
 */
public class DatabaseConstraintException extends Exception {
    /**
     * <p>Constructor for DatabaseConstraintException.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public DatabaseConstraintException(String message) {
        super(message);
    }
}
