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
        var placeLiving = new PlaceLivingEmbedded();
            placeLiving.setCountry(accountDtoIn.country());
            placeLiving.setCity(accountDtoIn.city());
            placeLiving.setStreet(accountDtoIn.street());
            placeLiving.setHouse(accountDtoIn.house());
            placeLiving.setLevel(accountDtoIn.level());
            placeLiving.setApartmentNumber(accountDtoIn.apartmentNumber());
        return placeLiving;
    }
}
