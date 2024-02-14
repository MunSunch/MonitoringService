package com.munsun.monitoring_service.backend.tests.security;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.mapping.AccountMapper;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.security.impl.SecurityServiceImpl;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceUnitTests {
    @Mock
    private AccountDao accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private SecurityServiceImpl securityService;

    @DisplayName("Failed authentication of a non-existent user")
    @Test
    public void testNegativeAuthenticateNotExistsAccount() {
        when(accountRepository.findByAccount_Login(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            securityService.authenticate(new LoginPasswordDtoIn("", ""));
        }).isInstanceOf(AccountNotFoundException.class);
    }

    @DisplayName("Failed authentication of a user with an invalid password")
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
