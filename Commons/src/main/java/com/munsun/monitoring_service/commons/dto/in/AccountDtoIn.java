package com.munsun.monitoring_service.commons.dto.in;

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
        String login,
        String password,
        String country,
        String city,
        String street,
        String house,
        String level,
        String apartmentNumber
) {}