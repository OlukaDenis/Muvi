package com.premar.muvi.utils;

import com.premar.muvi.model.Movie;
import com.premar.muvi.model.search.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchToMovie {
    private ArrayList<Search> searches;
    private ArrayList<Movie> movies;

    public SearchToMovie(ArrayList<Search> searches) {
        this.searches=searches;
        movies=new ArrayList<>(searches.size());
    }

    public ArrayList<Movie> getMovies()
    {
        for(int i=0;i<searches.size();i++)
        {
            movies.add(new Movie());
            movies.get(i).setTitle(searches.get(i).getTitle());
            movies.get(i).setAdult(searches.get(i).getAdult());
            movies.get(i).setBackdropPath((String)searches.get(i).getBackdropPath());
            movies.get(i).setPopularity(searches.get(i).getPopularity());
            movies.get(i).setId(searches.get(i).getId());
            movies.get(i).setOriginalLanguage(searches.get(i).getOriginalLanguage());
            movies.get(i).setOriginalTitle(searches.get(i).getOriginalTitle());
            movies.get(i).setOverview(searches.get(i).getOverview());
            movies.get(i).setPosterPath(searches.get(i).getPosterPath());
            movies.get(i).setReleaseDate(searches.get(i).getReleaseDate());
            movies.get(i).setVideo(searches.get(i).getVideo());
            movies.get(i).setVoteAverage(searches.get(i).getVoteAverage());
            movies.get(i).setVoteCount(searches.get(i).getVoteCount());
        }
        return movies;
    }
}
