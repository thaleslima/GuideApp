package com.guideapp.backend.dao.local;

import com.guideapp.backend.dao.generic.IGenericDAO;
import com.guideapp.backend.entity.Local;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface LocalDAO extends IGenericDAO<Local> {

    /**
     * Returns a locals list by filters
     * @param idCity the id of the city
     * @param idCategory the id of the category.
     * @param subCategories the list of sub category id.
     * @return the local list
     */
    List<Local> listByFilters(Long idCity, Long idCategory, Long[] subCategories);
}
