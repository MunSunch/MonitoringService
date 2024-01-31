package com.munsun.monitoring_service.backend.repositories.impl;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.repositories.AccountRepository;
import com.munsun.monitoring_service.commons.db.Database;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>AccountRepositoryImpl class.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
public class AccountRepositoryImpl implements AccountRepository {
    private final Map<String, Account> accountsIndexByLogin = new HashMap<>();
    private final Map<Long, Account> accountsIndexById = new HashMap<>();
    private Long counterId = 0L;

    /** {@inheritDoc} */
    @Override
    public Optional<Account> findByAccount_Login(String login) {
        return Optional.ofNullable(accountsIndexByLogin.get(login));
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Account> getById(Long key) {
        return Optional.ofNullable(accountsIndexById.get(key));
    }

    /** {@inheritDoc} */
    @Override
    public Account save(Account account) throws DatabaseConstraintException {
        account.setId(counterId++);
        checkConstraints(account);
        accountsIndexByLogin.put(account.getLogin(), account);
        accountsIndexById.put(account.getId(), account);
        return account;
    }

    private void checkConstraints(Account account) throws DatabaseConstraintException {
        if(accountsIndexByLogin.containsKey(account.getLogin())) {
            throw new DatabaseConstraintException("Error constraint: unique 'login'");
        }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Account> deleteById(Long key) {
        var account = accountsIndexById.get(key);
        if(account == null) {
            return Optional.empty();
        }
        accountsIndexById.remove(key);
        accountsIndexByLogin.remove(account.getLogin());
        return Optional.ofNullable(account);
    }
}
