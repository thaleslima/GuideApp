package com.guideapp.backend.dao.generic;

import com.googlecode.objectify.Key;

import java.util.List;
import java.util.Map;

public interface IGenericDAO<T> {

    Key<T> save(T entity);

    void insert(T entity);

    void delete(T entity);

    void delete(Iterable<?> entities);

    void update(T entity);

    List<T> listAll();

    T getByProperty(String propName, Object propValue);

    T getById(Long id);

    T getByKey(Long id);

    List<T> listByProperty(String propName, Object propValue);

    List<T> listByProperties(Map<String, Object> filters);
}