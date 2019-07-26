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
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewpagers.PersonDetailPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PersonDetailActivity extends AppCompatActivity {
    private static String TAG = PersonDetailActivity.class.getSimpleName();
    private PersonDetailPagerAdapter personPager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Bundle bundle;
    private int personId;
    private ApiService apiService;

    private TextView name;
    private ImageView mPoster, mBackdrop;
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_detail_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        personId = MovieCache.personId;
        apiService = ApiUtils.getApiService();

        viewPager = findViewById(R.id.person_detail_viewpager);
        tabLayout = findViewById(R.id.person_detail_tabs);
        name = findViewById(R.id.person_detail_name);
        mPoster = findViewById(R.id.person_detail_image);
        mBackdrop = findViewById(R.id.person_detail_backdrop);
        scrollView = findViewById(R.id.person_nested_scroll);

        personPager = new PersonDetailPagerAdapter(getSupportFragmentManager());
        personPager.setPageTitles(initPagesTitles());
        viewPager.setAdapter(personPager);
        tabLayout.setupWithViewPager(viewPager);

        scrollView.setFillViewport(true);

        // To remove the shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }


        bundle = getIntent().getExtras();
        if (bundle!=null){
            String person_name = (String) bundle.get("name");
            String backdrop = (String) bundle.get("backdrop");
            String poster = (String) bundle.get("poster");
            setTitle(person_name);

            populateDetails(person_name, poster, backdrop);
        }

    }

    private void populateDetails(String person_name, String poster, String backdrop) {
        name.setText(person_name);

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
    }

    private String[] initPagesTitles() {
        String[] pageTitles = new String[PersonDetailPagerAdapter.tabCount];
        pageTitles[0] = "Info";
        pageTitles[1] = "Movies";
        return pageTitles;

    }
}