package com.munsun.utils.logger.service.impl;

import com.munsun.utils.logger.enums.LevelLog;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.model.Log;
import com.munsun.utils.logger.repositories.JournalRepository;
import com.munsun.utils.logger.repositories.LogsRepository;
import com.munsun.utils.logger.service.JournalService;
import com.munsun.utils.logger.service.LoggerService;

import java.util.Date;
import java.util.List;

/**
 * Monitors the user's actions
 */
public class LoggerServiceImpl implements LoggerService, JournalService {
    private String nameClassOwner;
    private LogsRepository logsRepository;
    private JournalRepository journalRepository;

    public <T> LoggerServiceImpl(Class<T> tClass, LogsRepository logsRepository) {
        this.nameClassOwner = tClass.getName();
        this.logsRepository = logsRepository;
    }

    public LoggerServiceImpl(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Override
    public List<JournalRecord> getJournalRecords() {
        return journalRepository.getAllJournalRecords();
    }

    /**
     * Record a user action
     * @param message the action recorded by the string
     * @return saved DTO object JournalRecord.java
     */
    @Override
    public JournalRecord journal(String message) {
        return journalRepository.save(new JournalRecord(new Date(), message));
    }

    @Override
    public void debug(String message) {
        logsRepository.save(fabricMethod(message, LevelLog.DEBUG));
    }

    private Log fabricMethod(String message, LevelLog levelLog) {
        return new Log(new Date(), levelLog, nameClassOwner, message);
    }

    @Override
    public void info(String message) {
        logsRepository.save(fabricMethod(message, LevelLog.INFO));
    }

    @Override
    public void warning(String message) {
        logsRepository.save(fabricMethod(message, LevelLog.WARN));
    }

    @Override
    public void error(String message) {
        logsRepository.save(fabricMethod(message, LevelLog.ERROR));
    }

    /**
     * Get all jornals from database
     * @return list of journals
     */
    @Override
    public List<Log> getAllLogs() {
        return logsRepository.getAllLogs();
    }
}
