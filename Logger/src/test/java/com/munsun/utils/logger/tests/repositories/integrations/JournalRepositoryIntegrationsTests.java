package com.munsun.utils.logger.tests.repositories.integrations;

import com.munsun.monitoring_service.commons.db.Database;
import com.munsun.monitoring_service.commons.db.impl.DatabaseImpl;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.repositories.JournalRepository;
import com.munsun.utils.logger.repositories.impl.JournalRepositoryImpl;
import com.munsun.utils.logger.repositories.impl.mapping.impl.JdbcJournalMapperImpl;
import com.munsun.utils.logger.tests.repositories.PostgresContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class JournalRepositoryIntegrationsTests extends PostgresContainer {
    private Database database = new DatabaseImpl(getProperties());
    private JournalRepository journalRepository =
            new JournalRepositoryImpl(database, new JdbcJournalMapperImpl());

    private JournalRecord testJournal = JournalRecord.builder()
                                            .id(1L)
                                            .date(new Date(new java.util.Date().getTime()))
                                            .message("test message")
                                        .build();

    @DisplayName("success save journal in database")
    @Test
    public void positiveTestSave() {
        var actual = journalRepository.save(testJournal);

        assertThat(actual)
                .extracting(JournalRecord::getId, JournalRecord::getMessage)
                .containsExactly(testJournal.getId(), testJournal.getMessage());
    }

    @DisplayName("get all saved journals")
    @Test
    public void positiveGetAll() {
        journalRepository.save(testJournal);
        journalRepository.save(testJournal);
        journalRepository.save(testJournal);
        int expectedSize = 3;

        var actual = journalRepository.getAllJournalRecords();

        assertThat(actual)
                .hasSize(expectedSize)
                .allMatch(Objects::nonNull);
    }
}