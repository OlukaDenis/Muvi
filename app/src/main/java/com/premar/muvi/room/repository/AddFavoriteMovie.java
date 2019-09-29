package com.premar.muvi.room.repository;

import android.os.AsyncTask;

import com.premar.muvi.model.Movie;
import com.premar.muvi.room.favorite_movies_db.FavoritesDoa;

public class AddFavoriteMovie extends AsyncTask<Movie, Void, Void> {
    private FavoritesDoa favoritesDoa;

    public AddFavoriteMovie(FavoritesDoa favoritesDoa){
        this.favoritesDoa = favoritesDoa;
    }
    @Override
    protected Void doInBackground(Movie... movies) {
        favoritesDoa.insertFavoriteMovie(movies[0]);
        return null;
    }
}
