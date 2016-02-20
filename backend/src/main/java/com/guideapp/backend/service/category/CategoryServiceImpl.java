package com.guideapp.backend.service.category;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.dao.category.CategoryDAO;
import com.guideapp.backend.dao.category.CategoryDAOImpl;
import com.guideapp.backend.entity.Category;
import com.guideapp.backend.util.ValidationUtil;

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

        if(category == null) {
            throw new ConflictException("Categoria não informada.");
        }

        if(ValidationUtil.nullOrEmpty(category.getDescription())){
            throw new ConflictException("Descrição não informada.");
        }

        categoryDAO.insert(category);
    }

    @Override
    public void update(Category category) throws ConflictException, NotFoundException {
        if(category == null) {
            throw new ConflictException("Categoria não informada.");
        }

        if(ValidationUtil.nullOrEmpty(category.getId())){
            throw new ConflictException("Id não informado.");
        }

        if(ValidationUtil.nullOrEmpty(category.getDescription())){
            throw new ConflictException("Descrição não informada.");
        }

        Category c = categoryDAO.getByKey(category.getId());

        if(c == null){
            throw new NotFoundException("Categoria não encontrada.");
        }

        categoryDAO.insert(category);
    }

    @Override
    public void remove(Long id) throws ConflictException, NotFoundException {
        Category city = categoryDAO.getByKey(id);

        if(city == null){
            throw new NotFoundException("Categoria não encontrada.");
        }

        categoryDAO.delete(city);
    }
}
