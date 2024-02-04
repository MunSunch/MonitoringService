package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.dao.AccountRepository;
import com.munsun.monitoring_service.backend.security.SecurityContext;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import lombok.RequiredArgsConstructor;

/**
 * <p>SecurityServiceImpl class.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final SecurityContext securityContext;

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public AccountDtoOut register(AccountDtoIn accountDtoIn) {
        var newAccount = accountMapper.map(accountDtoIn);
            newAccount.setRole(Role.USER);
        try {
            var res = accountRepository.save(newAccount);
            return res == 1? new AccountDtoOut(accountDtoIn.login()): null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Аккаунт с таким логином уже существует");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void logout() {
        securityContext.clear();
    }

    /**
     * <p>getCurrentAuthorizedAccount.</p>
     *
     * @return a {@link com.munsun.monitoring_service.backend.models.Account} object
     */
    public Account getCurrentAuthorizedAccount() {
        return securityContext.getAuthorizedAccount();
    }

    /** {@inheritDoc} */
    @Override
    public boolean getAccess(Role role, ItemsMainMenu item) {
        return securityContext.isAccessAllowed(role, item);
    }

    /**
     * <p>Getter for the field <code>securityContext</code>.</p>
     *
     * @return a {@link com.munsun.monitoring_service.backend.security.SecurityContext} object
     */
    public SecurityContext getSecurityContext() {
        return securityContext;
    }
}
