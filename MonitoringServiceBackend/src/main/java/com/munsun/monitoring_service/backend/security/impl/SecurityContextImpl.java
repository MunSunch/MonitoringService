package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.SecurityContext;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.backend.security.enums.Role;

import java.util.Map;
import java.util.Set;

/**
 * <p>SecurityContextImpl class.</p>
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public class SecurityContextImpl implements SecurityContext {
    public Account currentAuthorizedAccount;
    private final Map<Role, Set<ItemsMainMenu>> context;

    /**
     * <p>Constructor for SecurityContextImpl.</p>
     */
    public SecurityContextImpl() {
        this.context = Map.of(Role.ADMIN, Set.of(ItemsMainMenu.GET_SHOW_HISTORY_ALL_USERS,
                                                 ItemsMainMenu.GET_SHOW_HISTORY_MONTH_ALL_USERS,
                                                 ItemsMainMenu.GET_ACTUAL_METER_READINGS_ALL_USERS,
                                                 ItemsMainMenu.GET_LOGS,
                                                 ItemsMainMenu.ADD_METER_READING,
                                                 ItemsMainMenu.EXIT),
                              Role.USER, Set.of(ItemsMainMenu.GET_ACTUAL_METER_READINGS,
                                                 ItemsMainMenu.GET_SHOW_HISTORY_MONTH,
                                                 ItemsMainMenu.GET_SHOW_HISTORY,
                                                 ItemsMainMenu.RECORD_METER_READINGS,
                                                 ItemsMainMenu.EXIT));
    }

    /**
     * <p>clear.</p>
     */
    public void clear() {
        currentAuthorizedAccount = null;
    }

    /** {@inheritDoc} */
    @Override
    public Account getAuthorizedAccount() {
        return currentAuthorizedAccount;
    }

    /** {@inheritDoc} */
    public void setCurrentAuthorizedAccount(Account currentAuthorizedAccount) {
        this.currentAuthorizedAccount = currentAuthorizedAccount;
    }

    /** {@inheritDoc} */
    public boolean isAccessAllowed(Role role, ItemsMainMenu item) {
        return context.get(role).stream().anyMatch(x -> x == item);
    }
}
