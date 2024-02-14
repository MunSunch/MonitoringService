package com.munsun.monitoring_service.backend.models;

import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.backend.security.enums.Role;
import lombok.*;

/**
 * Entity for storing user information
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
@RequiredArgsConstructor
@AllArgsConstructor
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
}
