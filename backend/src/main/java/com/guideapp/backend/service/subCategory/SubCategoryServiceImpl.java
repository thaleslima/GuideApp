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
import com.guideapp.backend.util.ValidationUtil;

import java.util.Date;
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
    public List<SubCategory> list(Long idCategory) throws NotFoundException {
        return subCategoryDAO.listByProperty("idCategory", idCategory);
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
        if(subCategory == null) {
            throw new ConflictException("Sub-Categoria não informada.");
        }

        if(ValidationUtil.nullOrEmpty(subCategory.getDescription())){
            throw new ConflictException("Descrição não informada.");
        }

        if(ValidationUtil.nullOrEmpty(subCategory.getIdCategory())){
            throw new ConflictException("Categoria não informada.");
        }

        subCategory.setTimestamp(new Date().getTime());
        subCategoryDAO.insert(subCategory);
    }

    @Override
    public void update(SubCategory subCategory) throws ConflictException, NotFoundException {
        if(subCategory == null) {
            throw new ConflictException("Sub-Categoria não informada.");
        }

        if(ValidationUtil.nullOrEmpty(subCategory.getDescription())){
            throw new ConflictException("Descrição não informada.");
        }

        if(ValidationUtil.nullOrEmpty(subCategory.getIdCategory())){
            throw new ConflictException("Categoria não informada.");
        }

        if(ValidationUtil.nullOrEmpty(subCategory.getId())){
            throw new ConflictException("Id não informado.");
        }

        SubCategory s = subCategoryDAO.getByKey(subCategory.getId());

        if(s == null){
            throw new NotFoundException("Sub-Categoria não encontrada.");
        }

        subCategory.setTimestamp(new Date().getTime());
        subCategoryDAO.insert(subCategory);
    }

    @Override
    public void remove(Long id) throws ConflictException, NotFoundException {
        SubCategory s = subCategoryDAO.getByKey(id);

        if(s == null){
            throw new NotFoundException("Sub-Categoria não encontrada.");
        }

        subCategoryDAO.delete(s);
    }
}
