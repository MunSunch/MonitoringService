package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.repositories.AccountRepository;
import com.munsun.monitoring_service.backend.security.SecurityContext;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final SecurityContext securityContext;

    @Override
    public void authenticate(LoginPasswordDtoIn loginPassword) throws AccountNotFoundException, AuthenticationException {
        var account = accountRepository.findByAccount_Login(loginPassword.login())
                .orElseThrow(AccountNotFoundException::new);
        if(account.isBlocked()) {
            throw new AuthenticationException("Account is blocked!");
        }
        if(!account.getPassword().equals(loginPassword.password())) {
            throw new AuthenticationException("Incorrect login/password!");
        }
        securityContext.setCurrentAuthorizedAccount(account);
    }

    @Override
    public AccountDtoOut register(AccountDtoIn accountDtoIn) {
        var newAccount = accountMapper.map(accountDtoIn);
            newAccount.setRole(Role.USER);
        try {
            var account = accountRepository.save(newAccount);
            return new AccountDtoOut(account.getLogin());
        } catch (DatabaseConstraintException e) {
            throw new IllegalArgumentException("Аккаунт с таким логином уже существует");
        }
    }

    @Override
    public void logout() {
        securityContext.clear();
    }

    public Account getCurrentAuthorizedAccount() {
        return securityContext.getAuthorizedAccount();
    }

    @Override
    public boolean getAccess(Role role, ItemsMainMenu item) {
        return securityContext.isAccessAllowed(role, item);
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }
}
