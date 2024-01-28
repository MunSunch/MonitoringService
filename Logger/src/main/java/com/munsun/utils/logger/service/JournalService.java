package com.munsun.utils.logger.service;

import com.munsun.utils.logger.model.JournalRecord;

import java.util.List;

/**
 * An abstraction for auditing user actions
 */
public interface JournalService {
    /**
     * Record a user action
     * @param message the action recorded by the string
     * @return saved DTO object JournalRecord.java
     */
    JournalRecord journal(String message);

    /**
     * Get all jornals from database
     * @return list of journals
     */
    List<JournalRecord> getJournalRecords();
}
