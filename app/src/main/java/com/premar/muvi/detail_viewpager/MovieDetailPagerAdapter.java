package com.premar.muvi.detail_viewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.premar.muvi.detail_viewpager.fragment.CastFragment;
import com.premar.muvi.detail_viewpager.fragment.CommentsFragment;
import com.premar.muvi.detail_viewpager.fragment.InfoFragment;
import com.premar.muvi.detail_viewpager.fragment.ReviewsFragment;

public class MovieDetailPagerAdapter extends FragmentPagerAdapter {
    public static final int tabCount = 4;
    private String[] pageTitles;

    public MovieDetailPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InfoFragment();
            case 1:
                return new CastFragment();
            case 2:
                return new CommentsFragment();
            case 3:
                return new ReviewsFragment();
        }
        return null;

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
