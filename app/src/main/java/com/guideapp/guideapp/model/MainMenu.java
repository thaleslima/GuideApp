package com.guideapp.guideapp.model;

/**
 * Main menu model
 */
public class MainMenu {
    private long id;
    private int idTitle;
    private int idImage;
    private int idColorPrimary;
    private int idColorPrimaryDark;
    private int idTheme;

    /**
     * Simple constructor to use when creating a view from code.
     * @param id Id
     * @param idTitle Title id
     * @param idImage Image id
     * @param idColorPrimary Color primary id
     */
    public MainMenu(long id,
                    int idTitle,
                    int idImage,
                    int idColorPrimary) {
        this.id = id;
        this.idTitle = idTitle;
        this.idImage = idImage;
        this.idColorPrimary = idColorPrimary;
    }

    /**
     * Simple constructor to use when creating a view from code.
     * @param id Id
     * @param idTitle Title id
     * @param idImage Image id
     * @param idColorPrimary Color primary id
     * @param idColorPrimaryDark Color primary dark id
     * @param idTheme Theme id
     */
    public MainMenu(long id,
                    int idTitle,
                    int idImage,
                    int idColorPrimary,
                    int idColorPrimaryDark,
                    int idTheme) {
        this.id = id;
        this.idTitle = idTitle;
        this.idImage = idImage;
        this.idColorPrimary = idColorPrimary;
        this.idColorPrimaryDark = idColorPrimaryDark;
        this.idTheme = idTheme;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdTitle(int idTitle) {
        this.idTitle = idTitle;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getIdColorPrimary() {
        return idColorPrimary;
    }

    public void setIdColorPrimary(int idColorPrimary) {
        this.idColorPrimary = idColorPrimary;
    }

    public int getIdColorPrimaryDark() {
        return idColorPrimaryDark;
    }

    public void setIdColorPrimaryDark(int idColorPrimaryDark) {
        this.idColorPrimaryDark = idColorPrimaryDark;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }

    public int getIdTitle() {
        return idTitle;
    }

}
