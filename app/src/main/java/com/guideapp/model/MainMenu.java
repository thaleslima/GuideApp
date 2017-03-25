package com.guideapp.model;

public class MainMenu {
    private final long id;
    private final int idTitle;
    private final int idImage;
    private final int idColorPrimary;

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
