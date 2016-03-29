package com.guideapp.backend.service.local;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Local;
import com.guideapp.backend.entity.SubCategory;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface LocalService {

    /**
     * Returns a locals list by filters
     * @param idCity the id of the city
     * @param idCategory the id of the category.
     * @param subCategories the list of sub category id.
     * @return the local list
     */
    List<Local> list(Long idCity, Long idCategory, Long[] subCategories) throws ConflictException;

    /**
     * Returns a locals list by filters
     * @param search generic
     * @return the locals list
     */
    List<Local> list(String search);

    /**
     * Returns a Local object with the given id.
     * @param id the Long representation of the local Key.
     * @return Local object.
     * @throws NotFoundException when there is no Local with the given id.
     */
    Local getById(Long id) throws NotFoundException;

    /**
     * Creates a Local object
     * @param local A Local object representing user's inputs
     * @throws ConflictException when there is a error in Local object
     */
    void insert(Local local) throws ConflictException;

    /**
     * Updates a Local object
     * @param local A Local object representing user's inputs
     * @throws ConflictException when there is a error in Local object
     * @throws NotFoundException when there is no Local with the given id
     */
    void update(Local local) throws ConflictException, NotFoundException;


    /**
     * Removes a Local object
     * @param id the Long representation of the local Key.
     * @throws NotFoundException when there is a error in Local object
     */
    void remove(Long id) throws NotFoundException;
}
