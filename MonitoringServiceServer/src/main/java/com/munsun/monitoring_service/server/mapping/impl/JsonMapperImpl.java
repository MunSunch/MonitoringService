package com.munsun.monitoring_service.server.mapping.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.utils.validator.Validator;
import com.munsun.monitoring_service.commons.utils.validator.exceptions.ValidationException;
import com.munsun.monitoring_service.server.exceptions.JsonParseExceptions;
import com.munsun.monitoring_service.server.mapping.JSONMapper;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class JsonMapperImpl implements JSONMapper {
    private final Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public LoginPasswordDtoIn toLoginPassword(String json) throws JsonParseExceptions {
        try {
            var loginPasswordDtoIn = objectMapper.readValue(json, LoginPasswordDtoIn.class);
            validator.validate(loginPasswordDtoIn);
            return loginPasswordDtoIn;
        } catch (JsonProcessingException|ValidationException e) {
            throw new JsonParseExceptions(e.getMessage());
        }
    }

    @Override
    public MeterReadingsDtoIn toMeterReadingsDtoIn(String json) throws JsonParseExceptions {
        try {
            TypeReference<HashMap<String, Long>> typeRef = new TypeReference<>() {};
            var map = objectMapper.readValue(json, typeRef);
            return new MeterReadingsDtoIn(map);
        } catch (JsonProcessingException e) {
            throw new JsonParseExceptions("Invalid input JSON format! Correct format: {string:number,....}");
        }
    }


    @Override
    public AccountDtoIn toAccountDtoIn(String json) throws JsonParseExceptions {
        try {
            var accountDtoIn = objectMapper.readValue(json, AccountDtoIn.class);
            validator.validate(accountDtoIn);
            return accountDtoIn;
        } catch (JsonProcessingException | ValidationException e) {
            throw new JsonParseExceptions(e.getMessage());
        }
    }

    @Override
    public <T> String toJSON(T obj) throws JsonParseExceptions {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonParseExceptions(e.getMessage());
        }
    }

    @Override
    public <T> String toJSON(List<T> obj) throws JsonParseExceptions {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonParseExceptions(e.getMessage());
        }
    }
}
