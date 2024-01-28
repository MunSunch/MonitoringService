package com.munsun.monitoring_service.backend.repositories.impl;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.repositories.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {
    private Map<String, Account> accountsIndexByLogin;
    private Map<Long, Account> accountsIndexById;
    private static Long COUNTER_ID = 0L;

    public AccountRepositoryImpl() {
        this.accountsIndexByLogin = new HashMap<>();
        this.accountsIndexById = new HashMap<>();
    }

    @Override
    public Optional<Account> findByAccount_Login(String login) {
        return Optional.ofNullable(accountsIndexByLogin.get(login));
    }

    @Override
    public Optional<Account> getById(Long key) {
        return Optional.ofNullable(accountsIndexById.get(key));
    }

    @Override
    public Account save(Account account) throws DatabaseConstraintException {
        account.setId(COUNTER_ID++);
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
