package com.munsun.utils.logger.service.impl;

import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.dao.JournalDao;
import com.munsun.utils.logger.service.JournalService;

import java.sql.Date;
import java.util.List;

/**
 * Monitors the user's actions
 *
 * @author apple
 * @version $Id: $Id
 */
public class LoggerServiceImpl implements JournalService {
    private String nameClassOwner;
    private JournalDao journalRepository;

    /**
     * <p>Constructor for LoggerServiceImpl.</p>
     *
     * @param tClass a {@link java.lang.Class} object
     * @param journalRepository a {@link JournalDao} object
     */
    public LoggerServiceImpl(Class<?> tClass, JournalDao journalRepository) {
        this.nameClassOwner = tClass.getName();
        this.journalRepository = journalRepository;
    }

    /**
     * {@inheritDoc}
     *
     * Record a user action
     */
    @Override
    public JournalRecord journal(JournalRecord journalRecord) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<JournalRecord> getJournalRecords() {
        return journalRepository.getAllJournalRecords();
    }
}
