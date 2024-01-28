package com.munsun.utils.logger.service;

import com.munsun.utils.logger.model.Log;

import java.util.List;

public interface LoggerService {
    void debug(String message);
    void info(String message);
    void warning(String message);
    void error(String message);
    List<Log> getAllLogs();
}
