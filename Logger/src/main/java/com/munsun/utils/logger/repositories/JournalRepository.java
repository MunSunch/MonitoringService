package com.munsun.utils.logger.repositories;

import com.munsun.utils.logger.model.JournalRecord;

import java.util.List;

/**
 * Abstraction for creating a magazine
 */
public interface JournalRepository extends CrudRepository<JournalRecord, Long>{
    /**
     * Get a list of logs from the database
     * @return list of DTO object JournalRecord.java
     */
    List<JournalRecord> getAllJournalRecords();
}