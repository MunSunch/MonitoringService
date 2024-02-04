package com.munsun.monitoring_service.commons.dto.in;

/**
 * DTO object for storing the number of the transmitted menu item from the input
 * @param command
 */
public record SelectedUserItemDtoIn(
        Integer command
) {}