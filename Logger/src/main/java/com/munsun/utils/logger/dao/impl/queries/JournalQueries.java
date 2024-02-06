package com.munsun.utils.logger.dao.impl.queries;

public interface JournalQueries {
    enum SAVE_JOURNAL implements JournalQueries {
        QUERY("""
            insert into monitoring_system.journals(date, message) values
            (?, ?);
            """);
        public enum Arguments implements JournalQueries {
            DATE(1),
            MESSAGE(2);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        SAVE_JOURNAL(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum GET_ALL_JOURNALS implements JournalQueries {
        QUERY("""
            select *
            from monitoring_system.journals;
            """);
        String description;

        GET_ALL_JOURNALS(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
