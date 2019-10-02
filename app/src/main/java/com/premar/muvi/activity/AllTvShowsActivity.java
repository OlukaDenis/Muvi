package com.premar.muvi.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.premar.muvi.R;
import com.premar.muvi.fragments.AllMoviesPagerAdapter;
import com.premar.muvi.fragments.AllTvPagerAdapter;

public class AllTvShowsActivity extends AppCompatActivity {
    private static String TAG = AllTvShowsActivity.class.getSimpleName();
    private AllTvPagerAdapter tvAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tv_shows);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Tv Shows");

        // To remove the shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        tvAdapter = new AllTvPagerAdapter(getSupportFragmentManager());
        tvAdapter.setPageTitles(initPagesTitles());

        viewPager = findViewById(R.id.allshows_viewpager);
        tabLayout = findViewById(R.id.allshows_tablayout);

        viewPager.setAdapter(tvAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private String[] initPagesTitles() {
        String[] pageTitles = new String[AllTvPagerAdapter.tabCount];
        pageTitles[0] = "Trending";
        pageTitles[1] = "Airing Today";
        pageTitles[2] = "Popular";
        pageTitles[3] = "Now Playing";
        pageTitles[4] = "Top Rated";

        return pageTitles;
    }
}
