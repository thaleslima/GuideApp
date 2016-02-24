package com.guideapp.backend.service.category;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.dao.category.CategoryDAO;
import com.guideapp.backend.dao.category.CategoryDAOImpl;
import com.guideapp.backend.entity.Category;
import com.guideapp.backend.util.ValidationUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO mCategoryDAO;

    /**
     * Constructor
     */
    public CategoryServiceImpl() {
        this.mCategoryDAO = new CategoryDAOImpl();
    }

    @Override
    public List<Category> list() {
        return mCategoryDAO.listAll();
    }

    @Override
    public List<Category> list(String search) {
        return mCategoryDAO.listByProperty("name", search);
    }

    @Override
    public Category getById(Long id) throws NotFoundException {
        Category category = mCategoryDAO.getByKey(id);

        if (category == null) {
            throw new NotFoundException("Category not found");
        }

        return category;
    }

    @Override
    public void insert(Category category) throws ConflictException {

        if (category == null) {
            throw new ConflictException("Categoria não informada.");
        }

        if (ValidationUtil.nullOrEmpty(category.getDescription())) {
            throw new ConflictException("Descrição não informada.");
        }

        category.setTimestamp(new Date().getTime());
        mCategoryDAO.insert(category);
    }

    @Override
    public void update(Category category) throws ConflictException, NotFoundException {
        if (category == null) {
            throw new ConflictException("Categoria não informada.");
        }

        if (ValidationUtil.nullOrEmpty(category.getId())) {
            throw new ConflictException("Id não informado.");
        }

        if (ValidationUtil.nullOrEmpty(category.getDescription())) {
            throw new ConflictException("Descrição não informada.");
        }

        Category c = mCategoryDAO.getByKey(category.getId());

        if (c == null) {
            throw new NotFoundException("Categoria não encontrada.");
        }

        category.setTimestamp(new Date().getTime());
        mCategoryDAO.insert(category);
    }

    @Override
    public void remove(Long id) throws NotFoundException {
        Category city = mCategoryDAO.getByKey(id);

        if (city == null) {
            throw new NotFoundException("Categoria não encontrada.");
        }

        mCategoryDAO.delete(city);
    }
}
