package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.backend.security.enums.Role;

/**
 * <p>SecurityContext interface.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
public interface SecurityContext {
    /**
     * <p>clear.</p>
     */
    void clear();
    /**
     * <p>isAccessAllowed.</p>
     *
     * @param role a {@link com.munsun.monitoring_service.backend.security.enums.Role} object
     * @param item a {@link com.munsun.monitoring_service.commons.enums.ItemsMainMenu} object
     * @return a boolean
     */
    boolean isAccessAllowed(Role role, ItemsMainMenu item);
    /**
     * <p>setCurrentAuthorizedAccount.</p>
     *
     * @param account a {@link com.munsun.monitoring_service.backend.models.Account} object
     */
    void setCurrentAuthorizedAccount(Account account);
    /**
     * <p>getAuthorizedAccount.</p>
     *
     * @return a {@link com.munsun.monitoring_service.backend.models.Account} object
     */
    Account getAuthorizedAccount();
}
