package com.premar.muvi.fragments.allmovies;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

public class TrendingMoviesFragment extends Fragment {
    private RecyclerView trending_recycleview;
    private ApiService apiService;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private PaginationScrollListener paginationScrollListener;
    GridLayoutManager gridLayoutManager;
    private MovieHomeAdapter adapter;
    private List<Movie> movieList;

    public static int totalPages;
    private int currentPage = 1;

    public TrendingMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending_movies, container, false);

        trending_recycleview = view.findViewById(R.id.recycler_view_all_trending);
        progressBar = view.findViewById(R.id.progressbar_trending);
        refreshLayout = view.findViewById(R.id.trending_refresh_layout);

        apiService = ApiUtils.getApiService();

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanCount(3);
        trending_recycleview.setLayoutManager(gridLayoutManager);


        paginationScrollListener = new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page +1) < totalPages){
                    loadMoreMovies(page +1);
                }
            }
        };
        trending_recycleview.addOnScrollListener(paginationScrollListener);

        getMovies();
        refreshLayout();
        return view;

    }

    private void refreshLayout() {
        refreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                getMovies();
                refreshLayout.setRefreshing(false);
            }, 2000);

            refreshLayout.setColorSchemeResources(R.color.colorAccent);
        });
    }

    private void getMovies() {
        apiService.getTrendingMovies(API_KEY,1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()){
                    if (response.body() != null){
                        MovieResponse movies = response.body();

                        totalPages = movies.getTotalPages();
                        movieList = movies.getResults();
                        adapter = new MovieHomeAdapter(movieList, R.layout.layout_movies, getContext());
                        trending_recycleview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.INVISIBLE);
                        Log.i(TAG, "onCreateView: TotalPages " + totalPages);
                        Log.i(TAG, "Number of trending movies received:" + movieList.size());

                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t);
            }
        });
    }

    private void loadMoreMovies( int pages) {
        apiService.getTrendingMovies(API_KEY, pages).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()){
                    if (response.body() != null){
                        MovieResponse movies = response.body();
                        if (pages == 1){
                            movieList = movies.getResults();
                            totalPages = movies.getTotalPages();
                            trending_recycleview.setAdapter(adapter);
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


    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onResume() {
        getMovies();
        Log.i(TAG, "onResume: ");
        super.onResume();
    }
}
