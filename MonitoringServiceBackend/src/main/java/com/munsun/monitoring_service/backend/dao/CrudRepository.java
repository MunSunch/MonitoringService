package com.munsun.monitoring_service.backend.dao;

import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import java.util.Optional;

/**
 * An abstraction that contains the main CRUD operations,
 * some of them are omitted as unnecessary
 *
 * @param <Value> entity
 * @param <Key> primary key
 * @author apple
 * @version $Id: $Id
 */
public interface CrudRepository<Value, Key> {
    /**
     * Return an entity wrapped in Optional with the primary key value from the database.
     *
     * @param key primary key
     * @return Optional<VALUE>
     */
    Optional<Value> getById(Key key);

    /**
     * Save the entity in the database and assign it a primary key in accordance
     * with the rules established by the DBMS
     *
     * @param value entity
     * @return VALUE entity and the modified primary key
     * @throws com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException it is thrown out when the restrictions set by the DBMS are violated
     */
    Value save(Value value) throws DatabaseConstraintException;
    /**
     * <p>deleteById.</p>
     *
     * @param key a Key object
     * @return a {@link java.util.Optional} object
     */
    Optional<Value> deleteById(Key key);
}
