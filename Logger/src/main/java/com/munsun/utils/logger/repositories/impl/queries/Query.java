package com.munsun.utils.logger.repositories.impl.queries;

public class Query {
    public static final String SAVE_JOURNAL = """
            insert into monitoring_system.journals(date, message) values
            (?, ?);
            """;
    public static final String GET_ALL_JOURNALS = """
            select *
            from monitoring_system.journals;
            """;
}