package com.munsun.monitoring_service.commons.dto.in;

/**
 * A data transfer object for storing information about an as-yet-unidentified user
 * @param login
 * @param password
 */
public record LoginPasswordDtoIn(
        String login,
        String password
) {}