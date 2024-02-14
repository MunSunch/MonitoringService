package com.munsun.monitoring_service.backend.security.model;

import com.munsun.monitoring_service.backend.security.enums.Role;

public record Token(
        Long id,
        Role role
) {}