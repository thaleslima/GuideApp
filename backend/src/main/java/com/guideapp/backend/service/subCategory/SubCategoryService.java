package com.guideapp.backend.service.subcategory;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Local;
import com.guideapp.backend.entity.SubCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by thales on 1/24/16.
 */
public interface SubCategoryService {

    /**
     * Returns a sub-categories list
     * @return the sub-categories list
     */
    List<SubCategory> list();

    /**
     * Returns a sub-categories list by Category key
     * @param idCategory the Long representation of the Category Key.
     * @return the sub-categories list
     */
    List<SubCategory> list(Long idCategory);

    /**
     * Returns a SubCategory object with the given id..
     * @param id the Long representation of the SubCategory Key.
     * @return SubCategory object.
     * @throws NotFoundException when there is no SubCategory with the given id.
     */
    SubCategory getById(Long id) throws NotFoundException;

    /**
     * Creates a SubCategory object
     * @param subCategory A SubCategory object representing user's inputs
     * @throws ConflictException when there is a error in SubCategory object
     */
    void insert(SubCategory subCategory) throws ConflictException;


    /**
     * Updates a SubCategory object
     * @param subCategory A SubCategory object representing user's inputs
     * @throws ConflictException when there is a error in SubCategory object
     * @throws NotFoundException when there is no SubCategory with the given id
     */
    void update(SubCategory subCategory) throws ConflictException, NotFoundException;


    /**
     * Removes a SubCategory object
     * @param id the Long representation of the SubCategory Key.
     * @throws NotFoundException when there is a error in SubCategory object
     */
    void remove(Long id) throws NotFoundException;

    /**
     * Get map representing the sub category list
     */
    Map<Long, SubCategory> getMap();
}
