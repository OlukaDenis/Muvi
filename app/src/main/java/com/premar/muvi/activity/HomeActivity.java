package com.premar.muvi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.adapter.ImageAdapter;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.adapter.PersonAdapter;
import com.premar.muvi.adapter.TvAdapter;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;
import com.premar.muvi.model.images.Backdrops;
import com.premar.muvi.model.images.ImageResponse;
import com.premar.muvi.model.people.Person;
import com.premar.muvi.model.people.PersonResponse;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.tv.Tv;
import com.premar.muvi.model.tv.TvResponse;
import com.premar.muvi.viewpagers.AllMoviesPagerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerview_trendiing_shows = null;
    RecyclerView recyclerview_upcoming = null;
    RecyclerView recyclerView_trending = null;
    RecyclerView recyclerView_trending_people = null;

    private ProgressBar movieProgress, tvProgress, peopleProgress, playingProgress;

    private SwipeRefreshLayout refreshLayout;
    private static final String API_KEY = AppConstants.API_KEY;
    private ApiService apiService;
    MovieHomeAdapter movieHomeAdapter;
    private TextView more_trending;

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

        //init views
        recyclerview_trendiing_shows = findViewById(R.id.home_recyclerview_popular);
        recyclerview_upcoming = findViewById(R.id.home_recyclerview_upcoming);
        recyclerView_trending = findViewById(R.id.home_recyclerview_trending);
        recyclerView_trending_people = findViewById(R.id.home_recyclerview_trending_people);
        refreshLayout = findViewById(R.id.pull_to_refresh);
        more_trending = findViewById(R.id.more_trending_movies);
        movieProgress = findViewById(R.id.home_trending_movies_progressbar);
        tvProgress = findViewById(R.id.home_trending_tv_progressbar);
        peopleProgress = findViewById(R.id.trending_people_progressbar);
        playingProgress  = findViewById(R.id.playing_movies_progressbar);

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

        //trending people layout manager
        recyclerView_trending_people.setHasFixedSize(true);
        LinearLayoutManager trendingPeopleLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_trending_people.setLayoutManager(trendingPeopleLayoutManager);

        apiService = ApiUtils.getApiService();

        refreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                connectAndGetApiData();
                refreshLayout.setRefreshing(false);
            }, 2000);

        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        });
        connectAndGetApiData();
        moreTrendingMovies();
    }

    private void moreTrendingMovies() {
        more_trending.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AllMoviesActivity.class);
            startActivity(intent);
        });
    }


    //this method creates an instance of retrofit
    private void connectAndGetApiData() {

        apiService.getTrendingMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        List<Movie> movies = response.body().getResults();
                        recyclerView_trending.setAdapter(new MovieHomeAdapter(movies,
                                R.layout.layout_movies,
                                getApplicationContext()));
                        movieProgress.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Number of trending movies received:" + movies.size());
                    } else {
                        movieProgress.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        apiService.getTrendingShows(API_KEY).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        TvResponse tvShows = response.body();

                        List<Tv> tv = tvShows.getResults();
                        TvAdapter adapter = new TvAdapter(tv, R.layout.layout_movies, getApplicationContext());
                        recyclerview_trendiing_shows.setAdapter(adapter);

                        tvProgress.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Number of movies received:" + tv.size());
                    } else {
                        tvProgress.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

        apiService.getPlayingMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
               if (response.isSuccessful()){
                   if (response.body() != null){
                       List<Movie> movies = response.body().getResults();
                       recyclerview_upcoming.setAdapter(new MovieHomeAdapter(movies,
                               R.layout.layout_movies,
                               getApplicationContext()));
                       playingProgress.setVisibility(View.INVISIBLE);
                       Log.d(TAG, "Number of upcoming movies received:" + movies.size());
                   } else {
                       playingProgress.setVisibility(View.VISIBLE);
                   }
               }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString() );
            }
        });


        apiService.getTrendingPeople(API_KEY).enqueue(new Callback<PersonResponse>() {
            @Override
            public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        PersonResponse people = response.body();

                        List<Person> personList = people.getResults();
                        PersonAdapter adapter = new PersonAdapter(getApplicationContext(), personList, R.layout.layout_person);
                        recyclerView_trending_people.setAdapter(adapter);
                        peopleProgress.setVisibility(View.INVISIBLE);

                        Log.d(TAG, "Number of trending people received:" + personList.size());
                    } else {
                        peopleProgress.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<PersonResponse> call, Throwable t) {
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
