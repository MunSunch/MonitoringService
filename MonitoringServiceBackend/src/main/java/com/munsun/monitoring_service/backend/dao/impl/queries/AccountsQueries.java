package com.munsun.monitoring_service.backend.dao.impl.queries;

public interface AccountsQueries {
    enum FIND_ACCOUNT_BY_LOGIN implements AccountsQueries {
        QUERY("""
            select *
            from monitoring_system.accounts as A
            where A.login = ?
            """);
        public enum Arguments implements AccountsQueries {
            LOGIN(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        FIND_ACCOUNT_BY_LOGIN(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum SAVE_ACCOUNT implements AccountsQueries {
        QUERY("""
            insert into monitoring_system.accounts(login,password,role,is_blocked,country,city,street,house,level,apartmentnumber) values
            (?,?,?,?,?,?,?,?,?,?);
            """);
        public enum Arguments implements AccountsQueries {
            LOGIN(1),
            PASSWORD(2),
            ROLE(3),
            IS_BLOCKED(4),
            COUNTRY(5),
            CITY(6),
            STREET(7),
            HOUSE(8),
            LEVEL(9),
            APARTMENT_NUMBER(10);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        SAVE_ACCOUNT(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_ACCOUNT_BY_ID implements AccountsQueries {
        QUERY("""
            select *
            from monitoring_system.accounts as A
            where A.id = ?;
            """);
        public enum Arguments implements AccountsQueries {
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

        GET_ACCOUNT_BY_ID(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum DELETE_BY_ID implements AccountsQueries {
        QUERY("""
            delete from monitoring_system.accounts as A
            where A.id = ?;
            """);
        public enum Arguments implements AccountsQueries {
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

        DELETE_BY_ID(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_LAST_SAVED_ACCOUNT implements AccountsQueries {
        QUERY("""
            select *
            from monitoring_system.accounts as A join monitoring_system.meter_readings as M on A.id=M.account_id
                                                       join monitoring_system.readings as R on M.id=R.meter_readings_id
            order by A.id desc
                  limit 1;
            """);

        String description;

        GET_LAST_SAVED_ACCOUNT(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}