package com.premar.muvi.threading;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;
import com.premar.muvi.room.favorite_movies_db.MuviDatabase;
import com.premar.muvi.room.viewmodel.FavoritesViewModel;
import com.premar.muvi.utils.AppConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrieveMoviesRunnable implements Runnable {
    private FavoritesViewModel viewModel;
    private Handler mMainThreadHandler;

    public RetrieveMoviesRunnable(Context context, Handler mMainThreadHandler) {
        this.mMainThreadHandler = mMainThreadHandler;
    }

    @Override
    public void run() {

    }
}
