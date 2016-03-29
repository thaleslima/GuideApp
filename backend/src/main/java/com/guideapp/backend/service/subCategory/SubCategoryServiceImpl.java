package com.guideapp.backend.service.subcategory;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.repackaged.com.google.api.client.util.ArrayMap;
import com.guideapp.backend.dao.subcategory.SubCategoryDAO;
import com.guideapp.backend.dao.subcategory.SubCategoryDAOImpl;
import com.guideapp.backend.entity.SubCategory;
import com.guideapp.backend.util.MemcacheUtil;
import com.guideapp.backend.util.ValidationUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 * Created by thales on 1/24/16.
 */
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryDAO mSubCategoryDAO;
    private static final String KEY_MAP = "map-sub-category";

    /**
     * Configure the sub-category service.
     */
    public SubCategoryServiceImpl() {
        this.mSubCategoryDAO = new SubCategoryDAOImpl();
    }

    @Override
    public List<SubCategory> list() {
        return mSubCategoryDAO.listAll();
    }

    @Override
    public List<SubCategory> list(Long idCategory) {
        return mSubCategoryDAO.listByProperty("idCategory", idCategory);
    }

    @Override
    public SubCategory getById(Long id) throws NotFoundException {
        SubCategory subCategory = mSubCategoryDAO.getByKey(id);

        if (subCategory == null) {
            throw new NotFoundException("Category not found");
        }

        return subCategory;
    }

    @Override
    public void insert(SubCategory subCategory) throws ConflictException {
        if (subCategory == null) {
            throw new ConflictException("Sub-Categoria não informada.");
        }

        if (ValidationUtil.nullOrEmpty(subCategory.getDescription())) {
            throw new ConflictException("Descrição não informada.");
        }

        if (ValidationUtil.nullOrEmpty(subCategory.getIdCategory())) {
            throw new ConflictException("Categoria não informada.");
        }

        subCategory.setTimestamp(new Date().getTime());
        mSubCategoryDAO.insert(subCategory);
    }

    @Override
    public void update(SubCategory subCategory) throws ConflictException, NotFoundException {
        if (subCategory == null) {
            throw new ConflictException("Sub-Categoria não informada.");
        }

        if (ValidationUtil.nullOrEmpty(subCategory.getDescription())) {
            throw new ConflictException("Descrição não informada.");
        }

        if (ValidationUtil.nullOrEmpty(subCategory.getIdCategory())) {
            throw new ConflictException("Categoria não informada.");
        }

        if (ValidationUtil.nullOrEmpty(subCategory.getId())) {
            throw new ConflictException("Id não informado.");
        }

        SubCategory s = mSubCategoryDAO.getByKey(subCategory.getId());

        if (s == null) {
            throw new NotFoundException("Sub-Categoria não encontrada.");
        }

        subCategory.setTimestamp(new Date().getTime());
        mSubCategoryDAO.insert(subCategory);
    }

    @Override
    public void remove(Long id) throws NotFoundException {
        SubCategory s = mSubCategoryDAO.getByKey(id);

        if (s == null) {
            throw new NotFoundException("Sub-Categoria não encontrada.");
        }

        mSubCategoryDAO.delete(s);
    }

    @Override
    public Map<Long, SubCategory> getMap() {
        List<SubCategory> list = getMapInMemCache();
        if (list == null) {
            list = list();
            putMapInMemCache(list);
        }

        int size = list.size();
        Map<Long, SubCategory> map = new HashMap<>();

        for (int i = 0; i < size; i++) {
            map.put(list.get(i).getId(), list.get(i));
        }

        return map;
    }

    /**
     * Insert map in memory cache
     */
    private void putMapInMemCache(List<SubCategory> list){
        MemcacheService syncCache = MemcacheUtil.getMemcacheService();
        syncCache.put(KEY_MAP, list);
    }

    /**
     * Get map in memory cache
     * @return SubCategory map
     */
    private List<SubCategory> getMapInMemCache(){
        MemcacheService syncCache = MemcacheUtil.getMemcacheService();
        return (List<SubCategory>) syncCache.get(KEY_MAP);
    }
}
