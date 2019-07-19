package com.premar.muvi.model;

import com.google.gson.annotations.SerializedName;

public class Todos {
    @SerializedName("complete")
    private boolean complete;

    @SerializedName("id")
    private int id;

    @SerializedName("text")
    private String text;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
