package com.munsun.utils.logger.repositories;

/**
 * An interface for performing four basic data manipulations:
 * reading, writing, updating, and deleting. In this implementation, some of the operations are omitted as unnecessary
 *
 * @param <Value>
 * @param <Key>
 * @author apple
 * @version $Id: $Id
 */
public interface CrudRepository<Value, Key> {
    /**
     * Saving an entity in the database. It is necessary to set the primary key,
     * which, during the saving of the DBMS, should be changed to a more relevant
     * one for yourself
     *
     * @param value a Value object
     * @return saved VALUE object with changed DBMS fields
     */
    Value save(Value value);
}
