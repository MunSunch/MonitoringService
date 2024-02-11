package com.munsun.monitoring_service.commons.utils.validator.impl;

import com.munsun.monitoring_service.commons.utils.validator.Validator;
import com.munsun.monitoring_service.commons.utils.validator.exceptions.ValidationException;
import com.munsun.monitoring_service.commons.utils.validator.impl.annotations.NotEmptyOrNull;
import com.munsun.monitoring_service.commons.utils.validator.impl.annotations.Size;

import java.lang.reflect.Field;

public class DefaultValidator implements Validator {
    private static final String SEPARATOR = ":";
    private static final String DELIMITER = ";";
    private static final String EMPTY_STRING = "";

    @Override
    public <T> void validate(T obj) throws ValidationException {
        StringBuilder result = new StringBuilder();
        for (var field: obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            result.append(handlerEmptyOrNullAnnotation(field, obj));
            result.append(handlerSizeAnnotation(field, obj));
        }
        if(!result.isEmpty()) {
            throw new ValidationException(result.toString());
        }
    }

    private <T> String handlerEmptyOrNullAnnotation(Field field, T obj) throws ValidationException {
        if(field.isAnnotationPresent(NotEmptyOrNull.class)) {
            try {
                if(isEmptyOrNullString((String)field.get(obj))) {
                    return field.getName() + SEPARATOR
                            + field.getAnnotation(NotEmptyOrNull.class).message()
                            + DELIMITER;
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e.getMessage());
            }
        }
        return EMPTY_STRING;
    }

    private <T> String handlerSizeAnnotation(Field field, T obj) throws ValidationException {
        if(field.isAnnotationPresent(Size.class)) {
            int expectedSize = field.getAnnotation(Size.class).limit();
            try {
                if(isSize(expectedSize, (String)field.get(obj))) {
                    return field.getName() + SEPARATOR
                            + field.getAnnotation(Size.class).message()
                            + DELIMITER;
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e.getMessage());
            }
        }
        return EMPTY_STRING;
    }

    private boolean isEmptyOrNullString(String str) {
        return str==null || str.equals(EMPTY_STRING);
    }

    private boolean isSize(int expectedSize, String str) {
        return str.length() < expectedSize;
    }
}
