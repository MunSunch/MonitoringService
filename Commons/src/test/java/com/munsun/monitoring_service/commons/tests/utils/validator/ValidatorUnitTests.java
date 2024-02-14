package com.munsun.monitoring_service.commons.tests.utils.validator;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.utils.validator.Validator;
import com.munsun.monitoring_service.commons.utils.validator.exceptions.ValidationException;
import com.munsun.monitoring_service.commons.utils.validator.impl.DefaultValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidatorUnitTests {
    private Validator validator = new DefaultValidator();

    @DisplayName("Validation of LoginPasswordDtoIn with an empty username and password exceeding the specified length")
    @Test
    public void negativeValidationLoginPasswordDtoIn_EmptyLoginAndIncorrectPassword() {
        LoginPasswordDtoIn loginPasswordDtoIn = new LoginPasswordDtoIn("", "1234567890");
        var expectedException = ValidationException.class;
        var expectedExceptionMessage = "login:string is empty or null;password:size is exceeded";

        assertThatThrownBy(() -> validator.validate(loginPasswordDtoIn))
                .isInstanceOf(expectedException)
                .hasMessageContaining(expectedExceptionMessage);
    }

    @DisplayName("Validation of LoginPasswordDtoIn with an empty username and and an empty password")
    @Test
    public void negativeValidationLoginPasswordDtoIn_EmptyLoginAndEmptyPassword() {
        LoginPasswordDtoIn loginPasswordDtoIn = new LoginPasswordDtoIn("", "");
        var expectedException = ValidationException.class;
        var expectedExceptionMessage = "login:string is empty or null;password:string is empty or null;";

        assertThatThrownBy(() -> validator.validate(loginPasswordDtoIn))
                .isInstanceOf(expectedException)
                .hasMessageContaining(expectedExceptionMessage);
    }

    @DisplayName("Validation of LoginPasswordDtoIn with a non-empty username and a non-empty password of the specified length")
    @Test
    public void positiveValidationLoginPasswordDtoIn() throws ValidationException {
        LoginPasswordDtoIn loginPasswordDtoIn = new LoginPasswordDtoIn("user", "user");

        assertThatNoException()
                .isThrownBy(() -> validator.validate(loginPasswordDtoIn));
    }

    @DisplayName("Validation of AccountDtoIn with a non-empty username and a non-empty password of the set length and other non-empty fields")
    @Test
    public void positiveValidationAccountDtoIn_EmptyLoginAndIncorrectPassword() {
        var accountDtoIn = new AccountDtoIn("user",
                                               "user",
                                                "Russia",
                                                    "Moscow",
                                                  "Red",
                                                  "Kreml",
                                                    "1",
                                         "President");

        assertThatNoException()
                .isThrownBy(() -> validator.validate(accountDtoIn));
    }

    @DisplayName("Validation of AccountDtoIn with a non-empty username and a non-empty password of the set length and other non-empty fields")
    @Test
    public void negativeValidationAccountDtoIn_EmptyLoginAndIncorrectPasswordAndOtherEmptyFields() {
        var accountDtoIn = new AccountDtoIn("",
                "",
                "",
                "",
                "",
                "",
                "",
                "");
        var expectedException = ValidationException.class;
        var expectedExceptionMessage = "login:string is empty or null;" +
                                        "password:string is empty or null;" +
                                        "country:string is empty or null;" +
                                        "city:string is empty or null;" +
                                        "street:string is empty or null;" +
                                        "house:string is empty or null;" +
                                        "level:string is empty or null;" +
                                        "apartmentNumber:string is empty or null;";

        assertThatThrownBy(() -> validator.validate(accountDtoIn))
                .isInstanceOf(expectedException)
                .hasMessageContaining(expectedExceptionMessage);
    }
}
