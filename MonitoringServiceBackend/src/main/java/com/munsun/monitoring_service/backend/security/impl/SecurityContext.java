package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.security.model.SecurityUser;

public class SecurityContext {
    private static SecurityUser securityUser;

    public static void setSecurityUser(SecurityUser user) {
        securityUser = user;
    }

    public static SecurityUser getSecurityUser() {
        return securityUser;
    }

    public static void clear() {
        securityUser = null;
    }

    private SecurityContext() {}
}
