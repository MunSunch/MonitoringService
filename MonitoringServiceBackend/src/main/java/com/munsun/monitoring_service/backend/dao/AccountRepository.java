package com.munsun.monitoring_service.backend.dao;

import com.munsun.monitoring_service.backend.models.Account;

import java.util.Optional;

/**
 * Interface for storing and manipulating an entity Account
 *
 * @author MunSun
 * @version 1.0-SNAPSHOT
 */
public interface AccountRepository extends CrudRepository<Account, Long>{
    /**
     * Return the Account entity by the specified login field
     *
     * @param login String
     * @return Optional<Account>
     */
    Optional<Account> findByAccount_Login(String login);
}
