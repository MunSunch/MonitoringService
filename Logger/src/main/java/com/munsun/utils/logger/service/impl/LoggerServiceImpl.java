package com.munsun.utils.logger.service.impl;

import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.dao.JournalDao;
import com.munsun.utils.logger.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Monitors the user's actions
 *
 * @author apple
 * @version $Id: $Id
 */
@Service
@RequiredArgsConstructor
public class LoggerServiceImpl implements JournalService {
    private final JournalDao journalRepository;

    /**
     * {@inheritDoc}
     *
     * Record a user action
     */
    @Override
    public JournalRecord journal(JournalRecord journalRecord) {
        return journalRepository.save(journalRecord);
    }

    /** {@inheritDoc} */
    @Override
    public List<JournalRecord> getJournalRecords() {
        return journalRepository.getAllJournalRecords();
    }
}
