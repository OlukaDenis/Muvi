package com.premar.muvi.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.detail_viewpager.MovieDetailPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {
   private MovieDetailPagerAdapter moviePager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Bundle bundle;
    private int movieId = -1;

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

            populateDetails(movie_title, date, poster, backdrop, movie_duration, movie_votes);
        }



    }

    private void populateDetails(String movie_title,
                                 String date,
                                 String poster,
                                 String backdrop,
                                 int movie_duration,
                                 int movie_votes) {

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

        String mDuration = String.valueOf(movie_votes);
        duration.setText(String.valueOf(movie_duration ));

        votes.setText(String.valueOf(movie_duration));
    }


    private String[] initPagesTitles() {
        String[] pageTitles = new String[MovieDetailPagerAdapter.tabCount];
        pageTitles[0] = "Info";
        pageTitles[1] = "Cast";
        pageTitles[2] = "Comments";
        pageTitles[3] = "Reviews";
        return pageTitles;
    }
}
