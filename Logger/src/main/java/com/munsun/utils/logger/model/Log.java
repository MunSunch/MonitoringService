package com.munsun.utils.logger.model;

import com.munsun.utils.logger.enums.LevelLog;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * An entity for storing service information of user actions that is more necessary for software developers
 *
 * @author apple
 * @version $Id: $Id
 */
@Builder
@AllArgsConstructor
@Getter @Setter
public class Log {
    private Long id;
    private Date date;
    private LevelLog level;
    private String nameClass;
    private String message;
}
