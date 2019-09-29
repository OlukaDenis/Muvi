package com.premar.muvi.model.trailers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("id")
    private String trailer_id;

    @SerializedName("key")
    private String trailer_key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    @Expose
    private String site;

    @SerializedName("size")
    private int size;

    @SerializedName("type")
    private String type;

    public String getTrailer_id() {
        return trailer_id;
    }

    public void setTrailer_id(String trailer_id) {
        this.trailer_id = trailer_id;
    }

    public String getTrailer_key() {
        return trailer_key;
    }

    public void setTrailer_key(String trailer_key) {
        this.trailer_key = trailer_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
