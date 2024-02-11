package com.munsun.monitoring_service.backend.tests.security;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.SimpleTokenProvider;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.security.impl.SimpleTokenProviderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SimpleTokenProviderUnitsTests {
    @Mock
    private AccountDao accountDao;
    @InjectMocks
    private SimpleTokenProviderImpl tokenProvider;

    @Test
    void test() {
        var testAccount = Account.builder()
                                            .id(11L)
                                            .role(Role.USER)
                                          .build();
        var expectedToken = "id:11,role:USER";

        var actualToken = tokenProvider.generateToken(testAccount);

        assertThat(actualToken)
                .isEqualTo(expectedToken);
    }
}
