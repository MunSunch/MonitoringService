package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.SimpleTokenProvider;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;
import com.munsun.monitoring_service.backend.security.model.Token;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class SimpleTokenProviderImpl implements SimpleTokenProvider {
    private static final String SECURITY_HEADER = "auth";
    private final AccountDao accountDao;

    @Override
    public String generateToken(Account account) {
        return String.format("id:%d,role:%s", account.getId(), account.getRole().name());
    }

    @Override
    public boolean validateToken(String token) throws AuthenticationException {
        try{
            var result = parse(token);
            if(result.isEmpty())
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Optional<Token> parse(String token) {
        String[] parts = token.split(",");
        if(parts.length==2) {
            Role role = Role.valueOf(parts[1].split(":")[1]);
            Long id = Long.parseLong(parts[0].split(":")[1]);
            return Optional.of(new Token(id, role));
        }
        return Optional.empty();
    }

    @Override
    public SecurityUser getSecurityUser(String token) throws AccountNotFoundException {
        Optional<Account> user = null;
        try {
            var userId = parse(token).get().id();
            user = accountDao.getById(userId);
        } catch (SQLException|NullPointerException e) {
            throw new AccountNotFoundException();
        }
        return SecurityUser.toSecurityUser(user.get());
    }

    @Override
    public String resolveToken(HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(SECURITY_HEADER);
        if(token == null) {
            throw new AuthenticationException("token is empty or not validate");
        }
        return token;
    }
}
