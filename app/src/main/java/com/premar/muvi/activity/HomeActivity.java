package com.premar.muvi.activity;

import android.content.Intent;
import android.database.MatrixCursor;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.util.Log;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.premar.muvi.R;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.adapter.PersonAdapter;
import com.premar.muvi.adapter.SearchAdapter;
import com.premar.muvi.adapter.TvAdapter;
import com.premar.muvi.databinding.ActivityHomeBinding;
import com.premar.muvi.fragments.SearchFragment;
import com.premar.muvi.room.viewmodel.FavoritesViewModel;
import com.premar.muvi.utils.AppConstants;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;
import com.premar.muvi.model.people.Person;
import com.premar.muvi.model.people.PersonResponse;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.search.Search;
import com.premar.muvi.model.search.SearchResponse;
import com.premar.muvi.model.tv.Tv;
import com.premar.muvi.model.tv.TvResponse;
import com.premar.muvi.utils.SearchToMovie;

import java.util.ArrayList;
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
    private LinearLayout searchLayout;

    private ProgressBar movieProgress, tvProgress, peopleProgress, playingProgress;

    private SwipeRefreshLayout refreshLayout;
    private static final String API_KEY = AppConstants.API_KEY;
    private ApiService apiService;
    MovieHomeAdapter movieHomeAdapter;
    private TextView more_trending, more_upcoming;

    private ArrayList<Search> searchList;
    private ArrayList<Search> mSearches;
    public static ArrayList<Movie> movieList=new ArrayList<>();
    public static ArrayList<Movie> moviesearch;
    public static SearchFragment searchFragment = new SearchFragment();
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    public static String queryM;
    private MatrixCursor cursor;



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
        more_upcoming = findViewById(R.id.tv_more_upcoming_movies);
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
        moreUpcoming();
    }

    private void moreTrendingMovies() {
        more_trending.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AllMoviesActivity.class);
            startActivity(intent);
        });
    }

    private void moreUpcoming() {
        more_upcoming.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AllMoviesActivity.class);
            startActivity(intent);
        });
    }


    //this method creates an instance of retrofit
    private void connectAndGetApiData() {

        apiService.getTrendingMovies(API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        List<Movie> movies = response.body().getResults();
                        recyclerView_trending.setAdapter(new MovieHomeAdapter( getApplicationContext(),movies));
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

        apiService.getPlayingMovies(API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
               if (response.isSuccessful()){
                   if (response.body() != null){
                       List<Movie> movies = response.body().getResults();
                       recyclerview_upcoming.setAdapter(new MovieHomeAdapter(getApplicationContext(), movies));
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

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    searchView.setQuery("",false);
                    searchView.clearFocus();
                    searchView.setIconified(false);

                    apiService.searchMovies(API_KEY, false, query).enqueue(new Callback<SearchResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                            if (response.isSuccessful() && response.body().getResults()!=null){
                                SearchResponse searches = response.body();
                                searchList = (ArrayList<Search>) searches.getResults();
                                SearchToMovie searchToMovie = new SearchToMovie(searchList);
                                movieList = searchToMovie.getMovies();

                                Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                                searchIntent.putExtra("search", query);
                                startActivity(searchIntent);
                                /*
                                if(fragmentManager.getFragments().isEmpty()) {
                                   // refreshLayout.setVisibility(View.GONE);
                                    searchLayout.setVisibility(View.VISIBLE);
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.add(R.id.frame_layout, searchFragment).commitAllowingStateLoss();
                                } else {
                                    //refreshLayout.setVisibility(View.GONE);
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.replace(R.id.frame_layout, searchFragment).commitAllowingStateLoss();
                                }*/

                            }


                        }

                        @Override
                        public void onFailure(Call<SearchResponse> call, Throwable t) {

                        }
                    });

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryM = newText;

                apiService.searchMovies(API_KEY, false, queryM).enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body().getResults()!=null) {
                            SearchResponse searches = response.body();
                            mSearches = (ArrayList<Search>) searches.getResults();
                            SearchToMovie searchToMovie = new SearchToMovie(mSearches);
                            moviesearch = searchToMovie.getMovies();

                            String a[] = new String[moviesearch.size()];
                            for (int i = 0; i < a.length; i++) {
                                a[i] = moviesearch.get(i).getTitle();
                            }
                            ArrayAdapter<String> Adapter = new ArrayAdapter<String>(HomeActivity.this, R.layout.layout_search_list, a);
                            String[] columnNames = {"_id", "text"};
                            cursor = new MatrixCursor(columnNames);
                            String[] temp = new String[2];
                            int id = 0;
                            for (String item : a) {
                                temp[0] = Integer.toString(id++);
                                temp[1] = item;
                                cursor.addRow(temp);
                            }

                            SearchAdapter searchAdapter=new SearchAdapter(HomeActivity.this, cursor,true,searchView,moviesearch);
                            searchView.setSuggestionsAdapter(searchAdapter);

                        }

                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                //finish();
                getFragmentManager().popBackStack();
                //searchLayout.setVisibility(View.GONE);
            } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
       // searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        search(searchView);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_search) {

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
