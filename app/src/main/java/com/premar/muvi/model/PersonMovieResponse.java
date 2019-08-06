package com.premar.muvi.model;

import com.google.gson.annotations.SerializedName;
import com.premar.muvi.model.credits.Crew;

import java.util.List;

public class PersonMovieResponse {
    @SerializedName("id")
    private int movie_id;

    @SerializedName("cast")
    private List<PersonMovie> personMovies;

    @SerializedName("crew")
    private List<Crew> crewList;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public List<PersonMovie> getPersonMovies() {
        return personMovies;
    }

    public void setPersonMovies(List<PersonMovie> personMovies) {
        this.personMovies = personMovies;
    }

    public List<Crew> getCrewList() {
        return crewList;
    }

    public void setCrewList(List<Crew> crewList) {
        this.crewList = crewList;
    }
}
