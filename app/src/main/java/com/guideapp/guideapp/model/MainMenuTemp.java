package com.guideapp.guideapp.model;

public class MainMenuTemp {
    private long id;
    private int idTitle;
    private String text;
    private int image;
    private int color;
    private int colorDark;
    private int theme;

    public int getIdTitle() {
        return idTitle;
    }

    public MainMenuTemp(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public MainMenuTemp(long id, int idTitle, int image, int color) {
        this.id = id;
        this.idTitle = idTitle;
        this.image = image;
        this.color = color;
    }

    public MainMenuTemp(long id, int idTitle, int image, int color, int colorDark, int theme) {
        this.id = id;
        this.idTitle = idTitle;
        this.image = image;
        this.color = color;
        this.colorDark = colorDark;
        this.theme = theme;
    }

    public void setIdTitle(int idTitle) {
        this.idTitle = idTitle;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getColorDark() {
        return colorDark;
    }

    public void setColorDark(int colorDark) {
        this.colorDark = colorDark;
    }
}
