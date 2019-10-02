package com.premar.muvi.my_collection.favorites;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.premar.muvi.fragments.allmovies.NowPlayingMoviesFragment;
import com.premar.muvi.fragments.allmovies.PopularMoviesFragment;
import com.premar.muvi.fragments.allmovies.TopRatedMoviesFragment;
import com.premar.muvi.fragments.allmovies.TrendingMoviesFragment;
import com.premar.muvi.fragments.allmovies.UpcomingMoviesFragment;

public class FavoritesPagerAdapter extends FragmentPagerAdapter {
    public static final int tabCount = 2;
    private String[] pageTitles;

    public FavoritesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FavoriteMoviesFragment();
            case 1:
                return new FavoriteTvFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }


    public void setPageTitles(String[] pageTitles) {
        this.pageTitles = pageTitles;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position < tabCount) {
            return pageTitles[position];
        }
        return null;
    }
}
