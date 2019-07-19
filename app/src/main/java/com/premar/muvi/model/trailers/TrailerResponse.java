package com.premar.muvi.model.trailers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailerResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Trailer> results;

    public int getMovieId() {
        return id;
    }

    public void setMovieId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
