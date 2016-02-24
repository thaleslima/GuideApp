package com.guideapp.backend.service.category;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Category;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface CategoryService {

    /**
     * Returns a Category list
     * @return the Category list
     */
    List<Category> list();

    /**
     * Returns a Category list by filters
     * @param search text generic
     * @return Category list
     */
    List<Category> list(String search);


    /**
     * Returns a Category object with the given id..
     * @param id the Long representation of the Category Key.
     * @return Category object.
     * @throws NotFoundException when there is no Category with the given id.
     */
    Category getById(Long id) throws NotFoundException;

    /**
     * Creates a Category object
     * @param category A Category object representing user's inputs
     * @throws ConflictException when there is a error in Category object
     */
    void insert(Category category) throws ConflictException;


    /**
     * Updates a City object
     * @param category A Category object representing user's inputs
     * @throws ConflictException when there is a error in Category object
     * @throws NotFoundException when there is no Category with the given id
     */
    void update(Category category) throws ConflictException, NotFoundException;


    /**
     * Removes a Category object
     * @param id the Long representation of the Category Key.
     * @throws NotFoundException when there is a error in Category object
     */
    void remove(Long id) throws NotFoundException;
}
