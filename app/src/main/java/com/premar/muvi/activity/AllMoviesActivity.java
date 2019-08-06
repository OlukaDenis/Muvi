package com.premar.muvi.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.premar.muvi.R;
import com.premar.muvi.fragments.AllMoviesPagerAdapter;

public class AllMoviesActivity extends AppCompatActivity {
    private static String TAG = MovieDetailActivity.class.getSimpleName();
    private AllMoviesPagerAdapter moviePager;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Movies");

        // To remove the shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        moviePager = new AllMoviesPagerAdapter(getSupportFragmentManager());
        moviePager.setPageTitles(initPagesTitles());

        viewPager = findViewById(R.id.allmovies_viewpager);
        tabLayout = findViewById(R.id.allmovies_tablayout);

        viewPager.setAdapter(moviePager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private String[] initPagesTitles() {
        String[] pageTitles = new String[AllMoviesPagerAdapter.tabCount];
        pageTitles[0] = "Trending";
        pageTitles[1] = "Popular";
        pageTitles[2] = "Now Playing";
        pageTitles[3] = "Top Rated";
        pageTitles[4] = "Upcoming";
        return pageTitles;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
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
}
