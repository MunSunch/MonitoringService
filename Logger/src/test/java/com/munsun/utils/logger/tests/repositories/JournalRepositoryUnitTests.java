package com.munsun.utils.logger.tests.repositories;

import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.repositories.JournalRepository;
import com.munsun.utils.logger.repositories.impl.JournalRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class JournalRepositoryUnitTests {
    private JournalRepository repository = new JournalRepositoryImpl();

    @Test
    public void testGetAllJournals() {
        repository.save(new JournalRecord(new Date(), "test"));
        repository.save(new JournalRecord(new Date(), "test"));
        repository.save(new JournalRecord(new Date(), "test"));

        var actual = repository.getAllJournalRecords();

        assertThat(actual)
                .allMatch(Objects::nonNull)
                .hasSize(3);
    }
}
