package com.munsun.utils.logger.repositories;

import com.munsun.utils.logger.model.Log;

import java.util.List;

/**
 * Abstraction for creating a log log
 *
 * @author apple
 * @version $Id: $Id
 */
public interface LogsRepository extends CrudRepository<Log, Long>{
    /**
     * Outputs a list from logs, and the size of this list is not defined
     *
     * @return list of logs
     */
    List<Log> getAllLogs();
}
