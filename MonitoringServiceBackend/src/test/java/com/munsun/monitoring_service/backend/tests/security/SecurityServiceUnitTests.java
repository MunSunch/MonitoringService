package com.munsun.monitoring_service.backend.tests.security;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.repositories.AccountRepository;
import com.munsun.monitoring_service.backend.security.SecurityContext;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.backend.security.impl.SecurityServiceImpl;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class SecurityServiceUnitTests {
    private AccountRepository accountRepository = mock();
    private SecurityService securityService = new SecurityServiceImpl(accountRepository,
            mock(AccountMapper.class), mock(SecurityContext.class));

    @Test
    public void testNegativeAuthenticateNotExistsAccount() {
        when(accountRepository.findByAccount_Login(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            securityService.authenticate(new LoginPasswordDtoIn("", ""));
        }).isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    public void testNegativeAuthenticateExistsAccount() {
        var account = new Account();
            account.setLogin("login");
            account.setPassword("non-password");
        when(accountRepository.findByAccount_Login(any()))
                .thenReturn(Optional.of(account));

        assertThatThrownBy(() -> {
            securityService.authenticate(new LoginPasswordDtoIn("login", "password"));
        }).isInstanceOf(AuthenticationException.class);
    }
}
