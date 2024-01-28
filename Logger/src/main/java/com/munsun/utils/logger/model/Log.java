package com.munsun.utils.logger.model;

import com.munsun.utils.logger.enums.LevelLog;

import java.util.Date;

/**
 * An entity for storing service information of user actions that is more necessary for software developers
 */
public class Log {
    private Long id;
    private Date date;
    private LevelLog level;
    private String nameClass;
    private String message;

    public Log(Date date, LevelLog level, String nameClass, String message) {
        this.date = date;
        this.level = level;
        this.nameClass = nameClass;
        this.message = message;
    }

    public Log() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LevelLog getLevel() {
        return level;
    }

    public void setLevel(LevelLog level) {
        this.level = level;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
