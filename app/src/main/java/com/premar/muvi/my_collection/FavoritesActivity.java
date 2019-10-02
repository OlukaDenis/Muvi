package com.premar.muvi.my_collection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.premar.muvi.R;
import com.premar.muvi.activity.MovieDetailActivity;
import com.premar.muvi.fragments.AllMoviesPagerAdapter;
import com.premar.muvi.my_collection.favorites.FavoritesPagerAdapter;

public class FavoritesActivity extends AppCompatActivity {
    private static String TAG = FavoritesActivity.class.getSimpleName();
    private FavoritesPagerAdapter favoritesPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Favorites");

        // To remove the shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        favoritesPagerAdapter = new FavoritesPagerAdapter(getSupportFragmentManager());
        favoritesPagerAdapter.setPageTitles(initPagesTitles());

        viewPager = findViewById(R.id.favorites_viewpager);
        tabLayout = findViewById(R.id.favorites_tablayout);

        viewPager.setAdapter(favoritesPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private String[] initPagesTitles() {
        String[] pageTitles = new String[FavoritesPagerAdapter.tabCount];
        pageTitles[0] = "Movies";
        pageTitles[1] = "Tv Shows";
        return pageTitles;

    }
}
