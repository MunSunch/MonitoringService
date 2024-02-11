package com.munsun.monitoring_service.server.tests.mapping;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.utils.validator.Validator;
import com.munsun.monitoring_service.commons.utils.validator.impl.DefaultValidator;
import com.munsun.monitoring_service.server.exceptions.JsonParseExceptions;
import com.munsun.monitoring_service.server.mapping.JSONMapper;
import com.munsun.monitoring_service.server.mapping.impl.JsonMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

public class JsonMapperIntegrationTests {
    private Validator validator = new DefaultValidator();
    private JSONMapper jsonMapper = new JsonMapperImpl(validator);

    @DisplayName("Parsing JSON in LoginPasswordDtoIn with a non-empty username and a non-empty password of the correct length")
    @Test
    public void positiveTestToLoginPassword() throws JsonParseExceptions {
        var expectedLoginPasswordDtoIn = new LoginPasswordDtoIn("user", "user");

        AtomicReference<LoginPasswordDtoIn> actualLoginPassword = new AtomicReference<>();
        assertThatNoException().isThrownBy(()->{
            actualLoginPassword.set(jsonMapper.toLoginPassword("{\n\"login\":\"user\",\n\"password\":\"user\"\n}"));
        });

        assertThat(actualLoginPassword.get())
                .isNotNull()
                .extracting(LoginPasswordDtoIn::login, LoginPasswordDtoIn::password)
                .containsExactly(expectedLoginPasswordDtoIn.login(), expectedLoginPasswordDtoIn.password());
    }

    @DisplayName("Parsing JSON in LoginPasswordDtoIn without specifying a login")
    @Test
    public void negativeTestToLoginPassword() throws JsonParseExceptions {
        var expectedException = JsonParseExceptions.class;
        var expectedExceptionMessage = "login:string is empty or null";

        assertThatThrownBy(() -> jsonMapper.toLoginPassword("{\n\"password\":\"user\"\n}"))
                .isInstanceOf(expectedException)
                .hasMessageContaining(expectedExceptionMessage);
    }

    @DisplayName("Success JSON parsing in AccountDtoIn with validation")
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


        AtomicReference<AccountDtoIn> actualAccountDtoIn = new AtomicReference<>();
        assertThatNoException().isThrownBy(()-> {
                    actualAccountDtoIn.set(jsonMapper.toAccountDtoIn("{\"login\": \"user\"," +
                            "\"password\": \"user\"," +
                            "\"country\": \"Russia\"," +
                            "\"city\": \"Moscow\"," +
                            "\"street\": \"Red\"," +
                            "\"house\": \"19A\"," +
                            "\"level\": \"5\"," +
                            "\"apartmentNumber\": \"11B\"}"));
        });

        assertThat(actualAccountDtoIn.get())
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
}