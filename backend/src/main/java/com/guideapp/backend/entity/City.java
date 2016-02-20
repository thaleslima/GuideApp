package com.guideapp.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.guideapp.backend.util.StringUtil;

/**
 * Created by thales on 1/24/16.
 */
@Entity
public class City {

    @Id
    private Long id;
    private String name;
    private String uf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtil.removeSpaceWhite(name);
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = StringUtil.removeSpaceWhite(uf);
    }
}
