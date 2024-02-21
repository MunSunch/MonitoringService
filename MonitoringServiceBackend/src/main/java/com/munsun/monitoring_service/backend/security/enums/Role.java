package com.munsun.monitoring_service.backend.security.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Role class.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public enum Role {
    USER(Set.of("read", "write")),
    ADMIN(Set.of("allRead"));

    private Set<String> permissions;

    Role(Set<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * <p>Getter for the field <code>permissions</code>.</p>
     *
     * @return a {@link java.util.Set} object
     */
    public Collection<GrantedAuthority> getPermissions() {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
