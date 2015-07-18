package com.guideapp.guideapp.model;

/**
 * Created by thales on 6/13/15.
 */
public class Local {
    private int id;
    private String description;
    private String address;

    public Local(String description, String address) {
        this.description = description;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
