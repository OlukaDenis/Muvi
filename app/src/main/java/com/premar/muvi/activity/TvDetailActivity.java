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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.premar.muvi.R;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.tv.Tv;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.fragments.TvDetailPagerAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.constants.AppConstants.API_KEY;

public class TvDetailActivity extends AppCompatActivity {
    private static String TAG = MovieDetailActivity.class.getSimpleName();
    private TvDetailPagerAdapter moviePager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Bundle bundle;
    private int tvId;
    private ApiService apiService;

    private TextView title, release_date, duration, votes, year;
    private ImageView mPoster, mBackdrop;
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tv_detail_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        tvId = MovieCache.getTvId();
        Toast.makeText(this, String.valueOf(tvId), Toast.LENGTH_SHORT).show();
        apiService = ApiUtils.getApiService();

        moviePager = new TvDetailPagerAdapter(getSupportFragmentManager());
        moviePager.setPageTitles(initPagesTitles());

        viewPager = findViewById(R.id.tv_detail_viewpager);
        viewPager.setAdapter(moviePager);

        tabLayout = findViewById(R.id.tv_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        title = findViewById(R.id.tv_detail_title);
        //release_date = findViewById(R.id.detail_release_date);
        duration = findViewById(R.id.tv_detail_duration);
        votes = findViewById(R.id.tv_detail_votes);
        mPoster = findViewById(R.id.tv_detail_poster);
        mBackdrop = findViewById(R.id.tv_detail_backdrop);
        scrollView = (NestedScrollView) findViewById(R.id.tv_detail_nested_scroll);
        year = findViewById(R.id.tv_release_year);


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
            setTitle(movie_title);

            try {
                populateDetails(movie_title, date, poster, backdrop);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        fetchTvDetails();
    }

    private void populateDetails(String movie_title,
                                 String date,
                                 String poster,
                                 String backdrop) throws ParseException {

        title.setText(movie_title);
        // release_date.setText(date);

        String release_year = AppConstants.getYear(date);
        Log.i(TAG, "Release year: " + release_year);
        year.setText(release_year);

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
        String[] pageTitles = new String[TvDetailPagerAdapter.tabCount];
        pageTitles[0] = "Info";
        pageTitles[1] = "Cast";
        return pageTitles;

    }

    private void fetchTvDetails() {
        apiService.getTvShow(tvId, API_KEY).enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(@NonNull Call<Tv> call, Response<Tv> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Tv tvDetails = response.body();

                        List<Integer> runtime = tvDetails.getEpisodeRuntime();
                        Integer mDuration = runtime.get(0);
                        Log.i(TAG, "Runtimes: " + runtime.size());
                        String hours = AppConstants.formatHoursAndMinutes(mDuration);
                        Log.i(TAG, "Movie duration: " + hours);
                        duration.setText(hours);


                        votes.setText(String.valueOf(tvDetails.getVote_count()));
                    }
                }

            }

            @Override
            public void onFailure(Call<Tv> call, Throwable throwable) {
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



}
