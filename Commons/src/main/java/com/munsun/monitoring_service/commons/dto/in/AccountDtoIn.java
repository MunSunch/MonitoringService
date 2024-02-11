package com.munsun.monitoring_service.commons.dto.in;

import com.munsun.monitoring_service.commons.utils.validator.impl.annotations.NotEmptyOrNull;
import com.munsun.monitoring_service.commons.utils.validator.impl.annotations.Size;

/**
 * A data transfer object for storing information about a user who has not yet been registered
 * @param login
 * @param password
 * @param country
 * @param city
 * @param street
 * @param house
 * @param level
 * @param apartmentNumber
 */
public record AccountDtoIn(
        @NotEmptyOrNull
        String login,

        @NotEmptyOrNull
        @Size(limit = 4, message = "size of at least 4 characters")
        String password,

        @NotEmptyOrNull
        String country,

        @NotEmptyOrNull
        String city,

        @NotEmptyOrNull
        String street,

        @NotEmptyOrNull
        String house,

        @NotEmptyOrNull
        String level,

        @NotEmptyOrNull
        String apartmentNumber
) {}