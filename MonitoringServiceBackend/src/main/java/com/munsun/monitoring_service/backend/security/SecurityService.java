package com.munsun.monitoring_service.backend.security;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;

/**
 * <p>SecurityService interface.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
public interface SecurityService {
    /**
     * <p>authenticate.</p>
     *
     * @param loginPassword a {@link com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn} object
     * @throws com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException if any.
     * @throws com.munsun.monitoring_service.backend.exceptions.AuthenticationException if any.
     */
    void authenticate(LoginPasswordDtoIn loginPassword) throws AccountNotFoundException, AuthenticationException;
    /**
     * <p>register.</p>
     *
     * @param accountDtoIn a {@link com.munsun.monitoring_service.commons.dto.in.AccountDtoIn} object
     * @return a {@link com.munsun.monitoring_service.commons.dto.out.AccountDtoOut} object
     * @throws com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException if any.
     */
    AccountDtoOut register(AccountDtoIn accountDtoIn) throws DatabaseConstraintException;
    /**
     * <p>logout.</p>
     */
    void logout();
    /**
     * <p>getAccess.</p>
     *
     * @param role a {@link com.munsun.monitoring_service.backend.security.enums.Role} object
     * @param item a {@link com.munsun.monitoring_service.commons.enums.ItemsMainMenu} object
     * @return a boolean
     */
    boolean getAccess(Role role, ItemsMainMenu item);
    /**
     * <p>getSecurityContext.</p>
     *
     * @return a {@link com.munsun.monitoring_service.backend.security.SecurityContext} object
     */
    SecurityContext getSecurityContext();
}
