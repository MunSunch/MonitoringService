package com.munsun.monitoring_service.backend.tests.mapping.units;

import com.munsun.monitoring_service.backend.mapping.PlaceLivingMapper;
import com.munsun.monitoring_service.backend.mapping.impl.AccountMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AccountMapperUnitTests {
    @Mock
    private PlaceLivingMapper placeLivingMapper;
    @InjectMocks
    private AccountMapperImpl accountMapper;

    @DisplayName("Successful conversion of a DTO into an entity")
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

        Mockito.when(placeLivingMapper.map(any())).thenReturn(expected.getPlaceLiving());
        var actual = accountMapper.map(testAccountDtoIn);

        assertThat(actual).isEqualTo(expected);
    }
}