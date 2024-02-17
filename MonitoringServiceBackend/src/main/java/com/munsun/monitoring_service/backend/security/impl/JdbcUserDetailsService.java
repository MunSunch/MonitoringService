package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.security.models.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class JdbcUserDetailsService implements UserDetailsService {
    private final AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountDao.findByAccount_Login(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return new SecurityUser(account.getId(), account.getLogin(), account.getPassword(), account.getRole());
    }
}
