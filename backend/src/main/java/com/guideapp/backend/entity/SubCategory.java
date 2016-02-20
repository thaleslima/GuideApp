package com.guideapp.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.guideapp.backend.util.StringUtil;

/**
 * Created by thales on 1/24/16.
 */
@Entity
public class SubCategory {

    @Id
    private Long id;
    private String description;

    @Index
    private Long idCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = StringUtil.removeSpaceWhite(description);

    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }
}
