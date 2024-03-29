package com.munsun.monitoring_service.backend.services.impl;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.JwtProvider;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.services.SecurityService;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.AuthorizationTokenDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final JwtProvider jwtProvider;
    private final AccountMapper accountMapper;
    private final AccountDao accountDao;

    @Override
    public AuthorizationTokenDtoOut authenticate(LoginPasswordDtoIn loginPassword) throws AuthenticationException, AccountNotFoundException {
        var account = accountDao.findByAccount_Login(loginPassword.login())
                .orElseThrow(AccountNotFoundException::new);
        if (!account.getPassword().equals(loginPassword.password())) {
            throw new AuthenticationException("invalid login/password");
        }
        return new AuthorizationTokenDtoOut(jwtProvider.generateAccessToken(loginPassword));
    }

    @Override
    public AccountDtoOut register(AccountDtoIn accountDtoIn) {
        try {
            var account = accountDao.findByAccount_Login(accountDtoIn.login());
            if(account.isPresent()) {
                throw new IllegalArgumentException();
            }
            Account newAccount = accountMapper.map(accountDtoIn);
            newAccount.setRole(Role.USER);
            accountDao.save(newAccount);
            return new AccountDtoOut(accountDtoIn.login());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("account is exists");
        }
    }
}
