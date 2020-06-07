package com.premar.muvi.fragments.allmovies;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
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

import static com.premar.muvi.utils.AppConstants.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMoviesFragment extends Fragment {
    private ShimmerRecyclerView upcoming_recycleview;
    private ApiService apiService;
    private static final String TAG = MainActivity.class.getSimpleName();
    private PaginationScrollListener paginationScrollListener;
    GridLayoutManager gridLayoutManager;
    public static int totalPages;
    private List<Movie> movieList;
    private MovieHomeAdapter adapter;

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_movies, container, false);

        upcoming_recycleview = view.findViewById(R.id.recycler_view_all_upcoming);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanCount(3);
        upcoming_recycleview.setLayoutManager(gridLayoutManager);
        upcoming_recycleview.setHasFixedSize(true);

        paginationScrollListener = new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page +1) < totalPages){
                    loadMoreMovies(page +1);
                }
            }
        };
        upcoming_recycleview.addOnScrollListener(paginationScrollListener);

        apiService = ApiUtils.getApiService();

        getMovies();
        return view;

    }

    private void getMovies() {
        apiService.getUpcomingMovies(API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                MovieResponse movies = response.body();

                totalPages = movies.getTotalPages();

                movieList = movies.getResults();
                adapter = new MovieHomeAdapter( getContext(), movieList);
                upcoming_recycleview.setAdapter(adapter);
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
                            upcoming_recycleview.setAdapter(adapter);
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
