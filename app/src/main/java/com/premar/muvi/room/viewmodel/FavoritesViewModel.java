package com.premar.muvi.room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.premar.muvi.model.Movie;
import com.premar.muvi.room.repository.FavoritesRepository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {
    private FavoritesRepository favoritesRepository;
    private LiveData<List<Movie>> getAllFavMovies;
    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        favoritesRepository = new FavoritesRepository(application);
        getAllFavMovies = favoritesRepository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getAllMovies(){
        return getAllFavMovies;
    }

    public Movie getMovie(String id){
        return favoritesRepository.getMovie(id);
    }

    public void AddFavoriteMovie(Movie movie){
        favoritesRepository.addMovie(movie);
    }

    public void DeleteFavorite(Movie movie){
        favoritesRepository.deleteMovie(movie);
    }
}
