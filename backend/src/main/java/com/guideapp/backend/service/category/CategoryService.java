package com.guideapp.backend.service.category;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Category;

import java.util.List;

public interface CategoryService {
    /**
     * Returns a Category list
     */
    List<Category> list();

    /**
     * Returns a Category list by filters
     */
    List<Category> list(String search);

    /**
     * Returns a Category object with the given id..
     */
    Category getById(Long id) throws NotFoundException;

    /**
     * Creates a Category object
     */
    void insert(Category category) throws ConflictException;

    /**
     * Updates a City object
     */
    void update(Category category) throws ConflictException, NotFoundException;

    /**
     * Removes a Category object
     */
    void remove(Long id) throws NotFoundException;
}
