package com.munsun.utils.logger.dao;

import com.munsun.monitoring_service.commons.exceptions.DatabaseException;
import com.munsun.utils.logger.model.JournalRecord;

import java.util.List;

/**
 * Abstraction for creating a magazine
 *
 * @author apple
 * @version $Id: $Id
 */
public interface JournalDao extends CrudDao<JournalRecord, Long> {
    /**
     * Get a list of logs from the database
     *
     * @return list of DTO object JournalRecord.java
     */
    List<JournalRecord> getAllJournalRecords();
}
