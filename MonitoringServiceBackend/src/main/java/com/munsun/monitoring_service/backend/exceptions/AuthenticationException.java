package com.munsun.monitoring_service.backend.exceptions;

import lombok.NoArgsConstructor;

/**
 * An exception thrown when the username/password does not match
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@NoArgsConstructor
public class AuthenticationException extends Exception {
    /**
     * <p>Constructor for AuthenticationException.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
