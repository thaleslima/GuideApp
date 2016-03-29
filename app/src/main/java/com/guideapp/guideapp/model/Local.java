package com.guideapp.guideapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 6/13/15.
 */
public class Local implements Parcelable {
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
    private List<Long> idCategories;
    private List<Long> idSubCategories;
    private long timestamp;
    private List<SubCategory> subCategories;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescriptionSubCategories() {
        if(subCategories != null) {
            int size = subCategories.size();
            String description = "";

            for (int i = 0; i < size; i++) {
                if(i > 0){
                    description += " | ";
                }
                description += subCategories.get(i).getDescription();
            }

            return description;
        }

        return "";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.description);
        dest.writeString(this.site);
        dest.writeString(this.phone);
        dest.writeString(this.address);
        dest.writeByte(wifi ? (byte) 1 : (byte) 0);
        dest.writeString(this.detail);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.imagePath);
        dest.writeValue(this.idCity);
        dest.writeList(this.idCategories);
        dest.writeList(this.idSubCategories);
        dest.writeLong(this.timestamp);
        dest.writeTypedList(subCategories);
    }

    protected Local(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.description = in.readString();
        this.site = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.wifi = in.readByte() != 0;
        this.detail = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.imagePath = in.readString();
        this.idCity = (Long) in.readValue(Long.class.getClassLoader());
        this.idCategories = new ArrayList<Long>();
        in.readList(this.idCategories, Long.class.getClassLoader());
        this.idSubCategories = new ArrayList<Long>();
        in.readList(this.idSubCategories, Long.class.getClassLoader());
        this.timestamp = in.readLong();
        this.subCategories = in.createTypedArrayList(SubCategory.CREATOR);
    }

    public static final Creator<Local> CREATOR = new Creator<Local>() {
        @Override
        public Local createFromParcel(Parcel source) {
            return new Local(source);
        }

        @Override
        public Local[] newArray(int size) {
            return new Local[size];
        }
    };
}
