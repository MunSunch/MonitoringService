package com.munsun.monitoring_service.backend.tests.mapping.units;

import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.mapping.impl.AccountMapperImpl;
import com.munsun.monitoring_service.backend.mapping.impl.PlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountMapperIntegrationTests {
    private AccountMapper accountMapper = new AccountMapperImpl(new PlaceLivingMapperImpl());

    @Test
    public void testToAccount() {
        var testAccountDtoIn = new AccountDtoIn("testLogin","testPassword",
                "testCountry", "testCity", "testStreet",
                "testHouse", "testLevel", "testApartment");
        var expected = new Account();
            expected.setId(null);
            expected.setBlocked(false);
            expected.setLogin(testAccountDtoIn.login());
            expected.setPassword(testAccountDtoIn.password());
            expected.setRole(null);
            var placeLiving = new PlaceLivingEmbedded();
                placeLiving.setCountry(testAccountDtoIn.country());
                placeLiving.setCity(testAccountDtoIn.city());
                placeLiving.setHouse(testAccountDtoIn.house());
                placeLiving.setStreet(testAccountDtoIn.street());
                placeLiving.setLevel(testAccountDtoIn.level());
                placeLiving.setApartmentNumber(testAccountDtoIn.apartmentNumber());
            expected.setPlaceLiving(placeLiving);

        var actual = accountMapper.map(testAccountDtoIn);

        assertThat(actual).isEqualTo(expected);
    }
}
