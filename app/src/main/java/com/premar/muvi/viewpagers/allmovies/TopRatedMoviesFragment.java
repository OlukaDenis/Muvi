package com.premar.muvi.viewpagers.allmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.activity.MainActivity;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.constants.AppConstants.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedMoviesFragment extends Fragment {
    private RecyclerView toprated_recycleview;
    private ApiService apiService;
    private static final String TAG = MainActivity.class.getSimpleName();

    public TopRatedMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated_movies, container, false);

        toprated_recycleview = view.findViewById(R.id.recycler_view_all_top_rated);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        toprated_recycleview.setLayoutManager(layoutManager);
        toprated_recycleview.setHasFixedSize(true);

        apiService = ApiUtils.getApiService();

        getMovies();
        return view;

    }

    private void getMovies() {
        apiService.getTopRatedMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                MovieResponse movies = response.body();

                int pages = movies.getTotalPages();

                List<Movie> movieList = movies.getResults();
                toprated_recycleview.setAdapter(new MovieHomeAdapter(movieList,
                        R.layout.layout_movies,
                        getContext()));
                Log.i(TAG, "Number of trending movies received:" + movieList.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t);
            }
        });
    }

}
