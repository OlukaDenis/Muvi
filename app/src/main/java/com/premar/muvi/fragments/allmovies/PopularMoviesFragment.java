package com.premar.muvi.fragments.allmovies;


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
import com.premar.muvi.commonUtils.PaginationScrollListener;
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
public class PopularMoviesFragment extends Fragment {
    private RecyclerView popular_recycleview;
    private ApiService apiService;
    private static final String TAG = MainActivity.class.getSimpleName();
    private PaginationScrollListener paginationScrollListener;
    GridLayoutManager gridLayoutManager;
    public static int totalPages;
    private List<Movie> movieList;
    private MovieHomeAdapter adapter;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        popular_recycleview = view.findViewById(R.id.recycler_view_all_popular);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanCount(3);
        popular_recycleview.setLayoutManager(gridLayoutManager);
        popular_recycleview.setHasFixedSize(true);

        paginationScrollListener = new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page +1) < totalPages){
                    loadMoreMovies(page +1);
                }
            }
        };
        popular_recycleview.addOnScrollListener(paginationScrollListener);

        apiService = ApiUtils.getApiService();

        getMovies();
        return view;
    }



    private void getMovies() {
        apiService.getPopularMovies(API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                MovieResponse movies = response.body();

                totalPages = movies.getTotalPages();

                movieList = movies.getResults();
                adapter = new MovieHomeAdapter(movieList, R.layout.layout_movies, getContext());
                popular_recycleview.setAdapter(adapter);
                Log.i(TAG, "Number of trending movies received:" + movieList.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t);
            }
        });
    }

    private void loadMoreMovies(int pages) {
        apiService.getPopularMovies(API_KEY, pages).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()){
                    if (response.body() != null){
                        MovieResponse movies = response.body();
                        if (pages == 1){
                            movieList = movies.getResults();
                            totalPages = movies.getTotalPages();
                            popular_recycleview.setAdapter(adapter);
                        }
                        else {
                            List<Movie> listMovies = movies.getResults();
                            for (Movie movie : listMovies){
                                movieList.add(movie);
                                adapter.notifyItemInserted(movieList.size() - 1);
                            }
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t);
            }
        });
    }
}