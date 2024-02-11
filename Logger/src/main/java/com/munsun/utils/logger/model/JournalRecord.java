package com.munsun.utils.logger.model;

import lombok.*;

import java.sql.Date;


/**
 * The essence for storing the service information of user actions is more necessary for specialists who are not related to software development
 *
 * @author apple
 * @version $Id: $Id
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class JournalRecord {
    private Long id;
    private Date date;
    private String message;

    /**
     * <p>Constructor for JournalRecord.</p>
     *
     * @param date a {@link java.util.Date} object
     * @param message a {@link java.lang.String} object
     */
    public JournalRecord(Date date, String message) {
        this.date = date;
        this.message = message;
    }
}
