package com.dosvales.vagoapp.dao.generic;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for Data Access Object (DAO). It acts as a manager
 * responsible for the persistence operations on a specific type {@code T}.
 * It is responsible for loading, saving, searching and deleting entities of
 * the according type.
 *
 * @param <T> Type of an entity for which this instance is to be used
 */
public interface GenericDao<T, ID extends Serializable> {
	
	 /**
     * Returns whether an entity with the given id exists.
     *
     * @param id of an entity
     * @return the entity with the given id or {@literal null} if none found
     */
    T findById(ID id);

    /**
     * Returns all instances of a type.
     *
     * @return all entities
     */
    List<T> findAll();

    /**
     * Saves a given entity.
     *
     * @param t Entity type
     * 
     * @return T Entity saved
     */
    T save(T t);
    
    /**
     * Update a given entity.
     *
     * @param t Entity type
     * 
     * @return T Entity updated
     */
    T update(T t);

    /**
     * Deletes the entity with the given id.
     *
     * @param t of Entity type
     */
    void delete(T t);
}
