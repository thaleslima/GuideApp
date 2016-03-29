package com.guideapp.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.guideapp.backend.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
@Entity
public class Local {

    @Id
    private Long id;

    private String description;
    private String site;
    private String phone;
    private String address;
    private boolean wifi;
    private String detail;
    private double latitude;
    private double longitude;
    private String imagePath;

    @Index
    private Long idCity;

    @Index
    private List<Long> idCategories;

    @Index
    private List<Long> idSubCategories;

    private long timestamp;

    @Ignore
    private List<SubCategory> subCategories;

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
        this.description = StringUtil.trim(description);
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = StringUtil.trim(site);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = StringUtil.trim(phone);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = StringUtil.trim(address);
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = StringUtil.trim(detail);
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

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = StringUtil.trim(imagePath);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Long> getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(List<Long> idCategories) {
        this.idCategories = idCategories;
    }

    public List<Long> getIdSubCategories() {
        return idSubCategories;
    }

    public void setIdSubCategories(List<Long> idSubCategories) {
        this.idSubCategories = idSubCategories;
    }

    public List<SubCategory> getSubCategories() {
        if(subCategories == null) {
            subCategories = new ArrayList<>();
        }

        return subCategories;
    }
}
