package com.guideapp.backend.service.subCategory;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.dao.category.CategoryDAO;
import com.guideapp.backend.dao.category.CategoryDAOImpl;
import com.guideapp.backend.dao.subCategory.SubCategoryDAO;
import com.guideapp.backend.dao.subCategory.SubCategoryDAOImpl;
import com.guideapp.backend.entity.Category;
import com.guideapp.backend.entity.SubCategory;
import com.guideapp.backend.service.category.CategoryService;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public class SubCategoryServiceImpl implements SubCategoryService {

    SubCategoryDAO subCategoryDAO;

    public SubCategoryServiceImpl() {
        this.subCategoryDAO = new SubCategoryDAOImpl();
    }

    @Override
    public List<SubCategory> list() {
        return subCategoryDAO.listAll();
    }

    @Override
    public List<SubCategory> list(String search) throws NotFoundException {
        return subCategoryDAO.listByProperty("name", search);
    }

    @Override
    public SubCategory getById(Long id) throws NotFoundException {
        SubCategory subCategory = subCategoryDAO.getByKey(id);

        if(subCategory == null) {
            throw new NotFoundException("Category not found");
        }

        return subCategory;
    }

    @Override
    public void insert(SubCategory subCategory) throws ConflictException, NotFoundException {
        subCategoryDAO.insert(subCategory);
    }

    @Override
    public void update(SubCategory subCategory) throws ConflictException, NotFoundException {
        subCategoryDAO.insert(subCategory);
    }

    @Override
    public void remove(Long id) throws ConflictException, NotFoundException {
        SubCategory city = subCategoryDAO.getByKey(id);
        subCategoryDAO.delete(city);
    }
}
