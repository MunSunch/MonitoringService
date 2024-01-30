package com.munsun.utils.logger.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The essence for storing the service information of user actions is more necessary for specialists who are not related to software development
 */
@RequiredArgsConstructor
@Getter @Setter
public class JournalRecord {
    private Long id;
    private Date date;
    private String message;

    public JournalRecord(Date date, String message) {
        this.date = date;
        this.message = message;
    }
}
