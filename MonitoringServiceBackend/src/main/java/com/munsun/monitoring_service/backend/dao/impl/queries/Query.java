package com.munsun.monitoring_service.backend.dao.impl.queries;

public class Query {
    public static final String FIND_ACCOUNT_BY_LOGIN = """
            select *
            from monitoring_system.accounts as A
            where A.login = ?
            """;
    public static final String SAVE_ACCOUNT = """
            insert into monitoring_system.accounts(login,password,role,is_blocked,country,city,street,house,level,apartmentnumber) values
            (?,?,?,?,?,?,?,?,?,?);
            """;
    public static final String GET_ACCOUNT_BY_ID = """
            select *
            from monitoring_system.accounts as A
            where A.id = ?;
            """;
    public static final String DELETE_BY_ID = """
            delete from monitoring_system.accounts as A
            where A.id = ?;
            """;
    public static final String SAVE_METER_READING = """
            insert into monitoring_system.meter_readings(date, account_id) values
            (?, ?);
            """;
    public static final String SAVE_READING = """
            insert into monitoring_system.readings(meter_readings_id, name, value) values
            (?, ?, ?);
            """;
    public static final String GET_METER_READINGS_BY_ACCOUNT_ID = """
            select *
            from monitoring_system.meter_readings as M join monitoring_system.readings as R on M.id=R.meter_readings_id
                                                       join monitoring_system.accounts as A on A.id=M.account_id
            where A.id=?;
            """;
    public static final String GET_LAST_SAVED_METER_READINGS = """
            select *
            from monitoring_system.meter_readings
            order by id desc
                  limit 1;
            """;
    public static final String GET_METER_READING_BY_ID = """
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id=M.account_id
                                                       join monitoring_system.readings as R on M.id=R.meter_readings_id
            where M.id = ?;
            """;
    public static final String DELETE_READING_BY_ID = """
            delete from monitoring_system.readings
            where meter_readings_id = ?;
            """;
    public static final String DELETE_METER_READING_BY_ID = """
            delete from monitoring_system.meter_readings
            where id = ?;
            """;
    public static final String GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH = """
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id = M.account_id
                                                       join monitoring_system.readings as R on R.meter_readings_id=M.id
            where date_part('month', M.date) = ? and A.id = ?;
            """;
    public static final String GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH = """
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id = M.account_id
                                                       join monitoring_system.readings as R on R.meter_readings_id=M.id
            where date_part('month', M.date) = ?;
            """;
    public static final String GET_ALL_METER_READINGS_ALL_ACCOUNTS = """
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id = M.account_id
                                                       join monitoring_system.readings as R on R.meter_readings_id=M.id;
            """;
    public static final String GET_LAST_SAVED_ACCOUNT = """
            select *
            from monitoring_system.accounts as A join monitoring_system.meter_readings as M on A.id=M.account_id
                                                       join monitoring_system.readings as R on M.id=R.meter_readings_id
            order by A.id desc
                  limit 1;
            """;
}