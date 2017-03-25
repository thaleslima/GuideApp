package com.guideapp.model;

import java.util.List;

public class Local {
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
    private Long idCity;
    private long idCategory;
    private List<Long> idCategories;
    private List<Long> idSubCategories;
    private long timestamp;
    private List<SubCategory> subCategories;
    private String descriptionSubCategories;
    private boolean favorite;

    public Local() {

    }

    public Local(String description, String address) {
        this.description = description;
        this.address = address;
    }

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
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        this.detail = detail;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
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

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescriptionSubCategories() {
        return this.descriptionSubCategories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public void setDescriptionSubCategories(String descriptionSubCategories) {
        this.descriptionSubCategories = descriptionSubCategories;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
