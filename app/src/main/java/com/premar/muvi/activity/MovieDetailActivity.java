package com.premar.muvi.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.adapter.CastAdapter;
import com.premar.muvi.adapter.GenreAdapter;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.detail_viewpager.MovieDetailPagerAdapter;
import com.premar.muvi.model.Genre;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.credits.Cast;
import com.premar.muvi.model.credits.Credits;
import com.premar.muvi.rest.ApiService;
import com.premar.muvi.rest.ApiUtils;
import com.premar.muvi.temporary_storage.MovieCache;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.constants.AppConstants.API_KEY;

public class MovieDetailActivity extends AppCompatActivity {
    private static String TAG = MovieDetailActivity.class.getSimpleName();
   private MovieDetailPagerAdapter moviePager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Bundle bundle;
    private int movieId;
    private ApiService apiService;

    private TextView title, release_date, duration, votes;
    private ImageView mPoster, mBackdrop;
    NestedScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
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
        release_date = findViewById(R.id.detail_release_date);
        duration = findViewById(R.id.detail_duration);
        votes = findViewById(R.id.detail_votes);
        mPoster = findViewById(R.id.detail_small_poster);
        mBackdrop = findViewById(R.id.detail_backdrop);
        scrollView = (NestedScrollView) findViewById(R.id.nested_scroll);


        scrollView.setFillViewport(true);

        // To remove the shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        bundle = getIntent().getExtras();
        if (bundle!=null){
            String movie_title = (String) bundle.get("movie_title");
            String date = (String) bundle.get("movie_date");
            String poster = (String) bundle.get("movie_poster");
            String backdrop = (String) bundle.get("movie_backdrop");
            int movie_duration = (int) bundle.get("movie_duration");
            int movie_votes = (int) bundle.get("movie_votes");
            setTitle(movie_title);

            populateDetails(movie_title, date, poster, backdrop);
        }

        fetchMovieDetails();

    }

    private void populateDetails(String movie_title,
                                 String date,
                                 String poster,
                                 String backdrop) {

        title.setText(movie_title);
        release_date.setText(date);

        Picasso.with(this)
                .load(poster)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(mPoster);

        Picasso.with(this)
                .load(backdrop)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(mBackdrop);

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
        apiService.getMovie(movieId, API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, Response<Movie> response) {
                assert response.body() != null;

                Movie movieDetails = response.body();

                String mDuration = String.valueOf(movieDetails.getRuntime());
                duration.setText(mDuration + getString(R.string.minutes));

                votes.setText(String.valueOf(movieDetails.getVoteCount()));

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

    }
}
