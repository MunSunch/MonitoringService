package com.munsun.monitoring_service.backend.tests.security;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.security.impl.DefaultJwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtProviderUnitTests {
    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private DefaultJwtProvider jwtProvider;

    @Test
    public void test() {
//        jwtProvider.generateAccessToken()
    }
}
