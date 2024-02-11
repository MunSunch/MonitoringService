package com.munsun.monitoring_service.commons.dto.in;

import com.munsun.monitoring_service.commons.utils.validator.impl.annotations.NotEmptyOrNull;
import com.munsun.monitoring_service.commons.utils.validator.impl.annotations.Size;

/**
 * A data transfer object for storing information about an as-yet-unidentified user
 * @param login
 * @param password
 */
public record LoginPasswordDtoIn(
        @NotEmptyOrNull
        String login,

        @NotEmptyOrNull
        @Size(limit = 4, message = "size of at least 4 characters")
        String password
) {}