package com.munsun.monitoring_service.backend.tests.mapping.units;

import com.munsun.monitoring_service.backend.mapping.PlaceLivingMapper;
import com.munsun.monitoring_service.backend.mapping.impl.PlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaceLivingMapperUnitTests {
    private PlaceLivingMapper placeLivingMapper = new PlaceLivingMapperImpl();

    @DisplayName("Successful extraction of the embedded entity from the DTO")
    @Test
    public void testToPlaceLiving() {
        var testAccountDtoIn = new AccountDtoIn(null, null,
                "testCountry", "testCity", "testStreet",
                "testHouse", "testLevel", "testApartment");
        var expected = Account.builder()
                                    .placeLiving(PlaceLivingEmbedded.builder()
                                                .country(testAccountDtoIn.country())
                                                .city(testAccountDtoIn.city())
                                                .house(testAccountDtoIn.house())
                                                .street(testAccountDtoIn.street())
                                                .level(testAccountDtoIn.level())
                                                .apartmentNumber(testAccountDtoIn.apartmentNumber())
                                            .build())
                                .build();

        var actual = placeLivingMapper.map(testAccountDtoIn);

        Assertions.assertThat(actual).isEqualTo(expected.getPlaceLiving());
    }
}