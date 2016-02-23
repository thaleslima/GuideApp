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
    private double latitude;
    private double longitude;

    private long timestamp;

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
        this.name = StringUtil.trim(name);
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = StringUtil.trim(uf);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
