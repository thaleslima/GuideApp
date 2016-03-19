package com.guideapp.guideapp.model;

/**
 * Created by thales on 6/13/15.
 */
public class LocalDetail {
    private int id;
    private int ico;
    private String text;
    private boolean divider;
    private int viewType;
    private double latitude;
    private double longitude;

    public LocalDetail(int ico, String text, boolean divider, int viewType) {
        this.ico = ico;
        this.text = text;
        this.divider = divider;
        this.viewType = viewType;
    }

    public LocalDetail(boolean divider, int viewType) {
        this.divider = divider;
        this.viewType = viewType;
    }

    public LocalDetail(boolean divider, int viewType, double latitude, double longitude) {
        this.divider = divider;
        this.viewType = viewType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIco() {
        return ico;
    }

    public void setIco(int ico) {
        this.ico = ico;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDivider() {
        return divider;
    }

    public void setDivider(boolean divider) {
        this.divider = divider;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
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
