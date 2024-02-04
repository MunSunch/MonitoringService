package com.munsun.monitoring_service.backend.dao;

import java.sql.SQLException;
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
    Optional<Value> getById(Key key) throws SQLException;

    /**
     * Save the entity in the database and assign it a primary key in accordance
     * with the rules established by the DBMS
     *
     * @param value entity
     * @return VALUE entity and the modified primary key
     */
    Long save(Value value) throws SQLException;
    /**
     * <p>deleteById.</p>
     *
     * @param key a Key object
     * @return a {@link java.util.Optional} object
     */
    Integer deleteById(Key key) throws SQLException;
}
