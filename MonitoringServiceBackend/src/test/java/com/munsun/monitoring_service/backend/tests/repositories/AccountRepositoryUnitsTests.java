package com.munsun.monitoring_service.backend.tests.repositories;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.repositories.AccountRepository;
import com.munsun.monitoring_service.backend.repositories.impl.AccountRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountRepositoryUnitsTests {
    private AccountRepository repository = new AccountRepositoryImpl();

    @DisplayName("Successful search for an existing account by login")
    @Test
    public void testPositiveFindByExistsAccount() throws DatabaseConstraintException {
        var account1 = new Account();
            account1.setLogin("testLogin1");
        var account2 = new Account();
            account2.setLogin("testLogin2");
        var account3 = new Account();
            account3.setLogin("testLogin3");

        repository.save(account1);
        repository.save(account2);
        repository.save(account3);
        var actual = repository.findByAccount_Login("testLogin2");

        assertThat(actual)
                .isPresent()
                .get().extracting(Account::getLogin).isEqualTo("testLogin2");
    }

    @DisplayName("Unsuccessful search for a non-existent account by login")
    @Test
    public void testNegativeFindByNotExistsAccount() throws DatabaseConstraintException {
        var actual = repository.findByAccount_Login("testLogin2");

        assertThat(actual)
                .isEmpty();
    }

    @DisplayName("Successfully saving a new account without throwing an error")
    @Test
    public void testSaveNewExistsAccount() throws DatabaseConstraintException {
        var account = new Account();
            account.setLogin("testLogin1");

        repository.save(account);

        assertThatThrownBy(() -> {
            repository.save(account);
        }).isInstanceOf(DatabaseConstraintException.class);
    }
}