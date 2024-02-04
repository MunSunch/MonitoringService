package com.munsun.monitoring_service.backend.repositories;

import com.munsun.monitoring_service.backend.models.Account;

import java.util.Optional;

/**
 * Interface for storing and manipulating an entity Account
 *
 * @author apple
 * @version $Id: $Id
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
