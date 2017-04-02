package com.guideapp.backend.service.subcategory;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.SubCategory;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {
    /**
     * Returns a sub-categories list
     */
    List<SubCategory> list();

    /**
     * Returns a sub-categories list by Category key
     */
    List<SubCategory> list(Long idCategory);

    /**
     * Returns a SubCategory object with the given id..
     */
    SubCategory getById(Long id) throws NotFoundException;

    /**
     * Creates a SubCategory object
     */
    void insert(SubCategory subCategory) throws ConflictException;

    /**
     * Updates a SubCategory object
     */
    void update(SubCategory subCategory) throws ConflictException, NotFoundException;

    /**
     * Removes a SubCategory object
     */
    void remove(Long id) throws NotFoundException;

    /**
     * Get map representing the sub category list
     */
    Map<Long, SubCategory> getMap();
}
