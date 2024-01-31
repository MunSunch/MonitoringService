package com.munsun.monitoring_service.backend.models.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

/**
 * An embedded object that contains information about the user's place of residence
 *
 * @author apple
 * @version $Id: $Id
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
