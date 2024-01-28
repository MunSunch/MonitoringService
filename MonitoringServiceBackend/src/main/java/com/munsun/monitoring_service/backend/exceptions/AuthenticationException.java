package com.munsun.monitoring_service.backend.exceptions;

/**
 * An exception thrown when the username/password does not match
 */
public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException() {
    }
}
