package com.premar.muvi.room.favorite_movies_db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.premar.muvi.model.Movie;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoritesDoa {

    @Insert(onConflict = REPLACE)
    void insertFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("select * from favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("select * from favorite_movies where title==:title")
    Movie getMovie(String title);
}
