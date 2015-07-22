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

    public LocalDetail(int ico, String text, boolean divider, int viewType) {
        this.ico = ico;
        this.text = text;
        this.divider = divider;
        this.viewType = viewType;
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
}
