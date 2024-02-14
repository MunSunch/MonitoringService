package com.munsun.monitoring_service.server.tests.mapping;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.utils.validator.Validator;
import com.munsun.monitoring_service.server.exceptions.JsonParseExceptions;
import com.munsun.monitoring_service.server.mapping.impl.JsonMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JsonMapperUnitTests {
    @Mock
    private Validator validator;
    @InjectMocks
    private JsonMapperImpl jsonMapper;

    @DisplayName("Success JSON parsing in LoginPasswordDtoIn")
    @Test
    public void positiveTestToLoginPassword() throws JsonParseExceptions {
        var expectedLoginPasswordDtoIn = new LoginPasswordDtoIn("user", "user");

        var actualLoginPassword = jsonMapper.toLoginPassword("{\n\"login\":\"user\",\n\"password\":\"user\"\n}");

        assertThat(actualLoginPassword)
                .extracting(LoginPasswordDtoIn::login, LoginPasswordDtoIn::password)
                .containsExactly(expectedLoginPasswordDtoIn.login(), expectedLoginPasswordDtoIn.password());
    }

    @DisplayName("Success JSON parsing in MeterReadingsDtoIn")
    @Test
    public void positiveTestToMeterReadingsDtoIn() throws JsonParseExceptions {
        var expectedMeterReadingsDtoIn = new MeterReadingsDtoIn(Map.of("electricity", 111L,
                                                                       "heating", 222L));

        var actualMeterReadingsDtoIn =
                jsonMapper.toMeterReadingsDtoIn("{\n\"electricity\":\"111\",\n\"heating\":222\n}");

        assertThat(actualMeterReadingsDtoIn)
                .extracting(MeterReadingsDtoIn::readings)
                .isEqualTo(expectedMeterReadingsDtoIn.readings());
    }

    @DisplayName("Success JSON parsing in AccountDtoIn without validation")
    @Test
    public void positiveTestToAccountDtoIn() throws JsonParseExceptions {
        var expectedAccountDtoIn = new AccountDtoIn("user",
                "user",
                "Russia",
                "Moscow",
                "Red",
                "19A",
                "5",
                "11B");

        AccountDtoIn actualAccountDtoIn = jsonMapper.toAccountDtoIn("{\"login\": \"user\"," +
                    "\"password\": \"user\"," +
                    "\"country\": \"Russia\"," +
                    "\"city\": \"Moscow\"," +
                    "\"street\": \"Red\"," +
                    "\"house\": \"19A\"," +
                    "\"level\": \"5\"," +
                    "\"apartmentNumber\": \"11B\"}");

        assertThat(actualAccountDtoIn)
                .isNotNull()
                .extracting(AccountDtoIn::login,
                        AccountDtoIn::password,
                        AccountDtoIn::country,
                        AccountDtoIn::city)
                .containsExactly(expectedAccountDtoIn.login(),
                        expectedAccountDtoIn.password(),
                        expectedAccountDtoIn.country(),
                        expectedAccountDtoIn.city());
    }

    @DisplayName("Success AccountDtoIn parsing in JSON")
    @Test
    public void positiveTestAccountDtoInToJSON() throws JsonParseExceptions {
        var accountDtoIn = new AccountDtoIn("user",
                "user",
                "Russia",
                "Moscow",
                "Red",
                "19A",
                "5",
                "11B");
        var expectedJson = "{\"login\": \"user\"," +
                "\"password\": \"user\"," +
                "\"country\": \"Russia\"," +
                "\"city\": \"Moscow\"," +
                "\"street\": \"Red\"," +
                "\"house\": \"19A\"," +
                "\"level\": \"5\"," +
                "\"apartmentNumber\": \"11B\"}";

        var actualJson = jsonMapper.toJSON(accountDtoIn);

        assertThat(actualJson).isEqualTo(actualJson);
    }

    @DisplayName("Success MeterReadings parsing in JSON")
    @Test
    public void positiveTestMeterReadingsToJSON() throws JsonParseExceptions {
        var meterReadingsDtoIn = new MeterReadingsDtoIn(Map.of("electricity", 111L,
                                                               "heating", 222L));
        var expectedJSON = "{\"readings\":{\"heating\":222,\"electricity\":111}}";

        var actualJson = jsonMapper.toJSON(meterReadingsDtoIn);

        assertThat(actualJson).isEqualTo(expectedJSON);
    }
}