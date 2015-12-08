package com.guideapp.guideapp.model;

import java.util.Date;

/**
 * Created by thales on 11/29/15.
 */
public class Opinion {
    private int id;
    private String name;
    private String opinion;
    private Date date;
    private String nameLocal;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    private int rating;

    public Opinion(int id, String name, String opinion, Date date, String nameLocal, int rating) {
        this.id = id;
        this.name = name;
        this.opinion = opinion;
        this.date = date;
        this.nameLocal = nameLocal;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNameLocal() {
        return nameLocal;
    }

    public void setNameLocal(String nameLocal) {
        this.nameLocal = nameLocal;
    }
}
