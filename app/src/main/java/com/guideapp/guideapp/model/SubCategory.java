package com.guideapp.guideapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thales on 3/25/16.
 */
public class SubCategory implements Parcelable {
    private Long id;
    private String description;
    private Long idCategory;
    private long timestamp;

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

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.description);
        dest.writeValue(this.idCategory);
        dest.writeLong(this.timestamp);
    }

    protected SubCategory(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.description = in.readString();
        this.idCategory = (Long) in.readValue(Long.class.getClassLoader());
        this.timestamp = in.readLong();
    }

    public SubCategory[] newArray(int size) {
        return new SubCategory[size];
    }

    public static final Parcelable.Creator<SubCategory> CREATOR = new Parcelable.Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel source) {
            return new SubCategory(source);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };
}
