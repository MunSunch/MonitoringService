package com.munsun.monitoring_service.backend.mapping;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * An abstraction that contains functionality for converting DTO objects into entities
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper instance = Mappers.getMapper(AccountMapper.class);

    /**
     * Сonvert {@linkplain com.munsun.monitoring_service.commons.dto.in.AccountDtoIn AccountDtoIn}
     * to {@linkplain com.munsun.monitoring_service.backend.models.Account Account}
     *
     * @param dtoIn DTO object AccountDtoIn.java
     * @return Account entity
     */
    @Mapping(target = "placeLiving", source = ".")
    Account map(AccountDtoIn dtoIn);
}
