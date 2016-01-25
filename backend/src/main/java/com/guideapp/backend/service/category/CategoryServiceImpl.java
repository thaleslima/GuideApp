package com.guideapp.backend.service.category;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.dao.category.CategoryDAO;
import com.guideapp.backend.dao.category.CategoryDAOImpl;
import com.guideapp.backend.entity.Category;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public class CategoryServiceImpl implements CategoryService {

    CategoryDAO categoryDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    @Override
    public List<Category> list() {
        return categoryDAO.listAll();
    }

    @Override
    public List<Category> list(String search) throws NotFoundException {
        return categoryDAO.listByProperty("name", search);
    }

    @Override
    public Category getById(Long id) throws NotFoundException {
        Category category = categoryDAO.getByKey(id);

        if(category == null) {
            throw new NotFoundException("Category not found");
        }

        return category;
    }

    @Override
    public void insert(Category category) throws ConflictException, NotFoundException {
        categoryDAO.insert(category);
    }

    @Override
    public void update(Category category) throws ConflictException, NotFoundException {
        categoryDAO.insert(category);
    }

    @Override
    public void remove(Long id) throws ConflictException, NotFoundException {
        Category city = categoryDAO.getByKey(id);
        categoryDAO.delete(city);
    }
}
