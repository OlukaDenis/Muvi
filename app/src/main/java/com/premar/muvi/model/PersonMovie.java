package com.premar.muvi.model;

import com.google.gson.annotations.SerializedName;

public class PersonMovie {
    @SerializedName("character")
    private String character;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("release_date")
    private String release_date;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
