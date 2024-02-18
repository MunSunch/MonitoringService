package com.munsun.monitoring_service.backend.tests.dao.integrations;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.dao.impl.AccountDaoImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcAccountMapper;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcAccountMapperImpl;
import com.munsun.monitoring_service.backend.dao.impl.mapping.impl.JdbcPlaceLivingMapperImpl;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.backend.security.enums.Role;
import com.munsun.monitoring_service.backend.tests.dao.PostgresContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountsDaoIntegrationsTests extends PostgresContainer {
    private JdbcAccountMapper jdbcAccountMapper;
    private AccountDao accountRepository;

    private Account testAccount = Account.builder()
            .login("user")
            .password("user")
            .role(Role.USER)
            .placeLiving(PlaceLivingEmbedded.builder()
                    .country("Russia")
                    .city("Moscow")
                    .street("Red")
                    .house("silver")
                    .level("8")
                    .apartmentNumber("12/22")
                    .build())
            .build();

    @DisplayName("save not exists account")
    @Test
    public void positiveTestSaveNotExistsAccount() throws SQLException {
        var expectedCountSavedAccounts = 1;

        var actual = accountRepository.save(testAccount);

        assertThat(actual)
                .isEqualTo(expectedCountSavedAccounts);
    }

    @DisplayName("Saving an account with an existing login")
    @Test
    public void negativeSaveExistsAccount() throws SQLException {
        var expectedException = PSQLException.class;
        var expectedExceptionMessage = "duplicate key value violates unique constraint";

        accountRepository.save(testAccount);

        assertThatThrownBy(() -> accountRepository.save(testAccount))
                .isInstanceOf(expectedException)
                .hasMessageContaining(expectedExceptionMessage);
    }

    @DisplayName("Search for an existing account by login")
    @Test
    public void positiveFindByLoginExistsAccount() throws SQLException {
        accountRepository.save(testAccount);
        var actual = accountRepository.findByAccount_Login(testAccount.getLogin());

        assertThat(actual)
                .isPresent().get()
                .extracting(Account::getId,
                            Account::getLogin,
                            Account::getPassword,
                            Account::getRole)
                    .containsExactly(1L,
                            testAccount.getLogin(),
                            testAccount.getPassword(),
                            testAccount.getRole());
    }

    @DisplayName("Search for a non-existent account by login")
    @Test
    public void negativeFindByLoginNotExistsAccount() throws SQLException {
        var actual = accountRepository.findByAccount_Login(testAccount.getLogin());

        assertThat(actual)
                .isEmpty();
    }

    @DisplayName("Getting an existing account by id")
    @Test
    public void positiveGetByIdAccount() throws SQLException {
        var expectedAccountID = 1L;

        accountRepository.save(testAccount);
        var actual = accountRepository.getById(expectedAccountID);

        assertThat(actual)
                .isPresent().get()
                .extracting(Account::getId)
                    .isEqualTo(expectedAccountID);
    }

    @DisplayName("Getting a non-existent account by id")
    @Test
    public void negativeGetByIdAccount() throws SQLException {
        var actual = accountRepository.getById(1L);

        assertThat(actual)
                .isEmpty();
    }

    @DisplayName("Deleting an account with an existing id")
    @Test
    public void positiveDeleteByIdAccount() throws SQLException {
        var expectedCountDeletedRow = 1;

        accountRepository.save(testAccount);
        var actual = accountRepository.deleteById(1L);

        assertThat(actual).isEqualTo(expectedCountDeletedRow);
    }
}