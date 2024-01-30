package com.munsun.monitoring_service.backend.repositories;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import java.util.Optional;

/**
 * An abstraction that contains the main CRUD operations,
 * some of them are omitted as unnecessary
 * @param <Value> entity
 * @param <Key> primary key
 */
public interface CrudRepository<Value, Key> {
    /**
     * Return an entity wrapped in Optional with the primary key value from the database.
     * @param key primary key
     * @return Optional<VALUE>
     */
    Optional<Value> getById(Key key);

    /**
     * Save the entity in the database and assign it a primary key in accordance
     * with the rules established by the DBMS
     * @param value entity
     * @return VALUE entity and the modified primary key
     * @throws DatabaseConstraintException it is thrown out when the restrictions set by the DBMS are violated
     */
    Value save(Value value) throws DatabaseConstraintException;
    Optional<Value> deleteById(Key key);
}
