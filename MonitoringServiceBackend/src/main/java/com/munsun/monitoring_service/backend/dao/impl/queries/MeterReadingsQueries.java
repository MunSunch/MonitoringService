package com.munsun.monitoring_service.backend.dao.impl.queries;

public interface MeterReadingsQueries {
    enum GET_METER_READING_BY_ID implements MeterReadingsQueries {
        QUERY("""
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id=M.account_id
                                                       join monitoring_system.readings as R on M.id=R.meter_readings_id
            where M.id = ?;
            """);
        public enum Arguments implements MeterReadingsQueries {
            ID(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        GET_METER_READING_BY_ID(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum SAVE_METER_READING implements MeterReadingsQueries {
        QUERY("""
            insert into monitoring_system.meter_readings(date, account_id) values
            (?, ?);
            """);
        public enum Arguments implements MeterReadingsQueries {
            DATE(1),
            ACCOUNT_ID(2);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        SAVE_METER_READING(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_LAST_SAVED_METER_READINGS implements MeterReadingsQueries {
        QUERY("""
            select *
            from monitoring_system.meter_readings
            order by id desc
                  limit 1;
            """);

        String description;

        GET_LAST_SAVED_METER_READINGS(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum SAVE_READING implements MeterReadingsQueries {
        QUERY("""
            insert into monitoring_system.readings(meter_readings_id, name, value) values
            (?, ?, ?);
            """);
        public enum Arguments implements MeterReadingsQueries {
            METER_READINGS_ID(1),
            NAME(2),
            VALUE(3);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        SAVE_READING(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH implements MeterReadingsQueries {
        QUERY("""
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id = M.account_id
                                                       join monitoring_system.readings as R on R.meter_readings_id=M.id
            where date_part('month', M.date) = ?;
            """);
        public enum Arguments implements MeterReadingsQueries {
            MONTH(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        GET_ALL_METER_READINGS_ALL_ACCOUNTS_BY_MONTH(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_ALL_METER_READINGS_ALL_ACCOUNTS implements MeterReadingsQueries {
        QUERY("""
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id = M.account_id
                                                       join monitoring_system.readings as R on R.meter_readings_id=M.id;
            """);

        String description;

        GET_ALL_METER_READINGS_ALL_ACCOUNTS(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum DELETE_READING_BY_ID implements MeterReadingsQueries {
        QUERY("""
            delete from monitoring_system.readings
            where meter_readings_id = ?;
            """);
        public enum Arguments implements MeterReadingsQueries {
            METER_READINGS_ID(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        DELETE_READING_BY_ID(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum DELETE_METER_READING_BY_ID implements MeterReadingsQueries {
        QUERY("""
            delete from monitoring_system.meter_readings
            where id = ?;
            """);
        public enum Arguments implements MeterReadingsQueries {
            ID(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        DELETE_METER_READING_BY_ID(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_METER_READINGS_BY_ACCOUNT_ID implements MeterReadingsQueries {
        QUERY("""
            select *
            from monitoring_system.meter_readings as M join monitoring_system.readings as R on M.id=R.meter_readings_id
                                                       join monitoring_system.accounts as A on A.id=M.account_id
            where A.id=?;
            """);
        public enum Arguments implements MeterReadingsQueries {
            ID(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        GET_METER_READINGS_BY_ACCOUNT_ID(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH implements MeterReadingsQueries {
        QUERY("""
            select *
            from monitoring_system.meter_readings as M join monitoring_system.accounts as A on A.id = M.account_id
                                                       join monitoring_system.readings as R on R.meter_readings_id=M.id
            where date_part('month', M.date) = ? and A.id = ?;
            """);
        public enum Arguments implements MeterReadingsQueries {
            MONTH(1),
            ID(2);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        GET_METER_READINGS_BY_ACCOUNT_ID_AND_MONTH(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
