package com.munsun.utils.logger.tests.repositories;

import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.repositories.JournalRepository;
import com.munsun.utils.logger.repositories.impl.JournalRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class JournalRepositoryUnitTests {
    private JournalRepository repository = new JournalRepositoryImpl();

    @DisplayName("Positive test get all journals")
    @Test
    public void testGetAllJournals() {
        String expectedMessage = "test";
        int expectedCountJournals = 3;

        repository.save(new JournalRecord(new Date(), expectedMessage));
        repository.save(new JournalRecord(new Date(), expectedMessage));
        repository.save(new JournalRecord(new Date(), expectedMessage));
        var actual = repository.getAllJournalRecords();

        assertThat(actual)
                .allMatch(Objects::nonNull)
                .extracting(JournalRecord::getMessage)
                    .allMatch(message -> message.equals(expectedMessage))
                .hasSize(expectedCountJournals);
    }
}
