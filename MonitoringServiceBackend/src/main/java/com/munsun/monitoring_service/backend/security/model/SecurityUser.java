package com.munsun.monitoring_service.backend.security.model;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SecurityUser {
    private Long id;
    private String login;
    private String password;
    private Role role;

    public static SecurityUser toSecurityUser(Account account) {
        return new SecurityUser(account.getId(),
                                account.getLogin(),
                                account.getPassword(),
                                account.getRole());
    }
}
