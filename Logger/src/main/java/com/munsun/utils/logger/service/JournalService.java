package com.munsun.utils.logger.service;

import com.munsun.monitoring_service.commons.exceptions.DatabaseException;
import com.munsun.utils.logger.model.JournalRecord;

import java.util.List;

/**
 * An abstraction for auditing user actions
 *
 * @author apple
 * @version $Id: $Id
 */
public interface JournalService {
    /**
     * Record a user action
     *
     * @param journalRecord dto object {@link JournalRecord}
     * @return saved DTO object JournalRecord.java
     */
    JournalRecord journal(JournalRecord journalRecord);

    /**
     * Get all jornals from database
     *
     * @return list of journals
     */
    List<JournalRecord> getJournalRecords();
}
