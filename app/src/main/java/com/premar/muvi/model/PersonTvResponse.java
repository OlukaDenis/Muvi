package com.premar.muvi.model;

import com.google.gson.annotations.SerializedName;
import com.premar.muvi.model.credits.Crew;

import java.util.List;

public class PersonTvResponse {
    private int movie_id;

    @SerializedName("cast")
    private List<PersonTv> personShows;

    @SerializedName("crew")
    private List<Crew> crewList;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public List<PersonTv> getPersonShows() {
        return personShows;
    }

    public void setPersonMovies(List<PersonTv> shows) {
        this.personShows = shows;
    }

    public List<Crew> getCrewList() {
        return crewList;
    }

    public void setCrewList(List<Crew> crewList) {
        this.crewList = crewList;
    }
}
