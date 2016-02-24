package com.guideapp.backend.service.city;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.City;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface CityService {

    /**
     * Returns a cities list
     * @return the cities list
     */
    List<City> list();

    /**
     * Returns a cities list by filters
     * @param search text generic
     * @return the cities list
     */
    List<City> list(String search);

    /**
     * Returns a City object with the given id..
     * @param id the Long representation of the City Key.
     * @return City object.
     * @throws NotFoundException when there is no City with the given id.
     */
    City getById(Long id) throws NotFoundException;


    /**
     * Creates a City object
     * @param city A City object representing user's inputs
     * @throws ConflictException when there is a error in City object
     */
    void insert(City city) throws ConflictException;


    /**
     * Updates a City object
     * @param city A City object representing user's inputs
     * @throws ConflictException when there is a error in City object
     * @throws NotFoundException when there is no City with the given id
     */
    void update(City city) throws ConflictException, NotFoundException;


    /**
     * Removes a City object
     * @param id the Long representation of the City Key.
     * @throws NotFoundException when there is a error in City object
     */
    void remove(Long id) throws NotFoundException;
}
