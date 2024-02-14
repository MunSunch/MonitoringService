package com.munsun.monitoring_service.backend.tests.mapping.units;

import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountMapperIntegrationTests {
    private AccountMapper accountMapper = AccountMapper.instance;

    @DisplayName("Positive test to convert DTO in entity")
    @Test
    public void testToAccount() {
        var testAccountDtoIn = new AccountDtoIn("testLogin","testPassword",
                "testCountry", "testCity", "testStreet",
                "testHouse", "testLevel", "testApartment");
        var expected = Account.builder()
                .login(testAccountDtoIn.login())
                .password(testAccountDtoIn.password())
                .placeLiving(PlaceLivingEmbedded.builder()
                        .country(testAccountDtoIn.country())
                        .city(testAccountDtoIn.city())
                        .house(testAccountDtoIn.house())
                        .street(testAccountDtoIn.street())
                        .level(testAccountDtoIn.level())
                        .apartmentNumber(testAccountDtoIn.apartmentNumber())
                        .build())
                .build();

        var actual = accountMapper.map(testAccountDtoIn);

        assertThat(actual).isEqualTo(expected);
    }
}