package com.munsun.monitoring_service.commons.tests.utils.validator;

import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.utils.validator.Validator;
import com.munsun.monitoring_service.commons.utils.validator.exceptions.ValidationException;
import com.munsun.monitoring_service.commons.utils.validator.impl.DefaultValidator;
import com.munsun.monitoring_service.commons.utils.validator.impl.annotations.NotEmptyOrNull;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidatorInitTests {
    private Validator validator = new DefaultValidator();

    @Test
    public void test() {
        LoginPasswordDtoIn loginPasswordDtoIn = new LoginPasswordDtoIn("", "1234567890");
        var expectedException = ValidationException.class;
        var expectedExceptionMessage = "login:string is empty or null;password:size is exceeded";

        assertThatThrownBy(() -> validator.validate(loginPasswordDtoIn))
                .isInstanceOf(expectedException)
                .hasMessageContaining(expectedExceptionMessage);
    }
}
