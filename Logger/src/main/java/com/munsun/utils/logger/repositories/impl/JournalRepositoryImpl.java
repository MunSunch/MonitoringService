package com.munsun.utils.logger.repositories.impl;

import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.repositories.JournalRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for storing and outputting Log entities from the database. The concept of a synthetic key is supported
 */
public class JournalRepositoryImpl implements JournalRepository {
    private List<JournalRecord> cacheJournalRecords;
    private static Long counterId = 0L;

    public JournalRepositoryImpl() {
        this.cacheJournalRecords = new ArrayList<>();
    }

    /**
     * Get a list of logs from the database
     * @return list of DTO object JournalRecord.java
     */
    @Override
    public List<JournalRecord> getAllJournalRecords() {
        return cacheJournalRecords;
    }

    /**
     * Save the new log to the database and return it from the database
     * @param journalRecord DTO object JournalRecord.java
     * @return DTO object JournalRecord.java
     */
    @Override
    public JournalRecord save(JournalRecord journalRecord) {
        journalRecord.setId(counterId++);
        cacheJournalRecords.add(journalRecord);
        return journalRecord;
    }
}
