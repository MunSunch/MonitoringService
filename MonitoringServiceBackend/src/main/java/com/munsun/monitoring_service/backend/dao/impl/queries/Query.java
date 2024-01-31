package com.munsun.monitoring_service.backend.dao.impl.queries;

public class Query {
    public static final String QUERY_FIND_ACCOUNT_BY_LOGIN = """
            select *
            from monitoring_service.accounts as A
            where A.login = ?
            """;
}
