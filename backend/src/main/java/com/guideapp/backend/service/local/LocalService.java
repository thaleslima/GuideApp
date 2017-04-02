package com.guideapp.backend.service.local;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Local;

import java.util.List;

public interface LocalService {
    /**
     * Returns a locals list by filters
     */
    List<Local> list(Long idCity, Long idCategory, Long[] subCategories) throws ConflictException;

    /**
     * Returns a locals list by filters
     */
    List<Local> list(String search);

    /**
     * Returns a Local object with the given id.
     */
    Local getById(Long id) throws NotFoundException;

    /**
     * Creates a Local object
     */
    void insert(Local local) throws ConflictException;

    /**
     * Updates a Local object
     */
    void update(Local local) throws ConflictException, NotFoundException;

    /**
     * Removes a Local object
     */
    void remove(Long id) throws NotFoundException;
}
