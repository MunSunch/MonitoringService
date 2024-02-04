package com.munsun.monitoring_service.backend.mapping.impl;

import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.mapping.PlaceLivingMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import lombok.RequiredArgsConstructor;

/**
 * Converter {@link AccountDtoIn} to {@link Account}
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final PlaceLivingMapper placeLivingMapper;

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public Account map(AccountDtoIn accountDtoIn) {
        Account newAccount = new Account();
            newAccount.setLogin(accountDtoIn.login());
            newAccount.setPassword(accountDtoIn.password());
            newAccount.setPlaceLiving(placeLivingMapper.map(accountDtoIn));
        return newAccount;
    }
}
