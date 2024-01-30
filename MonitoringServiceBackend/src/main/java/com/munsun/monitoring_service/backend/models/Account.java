package com.munsun.monitoring_service.backend.models;

import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.backend.security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;

/**
 * Entity for storing user information
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Account {
    private Long id;
    private String login;
    private String password;
    private PlaceLivingEmbedded placeLiving;
    private Role role;
    private boolean isBlocked;

    public Account(Long id) {
        this.id = id;
    }
}