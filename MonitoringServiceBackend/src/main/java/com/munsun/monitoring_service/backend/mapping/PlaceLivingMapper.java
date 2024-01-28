package com.munsun.monitoring_service.backend.mapping;

import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;

/**
 * An abstraction that contains functionality for converting DTO objects into embedded entities
 */
public interface PlaceLivingMapper {
    /**
     * extracts the embedded PlaceLivingEmbedded object from the AccountDtoIn object
     * @param accountDtoIn DTO object AccountDtoIn.java
     * @return PlaceLivingEmbedded
     */
    PlaceLivingEmbedded map(AccountDtoIn accountDtoIn);
}
