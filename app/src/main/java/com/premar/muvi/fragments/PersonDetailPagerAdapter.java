package com.premar.muvi.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.premar.muvi.fragments.person_detail_fragments.PersonInfoFragment;
import com.premar.muvi.fragments.person_detail_fragments.PersonMoviesFragment;

public class PersonDetailPagerAdapter extends FragmentPagerAdapter {
    public static final int tabCount = 2;
    private String[] pageTitles;

    public PersonDetailPagerAdapter(FragmentManager fragmentManager){
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
                return new PersonInfoFragment();
            case 1:
                return new PersonMoviesFragment();
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
