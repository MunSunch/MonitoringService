package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.security.SimpleTokenProvider;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.AuthorizationTokenDtoOut;
import com.munsun.monitoring_service.commons.enums.Endpoints;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * <p>SecurityServiceImpl class.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final AccountDao accountRepository;
    private final AccountMapper accountMapper;
    private final SimpleTokenProvider jwtProvider;

    private final List<Endpoints> allowedEveryOneEndpoints = List.of(Endpoints.LOGIN,
                                                                     Endpoints.REGISTER);
    private final List<Endpoints> allowedUsersEndpoints = List.of(Endpoints.GET_HISTORY,
                                                                  Endpoints.GET_ACTUAL_METER_READING,
                                                                  Endpoints.GET_METER_READING_BY_MONTH,
                                                                  Endpoints.SAVE_NEW_METER_READING);
    private final List<Endpoints> allowedAdminsEndpoints = List.of(Endpoints.EXPAND_METER_READING,
                                                                   Endpoints.GET_ACTUAL_METER_READINGS_ALL_USERS,
                                                                   Endpoints.GET_ALL_HISTORY_ALL_USERS,
                                                                   Endpoints.GET_ALL_HISTORY_ALL_USERS_BY_MONTH);

    /** {@inheritDoc} */
    @Override
    public AuthorizationTokenDtoOut authenticate(LoginPasswordDtoIn loginPassword) throws AccountNotFoundException, AuthenticationException {
        var account = accountRepository.findByAccount_Login(loginPassword.login())
                .orElseThrow(AccountNotFoundException::new);
        if(account.isBlocked()) {
            throw new AuthenticationException("Account is blocked!");
        }
        if(!account.getPassword().equals(loginPassword.password())) {
            throw new AuthenticationException("Incorrect login/password!");
        }
        return new AuthorizationTokenDtoOut(jwtProvider.generateToken(account));
    }

    /** {@inheritDoc} */
    @Override
    public AccountDtoOut register(AccountDtoIn accountDtoIn) {
        var newAccount = accountMapper.map(accountDtoIn);
            newAccount.setRole(Role.USER);
        try {
            var res = accountRepository.save(newAccount);
            return res == 1? new AccountDtoOut(accountDtoIn.login()): null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Account is exists!");
        }
    }

    @Override
    public boolean authorization(SecurityUser securityUser, Endpoints api) {
        Role role = securityUser.getRole();
        Boolean statusAccess = false;
        switch (role) {
            case USER -> {
                statusAccess = allowedUsersEndpoints.contains(api);
            }
            case ADMIN -> {
                statusAccess = allowedAdminsEndpoints.contains(api);
            }
        }
        return statusAccess;
    }

    @Override
    public boolean authorization(Endpoints api) {
        return allowedEveryOneEndpoints.contains(api);
    }
}
