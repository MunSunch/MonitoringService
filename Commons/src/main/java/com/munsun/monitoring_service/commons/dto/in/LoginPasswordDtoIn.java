package com.munsun.monitoring_service.commons.dto.in;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * A data transfer object for storing information about an as-yet-unidentified user
 * @param login
 * @param password
 */
public record LoginPasswordDtoIn(
        @NotBlank
        String login,

        @NotBlank
        @Length(min=4)
        String password
) {}