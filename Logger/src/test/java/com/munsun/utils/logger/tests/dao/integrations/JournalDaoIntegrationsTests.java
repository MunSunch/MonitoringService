package com.munsun.utils.logger.tests.dao.integrations;

import com.munsun.monitoring_service.commons.exceptions.DatabaseException;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.dao.JournalDao;
import com.munsun.utils.logger.dao.impl.JournalDaoImpl;
import com.munsun.utils.logger.dao.impl.mapping.impl.JdbcJournalMapperImpl;
import com.munsun.utils.logger.tests.dao.PostgresContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class JournalDaoIntegrationsTests extends PostgresContainer {
    private JournalDao journalRepository;
    private JournalRecord testJournal = JournalRecord.builder()
                                            .id(1L)
                                            .date(new Date(new java.util.Date().getTime()))
                                            .message("test message")
                                        .build();

    @DisplayName("success save journal in database")
    @Test
    public void positiveTestSave() throws DatabaseException {
        var actual = journalRepository.save(testJournal);

        assertThat(actual)
                .extracting(JournalRecord::getId, JournalRecord::getMessage)
                .containsExactly(testJournal.getId(), testJournal.getMessage());
    }

    @DisplayName("get all saved journals")
    @Test
    public void positiveGetAll() throws DatabaseException {
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