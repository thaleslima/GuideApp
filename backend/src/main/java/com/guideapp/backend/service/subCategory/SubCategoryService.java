package com.guideapp.backend.service.subCategory;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.SubCategory;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface SubCategoryService {
    List<SubCategory> list();
    List<SubCategory> list(String search) throws NotFoundException;
    SubCategory getById(Long id) throws NotFoundException;
    void insert(SubCategory subCategory) throws ConflictException, NotFoundException;
    void update(SubCategory subCategory) throws ConflictException, NotFoundException;
    void remove(Long id) throws ConflictException, NotFoundException;
}
