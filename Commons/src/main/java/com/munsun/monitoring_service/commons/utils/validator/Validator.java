package com.munsun.monitoring_service.commons.utils.validator;

import com.munsun.monitoring_service.commons.utils.validator.exceptions.ValidationException;

public interface Validator {
    <T> void validate(T obj) throws ValidationException;
}
