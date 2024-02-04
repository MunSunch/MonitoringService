package com.munsun.monitoring_service.backend.mapping;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;

/**
 * An abstraction that contains functionality for converting DTO objects into entities
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface AccountMapper {
    /**
     * Сonvert {@linkplain com.munsun.monitoring_service.commons.dto.in.AccountDtoIn AccountDtoIn}
     * to {@linkplain com.munsun.monitoring_service.backend.models.Account Account}
     *
     * @param accountDtoIn DTO object AccountDtoIn.java
     * @return Account entity
     */
    Account map(AccountDtoIn accountDtoIn);
}
