package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.security.JwtProvider;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.AuthorizationTokenDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final JwtProvider jwtProvider;
    private final AccountMapper accountMapper;
    private final AccountDao accountDao;

    @Override
    public AuthorizationTokenDtoOut authenticate(LoginPasswordDtoIn loginPassword) throws AuthenticationException {
        try {
            var account = accountDao.findByAccount_Login(loginPassword.login())
                    .orElseThrow(AccountNotFoundException::new);
            if (!account.getPassword().equals(loginPassword.password())) {
                throw new AuthenticationException("invalid login/password");
            }
            return new AuthorizationTokenDtoOut(jwtProvider.generateAccessToken(loginPassword));
        } catch (AccountNotFoundException e) {
            throw new AuthenticationException("account not found");
        }
    }

    @Override
    public AccountDtoOut register(AccountDtoIn accountDtoIn) {
        try {
            accountDao.findByAccount_Login(accountDtoIn.login())
                    .orElseThrow(IllegalArgumentException::new);
            accountDao.save(accountMapper.map(accountDtoIn));
            return new AccountDtoOut(accountDtoIn.login());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("account is exists");
        }
    }
}
