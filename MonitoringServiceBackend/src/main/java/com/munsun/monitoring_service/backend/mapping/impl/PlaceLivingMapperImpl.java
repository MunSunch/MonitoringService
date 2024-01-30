package com.munsun.monitoring_service.backend.mapping.impl;

import com.munsun.monitoring_service.backend.mapping.PlaceLivingMapper;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;

/**
 * Converter from the AccountDtoIn input object to the PlaceLivingEmbedded entity
 */
public class PlaceLivingMapperImpl implements PlaceLivingMapper {
    /**
     * Extracts the PlaceLivingEmbedded entity from the AccountDtoIn object.
     * @param accountDtoIn DTO object AccountDtoIn.java
     * @return PlaceLivingEmbedded
     */
    @Override
    public PlaceLivingEmbedded map(AccountDtoIn accountDtoIn) {
         return PlaceLivingEmbedded.builder()
                        .country(accountDtoIn.country())
                        .city(accountDtoIn.city())
                        .street(accountDtoIn.street())
                        .house(accountDtoIn.house())
                        .level(accountDtoIn.level())
                        .apartmentNumber(accountDtoIn.apartmentNumber())
                .build();
    }
}