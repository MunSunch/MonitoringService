package com.munsun.monitoring_service.backend.security.enums;

import java.util.Set;

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
    public Set<String> getPermissions() {
        return permissions;
    }
}
