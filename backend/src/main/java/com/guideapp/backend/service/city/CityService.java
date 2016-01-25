package com.guideapp.backend.service.city;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.City;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface CityService {
    List<City> list();
    List<City> list(String search) throws NotFoundException;
    City getById(Long id) throws NotFoundException;
    void insert(City city) throws ConflictException, NotFoundException;
    void update(City city) throws ConflictException, NotFoundException;
    void remove(Long id) throws ConflictException, NotFoundException;
}
