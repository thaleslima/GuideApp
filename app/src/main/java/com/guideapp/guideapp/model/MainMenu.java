package com.guideapp.guideapp.model;

public class MainMenu {
    private long id;
    private int idTitle;
    private int idImage;
    private int idColorPrimary;

    public MainMenu(long id,
                    int idTitle,
                    int idImage,
                    int idColorPrimary) {
        this.id = id;
        this.idTitle = idTitle;
        this.idImage = idImage;
        this.idColorPrimary = idColorPrimary;
    }

    public long getId() {
        return id;
    }

    public int getIdImage() {
        return idImage;
    }

    public int getIdColorPrimary() {
        return idColorPrimary;
    }

    public int getIdTitle() {
        return idTitle;
    }
}
