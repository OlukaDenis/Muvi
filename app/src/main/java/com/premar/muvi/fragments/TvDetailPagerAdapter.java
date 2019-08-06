package com.premar.muvi.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.premar.muvi.fragments.tv_fragments.TvCastFragment;
import com.premar.muvi.fragments.tv_fragments.TvInfoFragment;

public class TvDetailPagerAdapter extends FragmentPagerAdapter {
    public static final int tabCount = 2;
    private String[] pageTitles;

    public TvDetailPagerAdapter(FragmentManager fragmentManager){
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
                return new TvInfoFragment();
            case 1:
                return new TvCastFragment();
            case 2:
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
