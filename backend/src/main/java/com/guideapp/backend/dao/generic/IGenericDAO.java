package com.guideapp.backend.dao.generic;

import com.googlecode.objectify.Key;

import java.util.List;
import java.util.Map;

/**
 * Class generic for access dataStore
 * @param <T> the T represents a entity of dataStore
 */
public interface IGenericDAO<T> {

    /**
     * Save a T object.
     * @param entity A T object representing data's inputs.
     * @return the key of T.
     */
    Key<T> save(T entity);


    /**
     * Creates a T object.
     * @param entity A T object representing data's inputs.
     */
    void insert(T entity);

    /**
     * Delete a T object.
     * @param entity A T object to be deleted.
     */
    void delete(T entity);

    /**
     * Delete a T list.
     * @param entities A T list to be deleted.
     */
    void delete(Iterable<?> entities);

    /**
     * Creates a T object.
     * @param entity A T object representing data's inputs.
     */
    void update(T entity);

    /**
     * Returns a list of T.
     * @return T list.
     */
    List<T> listAll();

    /**
     * Returns a T object by filters.
     * @param propName the name of parameter.
     * @param propValue the value of parameter.
     * @return T object
     */
    T getByProperty(String propName, Object propValue);

    /**
     * Returns a T object with the given id.
     * @param id the Long representation of the local Key.
     * @return T object.
     */
    T getById(Long id);

    /**
     * Returns a T object with the given key.
     * @param id the Long representation of the local Key.
     * @return T object.
     */
    T getByKey(Long id);

    /**
     * Returns a T list by filters.
     * @param propName the name of parameter.
     * @param propValue the value of parameter.
     * @return T list
     */
    List<T> listByProperty(String propName, Object propValue);

    /**
     * Returns a T list by filters.
     * @param filters the list of parameter.
     * @return T list
     */
    List<T> listByProperties(Map<String, Object> filters);
}