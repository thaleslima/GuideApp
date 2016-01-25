package com.guideapp.backend.service.category;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Category;
import com.guideapp.backend.entity.City;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface CategoryService {
    List<Category> list();
    List<Category> list(String search) throws NotFoundException;
    Category getById(Long id) throws NotFoundException;
    void insert(Category category) throws ConflictException, NotFoundException;
    void update(Category category) throws ConflictException, NotFoundException;
    void remove(Long id) throws ConflictException, NotFoundException;
}
