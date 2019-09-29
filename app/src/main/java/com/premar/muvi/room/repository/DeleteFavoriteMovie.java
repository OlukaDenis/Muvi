package com.premar.muvi.room.repository;

import android.os.AsyncTask;

import com.premar.muvi.model.Movie;
import com.premar.muvi.room.favorite_movies_db.FavoritesDoa;

public class DeleteFavoriteMovie extends AsyncTask<Movie, Void, Void> {
    private FavoritesDoa favoritesDoa;

    public DeleteFavoriteMovie(FavoritesDoa favoritesDoa){
        this.favoritesDoa = favoritesDoa;
    }
    @Override
    protected Void doInBackground(Movie... movies) {
        favoritesDoa.deleteFavoriteMovie(movies[0]);
        return null;
    }
}
