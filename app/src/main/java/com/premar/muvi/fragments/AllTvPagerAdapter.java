package com.premar.muvi.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.premar.muvi.fragments.alltvshows.NowPlayingShowsFragment;
import com.premar.muvi.fragments.alltvshows.PopularShowsFragment;
import com.premar.muvi.fragments.alltvshows.TopRatedShowsFragment;
import com.premar.muvi.fragments.alltvshows.TrendingShowsFragment;
import com.premar.muvi.fragments.alltvshows.AiringTodayShowsFragment;

public class AllTvPagerAdapter extends FragmentPagerAdapter {
    public static final int tabCount = 5;
    private String[] pageTitles;

    public AllTvPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new TrendingShowsFragment();
            case 1:
                return new AiringTodayShowsFragment();
            case 2:
                return new PopularShowsFragment();
            case 3:
                return new NowPlayingShowsFragment();
            case 4:
                return new TopRatedShowsFragment();

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
