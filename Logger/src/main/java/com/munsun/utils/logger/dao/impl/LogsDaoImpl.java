package com.munsun.utils.logger.dao.impl;

import com.munsun.utils.logger.model.Log;
import com.munsun.utils.logger.dao.LogsDao;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>LogsRepositoryImpl class.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
public class LogsDaoImpl implements LogsDao {
    private final List<Log> cacheLogs = new ArrayList<>();
    private Long counterId = 0L;

    /** {@inheritDoc} */
    @Override
    public Log save(Log log) {
        log.setId(counterId++);
        cacheLogs.add(log);
        return log;
    }

    /** {@inheritDoc} */
    @Override
    public List<Log> getAllLogs() {
        return cacheLogs;
    }
}
