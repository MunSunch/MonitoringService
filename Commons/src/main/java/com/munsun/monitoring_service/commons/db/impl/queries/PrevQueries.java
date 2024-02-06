package com.munsun.monitoring_service.commons.db.impl.queries;

public interface PrevQueries {
    enum CREATE_NEW_SCHEMA implements PrevQueries {
        QUERY("create schema %s;");
        public enum Arguments implements PrevQueries {
            NAME_SCHEMA(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        CREATE_NEW_SCHEMA(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    enum IS_EXISTS_SCHEMA implements PrevQueries {
        QUERY("""
            SELECT schema_name
            FROM information_schema.schemata
            WHERE schema_name = ?;
            """);

        public enum Arguments implements PrevQueries {
            NAME_SCHEMA(1);

            int position;

            Arguments(int positionInQuery) {
                this.position = positionInQuery;
            }

            public int getPositionInQuery() {
                return position;
            }
        }

        String description;

        IS_EXISTS_SCHEMA(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
