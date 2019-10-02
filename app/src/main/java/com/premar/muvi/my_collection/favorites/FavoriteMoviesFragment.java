package com.premar.muvi.my_collection.favorites;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.databinding.FragmentFavoriteMoviesBinding;
import com.premar.muvi.model.Movie;
import com.premar.muvi.room.viewmodel.FavoritesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment {
   private FragmentFavoriteMoviesBinding binding;
   private RecyclerView recyclerView;
   private NestedScrollView scrollView;
   private FavoritesViewModel viewModel;
   private ArrayList<Movie> moviesList;
   private MovieHomeAdapter adapter;
   private Context context;


    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_movies,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle("Favorites Movies");
        context = getContext();
        viewModel = ViewModelProviders.of(getActivity()).get(FavoritesViewModel.class);
        viewModel.getAllMovies().observe(getActivity(), movies -> {
            moviesList = (ArrayList<Movie>) movies;
            showRecyclerView();
        });
    }

    private void showRecyclerView() {
        recyclerView = binding.favoritesRecyclerview;
        adapter = new MovieHomeAdapter(context, moviesList);
        if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        }
        else{
        recyclerView.setLayoutManager((new GridLayoutManager(context,4)));}
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }
}
