package com.munsun.utils.logger.service;

import com.munsun.utils.logger.model.Log;

import java.util.List;

/**
 * <p>LoggerService interface.</p>
 *
 * @author apple
 * @version $Id: $Id
 */
public interface LoggerService {
    /**
     * <p>debug.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    void debug(String message);
    /**
     * <p>info.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    void info(String message);
    /**
     * <p>warning.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    void warning(String message);
    /**
     * <p>error.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    void error(String message);
    /**
     * <p>getAllLogs.</p>
     *
     * @return a {@link java.util.List} object
     */
    List<Log> getAllLogs();
}
