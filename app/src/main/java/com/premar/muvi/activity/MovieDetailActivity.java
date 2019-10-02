package com.premar.muvi.activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.premar.muvi.R;
import com.premar.muvi.databinding.ActivityMovieDetailBinding;
import com.premar.muvi.room.viewmodel.FavoritesViewModel;
import com.premar.muvi.utils.AppConstants;
import com.premar.muvi.fragments.MovieDetailPagerAdapter;
import com.premar.muvi.model.Movie;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.temporary_storage.MovieCache;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;

import java.text.ParseException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;
import static com.premar.muvi.utils.AppConstants.BACKDROP_URL_BASE_PATH;
import static com.premar.muvi.utils.AppConstants.IMAGE_URL_BASE_PATH;

public class MovieDetailActivity extends AppCompatActivity {
    private static String TAG = MovieDetailActivity.class.getSimpleName();
    private MovieDetailPagerAdapter moviePager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public  Movie selectedMovie;
    Bundle bundle;
    private int movieId;
    private ApiService apiService;

    private TextView title, release_date, duration, votes, year;
    private ImageView mPoster, mBackdrop;
    private NestedScrollView scrollView;
    private SparkButton btnAddRemoveFavorite;

    private ActivityMovieDetailBinding binding;
    private FavoritesViewModel viewModel;

    private boolean fav_switch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);


        binding = DataBindingUtil.setContentView(MovieDetailActivity.this, R.layout.activity_movie_detail);
        viewModel = ViewModelProviders.of(MovieDetailActivity.this).get(FavoritesViewModel.class);
        setSupportActionBar(binding.detailToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        movieId = MovieCache.movieId;
        apiService = ApiUtils.getApiService();

        moviePager = new MovieDetailPagerAdapter(getSupportFragmentManager());
        moviePager.setPageTitles(initPagesTitles());

        viewPager = findViewById(R.id.detail_viewpager);
        viewPager.setAdapter(moviePager);

        tabLayout = findViewById(R.id.detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        title = findViewById(R.id.detail_title);
        //release_date = findViewById(R.id.detail_release_date);
        duration = findViewById(R.id.detail_duration);
        votes = findViewById(R.id.detail_votes);
        mPoster = findViewById(R.id.detail_small_poster);
        mBackdrop = findViewById(R.id.detail_backdrop);
        scrollView = (NestedScrollView) findViewById(R.id.nested_scroll);
        year = findViewById(R.id.movie_release_year);


        scrollView.setFillViewport(true);

        //Receive intent
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        if (movie == null){
            movie = new Movie();
        }
        this.selectedMovie = movie;

        if(viewModel.getMovie(selectedMovie.getTitle()) != null){
            fav_switch = true;
            binding.favImage.setImageResource(R.drawable.ic_favorite_orange);
        } else {
            fav_switch = false;
            binding.favImage.setImageResource(R.drawable.ic_favorite);
        }

        binding.setMovie(selectedMovie);

        binding.favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fav_switch){
                    viewModel.DeleteFavorite(selectedMovie);
                    Toast.makeText(MovieDetailActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    fav_switch = false;
                    binding.favImage.setImageResource(R.drawable.ic_favorite);
                } else {
                    viewModel.AddFavoriteMovie(selectedMovie);
                    Toast.makeText(MovieDetailActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    fav_switch = true;
                    binding.favImage.setImageResource(R.drawable.ic_favorite_orange);
                }
            }
        });


        String movie_title = selectedMovie.getTitle();
        String date = selectedMovie.getReleaseDate();
        String poster = selectedMovie.getPosterPath();
        String backdrop = selectedMovie.getBackdropPath();
        int mduration = selectedMovie.getRuntime();
        int mvotes = selectedMovie.getVoteCount();
        try {
            populateDetails(movie_title, date, poster, backdrop, mduration, mvotes);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // To remove the shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        fetchMovieDetails();

    }

    private void populateDetails(String movie_title,
                                 String date,
                                 String poster,
                                 String backdrop,
                                 int mduration,
                                 int mvotes) throws ParseException {

        //title.setText(movie_title);
        setTitle(movie_title);

        if (date == null){
            year.setVisibility(View.GONE);
        } else {
            String release_year = AppConstants.getYear(date);
            Log.i(TAG, "Release year: " + release_year);
            year.setText(release_year);
        }

        String image_url = IMAGE_URL_BASE_PATH + poster;
        String backdrop_url = BACKDROP_URL_BASE_PATH + backdrop;

        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(mPoster);

        Picasso.get()
                .load(backdrop_url)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(mBackdrop);


        votes.setText(String.valueOf(mvotes));

    }


    private String[] initPagesTitles() {
        String[] pageTitles = new String[MovieDetailPagerAdapter.tabCount];
        pageTitles[0] = "Info";
        pageTitles[1] = "Cast";
        pageTitles[2] = "Comments";
        pageTitles[3] = "Reviews";
        return pageTitles;

    }

    private void fetchMovieDetails() {
        apiService.getMovie(selectedMovie.getId(), API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Movie tvDetails = response.body();
                        int mDuration = tvDetails.getRuntime();
                        String hours = AppConstants.formatHoursAndMinutes(mDuration);
                        Log.i(TAG, "Movie duration: " + hours);
                        duration.setText(hours);
                    }
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                Log.e(TAG, "API error: " + throwable.toString());
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        //Receive intent
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        if (movie == null){
            movie = new Movie();
        }
        this.selectedMovie = movie;
        super.onResume();
    }
}
