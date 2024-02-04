package com.munsun.monitoring_service.backend.tests.security;

import com.munsun.monitoring_service.backend.security.SecurityContext;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.security.impl.SecurityContextImpl;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;


public class SecurityContextUnitTests {
    private SecurityContext securityContext = new SecurityContextImpl();

    @DisplayName("Successful authorization of a user with USER rights")
    @ParameterizedTest
    @EnumSource(
            value = ItemsMainMenu.class,
            names = {"GET_ACTUAL_METER_READINGS",
                     "RECORD_METER_READINGS",
                     "GET_SHOW_HISTORY_MONTH",
                     "GET_SHOW_HISTORY",
                     "EXIT"}
    )
    public void positiveTestAllowAccessByUserRole(ItemsMainMenu item) {
        var actualAccess = securityContext.isAccessAllowed(Role.USER, item);

        assertThat(actualAccess).isTrue();
    }

    @DisplayName("Failed authorization of a user with USER rights")
    @ParameterizedTest
    @EnumSource(
            value = ItemsMainMenu.class,
            names = {"GET_ACTUAL_METER_READINGS_ALL_USERS",
                    "GET_SHOW_HISTORY_ALL_USERS",
                    "GET_SHOW_HISTORY_MONTH_ALL_USERS",
                    "GET_LOGS",
                    "ADD_METER_READING"}
    )
    public void negativeTestAllowAccessByUserRole(ItemsMainMenu item) {
        var actualAccess = securityContext.isAccessAllowed(Role.USER, item);

        assertThat(actualAccess).isFalse();
    }

    @DisplayName("Successful authorization of a user with ADMIN rights")
    @ParameterizedTest
    @EnumSource(
            value = ItemsMainMenu.class,
            names = {"GET_ACTUAL_METER_READINGS_ALL_USERS",
                    "GET_SHOW_HISTORY_ALL_USERS",
                    "GET_SHOW_HISTORY_MONTH_ALL_USERS",
                    "GET_LOGS",
                    "ADD_METER_READING",
                    "EXIT"}
    )
    public void positiveTestAllowAccessByAdminRole(ItemsMainMenu item) {
        var actualAccess = securityContext.isAccessAllowed(Role.ADMIN, item);

        assertThat(actualAccess).isTrue();
    }

    @DisplayName("Failed authorization of a user with ADMIN rights")
    @ParameterizedTest
    @EnumSource(
            value = ItemsMainMenu.class,
            names = {"GET_ACTUAL_METER_READINGS",
                    "RECORD_METER_READINGS",
                    "GET_SHOW_HISTORY_MONTH",
                    "GET_SHOW_HISTORY"}
    )
    public void negativeTestAllowAccessByAdminRole(ItemsMainMenu item) {
        var actualAccess = securityContext.isAccessAllowed(Role.ADMIN, item);

        assertThat(actualAccess).isFalse();
    }
}