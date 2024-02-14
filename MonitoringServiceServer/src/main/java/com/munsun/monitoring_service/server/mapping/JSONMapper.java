package com.munsun.monitoring_service.server.mapping;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.server.exceptions.JsonParseExceptions;

import java.util.List;

public interface JSONMapper {
    LoginPasswordDtoIn toLoginPassword(String reader) throws JsonParseExceptions;

    AccountDtoIn toAccountDtoIn(String reader) throws JsonParseExceptions;

    MeterReadingsDtoIn toMeterReadingsDtoIn(String reader) throws JsonParseExceptions;

    <T> String toJSON(T obj) throws JsonParseExceptions;
    <T> String toJSON(List<T> obj) throws JsonParseExceptions;
}
