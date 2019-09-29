package com.premar.muvi.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.premar.muvi.model.Movie;
import com.premar.muvi.room.favorite_movies_db.FavoritesDatabase;
import com.premar.muvi.room.favorite_movies_db.FavoritesDoa;

import java.util.List;

public class FavoritesRepository {
    private FavoritesDoa favoritesDoa;

    public FavoritesRepository(Application application){
        FavoritesDatabase database = FavoritesDatabase.getInstance(application);
        favoritesDoa = database.getFavoritesDoa();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies(){
        return favoritesDoa.getAllFavoriteMovies();
    }

    public Movie getMovie(String id){
        return favoritesDoa.getMovie(id);
    }

    public void addMovie(Movie movie){
        new AddFavoriteMovie(favoritesDoa).execute(movie);
    }

    public void deleteMovie(Movie movie){
        new DeleteFavoriteMovie(favoritesDoa).execute(movie);
    }
}
