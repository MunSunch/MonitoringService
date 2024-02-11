package com.munsun.monitoring_service.server.mapping.impl;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.server.exceptions.ValidationException;
import com.munsun.monitoring_service.server.mapping.Validator;
import com.munsun.monitoring_service.server.mapping.impl.annotations.NotEmptyOrNull;

import java.lang.annotation.Annotation;
import java.util.List;

public class DefaultValidator implements Validator {
    private final List<?> annotaions = List.of(NotEmptyOrNull.class);
    @Override
    public void validate(AccountDtoIn accountDtoIn) throws ValidationException {

    }

    @Override
    public void validate(LoginPasswordDtoIn loginPasswordDtoIn) {
        loginPasswordDtoIn.getClass().isAnnotationPresent(NotEmptyOrNull.class);
    }

    @Override
    public void validate(MeterReadingsDtoIn meterReadingsDtoIn) {

    }
}
