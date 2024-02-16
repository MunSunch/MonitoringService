package com.munsun.monitoring_service.commons.dto.in;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

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
        @NotBlank
        String login,

        @NotBlank
        @Length(min = 4)
        String password,

        @NotBlank
        String country,

        @NotBlank
        String city,

        @NotBlank
        String street,

        @NotBlank
        String house,

        @NotBlank
        String level,

        @NotBlank
        String apartmentNumber
) {}