package com.munsun.monitoring_service.backend.security.enums;

import java.util.Set;

public enum Role {
    USER(Set.of("read", "write")),
    ADMIN(Set.of("allRead"));

    private Set<String> permissions;

    Role(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
