package com.premar.muvi.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.premar.muvi.R;
import com.premar.muvi.adapter.MovieAdapter;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;
import com.premar.muvi.rest.ApiService;
import com.premar.muvi.rest.ApiUtils;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //private static final String BASE_URL = "http://api.themoviedb.org/3/";
    RecyclerView recyclerView = null;
    private static final String API_KEY = "617e3f93f561c0a9a2b934055ba31e6a";
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Muvi");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiUtils.getApiService();
        connectAndGetApiData();
    }

    //this method creates an instance of retrofit
    private void connectAndGetApiData() {
        apiService.getPopularMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies,
                        R.layout.list_item_movie,
                        getApplicationContext()));
                Log.d(TAG, "Number of movies received:" + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }


}
