package com.munsun.monitoring_service.backend.mapping;

import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;

/**
 * An abstraction that contains functionality for converting DTO objects into embedded entities
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface PlaceLivingMapper {
    /**
     * extracts the embedded {@linkplain PlaceLivingEmbedded} object from
     * the {@linkplain AccountDtoIn} object
     *
     * @param accountDtoIn DTO object {@linkplain AccountDtoIn}
     * @return {@linkplain PlaceLivingEmbedded}
     */
    PlaceLivingEmbedded map(AccountDtoIn accountDtoIn);
}
