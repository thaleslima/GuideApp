package com.guideapp.backend.service.city;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.City;

import java.util.List;

public interface CityService {

    /**
     * Returns a cities list
     */
    List<City> list();

    /**
     * Returns a cities list by filters
     */
    List<City> list(String search);

    /**
     * Returns a City object with the given id..
     */
    City getById(Long id) throws NotFoundException;


    /**
     * Creates a City object
     */
    void insert(City city) throws ConflictException;


    /**
     * Updates a City object
     */
    void update(City city) throws ConflictException, NotFoundException;


    /**
     * Removes a City object
     */
    void remove(Long id) throws NotFoundException;
}
