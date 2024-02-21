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
        @NotBlank(message = "login is empty")
        String login,

        @NotBlank(message = "password is empty")
        @Length(min = 4)
        String password,

        @NotBlank(message = "country is empty")
        String country,

        @NotBlank(message = "city is empty")
        String city,

        @NotBlank(message = "street is empty")
        String street,

        @NotBlank(message = "house is empty")
        String house,

        @NotBlank(message = "level is empty")
        String level,

        @NotBlank(message = "apartmentNumber is empty")
        String apartmentNumber
) {}