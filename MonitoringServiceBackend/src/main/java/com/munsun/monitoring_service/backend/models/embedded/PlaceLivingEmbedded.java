package com.munsun.monitoring_service.backend.models.embedded;

import lombok.*;

/**
 * An embedded object that contains information about the user's place of residence
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@Builder
@Getter @Setter
@EqualsAndHashCode
public class PlaceLivingEmbedded {
    private String country;
    private String city;
    private String street;
    private String house;
    private String level;
    private String apartmentNumber;
}
