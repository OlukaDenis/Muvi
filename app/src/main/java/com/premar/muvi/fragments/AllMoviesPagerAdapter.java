package com.premar.muvi.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.premar.muvi.fragments.allmovies.NowPlayingMoviesFragment;
import com.premar.muvi.fragments.allmovies.PopularMoviesFragment;
import com.premar.muvi.fragments.allmovies.TopRatedMoviesFragment;
import com.premar.muvi.fragments.allmovies.TrendingMoviesFragment;
import com.premar.muvi.fragments.allmovies.UpcomingMoviesFragment;

public class AllMoviesPagerAdapter extends FragmentPagerAdapter {
    public static final int tabCount = 5;
    private String[] pageTitles;

    public AllMoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new TrendingMoviesFragment();
            case 1:
                return new PopularMoviesFragment();
            case 2:
                return new NowPlayingMoviesFragment();
            case 3:
                return new TopRatedMoviesFragment();
            case 4:
                return new UpcomingMoviesFragment();
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
