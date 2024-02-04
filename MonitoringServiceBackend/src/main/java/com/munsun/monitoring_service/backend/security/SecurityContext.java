package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.backend.security.enums.Role;

/**
 * Abstraction for user authorization
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface SecurityContext {
    /**
     * Clear the storage with authenticated users
     */
    void clear();

    /**
     * Authorize a user with a {@link Role role} to access a specific resource {@link ItemsMainMenu}
     * @param role a {@link com.munsun.monitoring_service.backend.security.enums.Role} object
     * @param item a {@link com.munsun.monitoring_service.commons.enums.ItemsMainMenu} object
     * @return a boolean
     */
    boolean isAccessAllowed(Role role, ItemsMainMenu item);

    /**
     * Substitute a user
     * @param account a {@link com.munsun.monitoring_service.backend.models.Account} object
     */
    void setCurrentAuthorizedAccount(Account account);

    /**
     * Return the current authenticated user from the context
     * @return a {@link com.munsun.monitoring_service.backend.models.Account} object
     */
    Account getAuthorizedAccount();
}
