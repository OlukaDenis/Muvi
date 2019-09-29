package com.premar.muvi.model;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getGenreId() {
        return id;
    }

    public void setGenreId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return name;
    }

    public void setGenreName(String name) {
        this.name = name;
    }
}
