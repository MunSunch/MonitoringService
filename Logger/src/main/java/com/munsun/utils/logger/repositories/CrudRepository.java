package com.munsun.utils.logger.repositories;

/**
 * An interface for performing four basic data manipulations:
 * reading, writing, updating, and deleting. In this implementation, some of the operations are omitted as unnecessary
 * @param <VALUE>
 * @param <KEY>
 */
public interface CrudRepository<VALUE, KEY> {
    /**
     * Saving an entity in the database. It is necessary to set the primary key,
     * which, during the saving of the DBMS, should be changed to a more relevant
     * one for yourself
     * @param value
     * @return saved VALUE object with changed DBMS fields
     */
    VALUE save(VALUE value);
}
