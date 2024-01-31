package com.munsun.monitoring_service.commons.db.queries;

public class Query {
    public static String getQueryCreateNewSchema(String nameNewSchema) {
        return String.format("create schema %s;", nameNewSchema);
    }

    public static String isExistsSchemaInDatabase(String nameSchema) {
        return String.format("""
                    SELECT schema_name
                    FROM information_schema.schemata
                    WHERE schema_name = '%s'");
                    """, nameSchema);
    }
}
