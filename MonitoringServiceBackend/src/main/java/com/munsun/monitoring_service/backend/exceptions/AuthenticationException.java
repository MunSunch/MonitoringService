package com.munsun.monitoring_service.backend.exceptions;

import lombok.NoArgsConstructor;

/**
 * An exception thrown when the username/password does not match
 */
@NoArgsConstructor
public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}
