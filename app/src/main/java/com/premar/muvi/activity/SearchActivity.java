package com.premar.muvi.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.premar.muvi.R;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.model.Movie;

import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecycleview;
    private GridLayoutManager layoutManager;
    MovieHomeAdapter adapter;
    private ArrayList<Movie> movieList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        String searchQuery = getIntent().getStringExtra("search");
        if (searchQuery!=null){
            Objects.requireNonNull(getSupportActionBar()).setTitle(searchQuery);
        }

        movieList= HomeActivity.movieList;

        adapter = new MovieHomeAdapter( getApplicationContext(), movieList);
        searchRecycleview = findViewById(R.id.search_results_recycleview);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);

        searchRecycleview.setLayoutManager(layoutManager);
        searchRecycleview.setAdapter(adapter);
        searchRecycleview.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

}
