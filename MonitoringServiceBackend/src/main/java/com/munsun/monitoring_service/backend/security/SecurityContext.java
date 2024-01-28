package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.backend.security.enums.Role;

public interface SecurityContext {
    void clear();
    boolean allowAccess(Role role, ItemsMainMenu item);
    void setCurrentAuthorizedAccount(Account account);
    Account getAuthorizedAccount();
}
