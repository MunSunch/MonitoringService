package com.munsun.monitoring_service.backend.tests.mapping.units;

import com.munsun.monitoring_service.backend.mapping.PlaceLivingMapper;
import com.munsun.monitoring_service.backend.mapping.impl.PlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlaceLivingMapperUnitTests {
    private PlaceLivingMapper placeLivingMapper = new PlaceLivingMapperImpl();

    @Test
    public void testToPlaceLiving() {
        var testAccountDtoIn = new AccountDtoIn(null, null,
                "testCountry", "testCity", "testStreet",
                "testHouse", "testLevel", "testApartment");
        var expected = new Account();
            var placeLiving = new PlaceLivingEmbedded();
                placeLiving.setCountry(testAccountDtoIn.country());
                placeLiving.setCity(testAccountDtoIn.city());
                placeLiving.setHouse(testAccountDtoIn.house());
                placeLiving.setStreet(testAccountDtoIn.street());
                placeLiving.setLevel(testAccountDtoIn.level());
                placeLiving.setApartmentNumber(testAccountDtoIn.apartmentNumber());
            expected.setPlaceLiving(placeLiving);

        var actual = placeLivingMapper.map(testAccountDtoIn);

        Assertions.assertThat(actual).isEqualTo(expected.getPlaceLiving());
    }
}
