package com.munsun.monitoring_service.backend.mapping.impl;

import com.munsun.monitoring_service.backend.mapping.PlaceLivingMapper;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;

/**
 * Converter from the {@linkplain AccountDtoIn} input object
 * to the {@linkplain PlaceLivingEmbedded} entity
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public class PlaceLivingMapperImpl implements PlaceLivingMapper {
    /**
     * {@inheritDoc}
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
