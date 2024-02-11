package com.munsun.monitoring_service.server.mapping;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.server.exceptions.ValidationException;

public interface Validator {
    void validate(AccountDtoIn accountDtoIn) throws ValidationException;
    void validate(LoginPasswordDtoIn loginPasswordDtoIn);
    void validate(MeterReadingsDtoIn meterReadingsDtoIn);
}
