package com.munsun.utils.logger.repositories.impl;

import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.repositories.JournalRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for storing and outputting Log entities from the database. The concept of a synthetic key is supported
 *
 * @author apple
 * @version $Id: $Id
 */
public class JournalRepositoryImpl implements JournalRepository {
    private final List<JournalRecord> cacheJournalRecords = new ArrayList<>();
    private Long counterId = 0L;

    /**
     * {@inheritDoc}
     *
     * Get a list of logs from the database
     */
    @Override
    public List<JournalRecord> getAllJournalRecords() {
        return cacheJournalRecords;
    }

    /**
     * {@inheritDoc}
     *
     * Save the new log to the database and return it from the database
     */
    @Override
    public JournalRecord save(JournalRecord journalRecord) {
        journalRecord.setId(counterId++);
        cacheJournalRecords.add(journalRecord);
        return journalRecord;
    }
}
