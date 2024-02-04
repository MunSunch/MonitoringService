package com.munsun.utils.logger.service.impl;

import com.munsun.utils.logger.enums.LevelLog;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.model.Log;
import com.munsun.utils.logger.repositories.JournalRepository;
import com.munsun.utils.logger.repositories.LogsRepository;
import com.munsun.utils.logger.service.JournalService;
import com.munsun.utils.logger.service.LoggerService;

import java.sql.Date;
import java.util.List;

/**
 * Monitors the user's actions
 *
 * @author apple
 * @version $Id: $Id
 */
public class LoggerServiceImpl implements LoggerService, JournalService {
    private String nameClassOwner;
    private LogsRepository logsRepository;
    private JournalRepository journalRepository;

    /**
     * <p>Constructor for LoggerServiceImpl.</p>
     *
     * @param tClass a {@link java.lang.Class} object
     * @param logsRepository a {@link com.munsun.utils.logger.repositories.LogsRepository} object
     */
    public LoggerServiceImpl(Class<?> tClass, LogsRepository logsRepository) {
        this.nameClassOwner = tClass.getName();
        this.logsRepository = logsRepository;
    }

    /**
     * <p>Constructor for LoggerServiceImpl.</p>
     *
     * @param tClass a {@link java.lang.Class} object
     * @param journalRepository a {@link com.munsun.utils.logger.repositories.JournalRepository} object
     */
    public LoggerServiceImpl(Class<?> tClass, JournalRepository journalRepository) {
        this.nameClassOwner = tClass.getName();
        this.journalRepository = journalRepository;
    }

    /** {@inheritDoc} */
    @Override
    public List<JournalRecord> getJournalRecords() {
        return journalRepository.getAllJournalRecords();
    }

    /**
     * {@inheritDoc}
     *
     * Record a user action
     */
    @Override
    public JournalRecord journal(String message) {
        return journalRepository.save(new JournalRecord(new Date(new java.util.Date().getTime()), message));
    }

    /** {@inheritDoc} */
    @Override
    public void debug(String message) {
        logsRepository.save(Log.builder()
                .message(message)
                .level(LevelLog.DEBUG)
                .build());
    }

    /** {@inheritDoc} */
    @Override
    public void info(String message) {
        logsRepository.save(Log.builder()
                .message(message)
                .level(LevelLog.INFO)
                .build());
    }

    /** {@inheritDoc} */
    @Override
    public void warning(String message) {
        logsRepository.save(Log.builder()
                .message(message)
                .level(LevelLog.WARN)
                .build());
    }

    /** {@inheritDoc} */
    @Override
    public void error(String message) {
        logsRepository.save(Log.builder()
                .message(message)
                .level(LevelLog.ERROR)
                .build());
    }

    /**
     * {@inheritDoc}
     *
     * Get all jornals from database
     */
    @Override
    public List<Log> getAllLogs() {
        return logsRepository.getAllLogs();
    }
}
