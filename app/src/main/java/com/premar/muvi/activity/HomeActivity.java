package com.premar.muvi.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.premar.muvi.R;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;
import com.premar.muvi.rest.ApiService;
import com.premar.muvi.rest.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    //private static final String BASE_URL = "http://api.themoviedb.org/3/";
    RecyclerView recyclerview_trendiing_shows = null;
    RecyclerView recyclerview_upcoming = null;
    RecyclerView recyclerView_trending = null;
    private static final String API_KEY = AppConstants.API_KEY;
    private ApiService apiService;
    MovieHomeAdapter movieHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerview_trendiing_shows = findViewById(R.id.home_recyclerview_popular);
        recyclerview_upcoming = findViewById(R.id.home_recyclerview_upcoming);
        recyclerView_trending = findViewById(R.id.home_recyclerview_trending);
        //trending shows layout manager
        recyclerview_trendiing_shows.setHasFixedSize(true);
        LinearLayoutManager popularLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview_trendiing_shows.setLayoutManager(popularLayoutManager);
        //upcoming layout manager
        LinearLayoutManager upcomingLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview_upcoming.setLayoutManager(upcomingLayoutManager);
        //upcoming layout manager
        LinearLayoutManager trendingLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_trending.setLayoutManager(trendingLayoutManager);

        apiService = ApiUtils.getApiService();
        connectAndGetApiData();
    }


    //this method creates an instance of retrofit
    private void connectAndGetApiData() {
        apiService.getPopularShows(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                List<Movie> movies = response.body().getResults();
                movieHomeAdapter = new MovieHomeAdapter(movies, R.layout.layout_movies, getApplicationContext());
                recyclerview_trendiing_shows.setAdapter(movieHomeAdapter);
                Log.d(TAG, "Number of movies received:" + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

        apiService.getUpcomingMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                List<Movie> movies = response.body().getResults();
                recyclerview_upcoming.setAdapter(new MovieHomeAdapter(movies,
                        R.layout.layout_movies,
                        getApplicationContext()));
                Log.d(TAG, "Number of upcoming movies received:" + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        apiService.getPopularMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                List<Movie> movies = response.body().getResults();
                recyclerView_trending.setAdapter(new MovieHomeAdapter(movies,
                        R.layout.layout_movies,
                        getApplicationContext()));
                Log.d(TAG, "Number of trending movies received:" + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
