package com.munsun.utils.logger.repositories.impl;

import com.munsun.utils.logger.model.Log;
import com.munsun.utils.logger.repositories.LogsRepository;

import java.util.ArrayList;
import java.util.List;

public class LogsRepositoryImpl implements LogsRepository {
    private List<Log> cacheLogs;
    private static Long counterId = 0L;

    public LogsRepositoryImpl() {
        this.cacheLogs = new ArrayList<>();
    }

    @Override
    public Log save(Log log) {
        log.setId(counterId++);
        cacheLogs.add(log);
        return log;
    }

    @Override
    public List<Log> getAllLogs() {
        return cacheLogs;
    }
}
