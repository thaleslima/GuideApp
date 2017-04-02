package com.guideapp.backend.dao.local;

import com.guideapp.backend.dao.generic.IGenericDAO;
import com.guideapp.backend.entity.Local;

import java.util.List;

public interface LocalDAO extends IGenericDAO<Local> {
    List<Local> listByFilters(Long idCity, Long idCategory, Long[] subCategories);
}
