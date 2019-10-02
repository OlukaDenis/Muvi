package com.premar.muvi.fragments.alltvshows;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.premar.muvi.R;
import com.premar.muvi.activity.MainActivity;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.adapter.TvAdapter;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.commonUtils.PaginationScrollListener;
import com.premar.muvi.model.tv.Tv;
import com.premar.muvi.model.tv.TvResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingShowsFragment extends Fragment {
    private RecyclerView trending_recycleview;
    private ApiService apiService;
    private static final String TAG = TrendingShowsFragment.class.getSimpleName();
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshingLayout;
    private PaginationScrollListener paginationScrollListener;
    GridLayoutManager gridLayoutManager;
    private TvAdapter adapter;
    private List<Tv> tvList;
    public static int totalPages;


    public TrendingShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending_shows, container, false);

        trending_recycleview = view.findViewById(R.id.recycler_view_all_trending_tv);
        progressBar = view.findViewById(R.id.progressbar_trending_tv);
        refreshingLayout = view.findViewById(R.id.trending_tv_refresh_layout);

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

        getTvShows();
        refreshLayout();
        return view;
    }



    private void refreshLayout() {
        refreshingLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                getTvShows();
                refreshingLayout.setRefreshing(false);
            }, 2000);

            refreshingLayout.setColorSchemeResources(R.color.colorAccent);
        });
    }

    private void getTvShows() {
        apiService.getTrendingShows(API_KEY, 1).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        TvResponse tvShows = response.body();

                        totalPages = tvShows.getTotalPages();
                        tvList = tvShows.getResults();
                        adapter = new TvAdapter(tvList, getContext());
                        trending_recycleview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.INVISIBLE);
                        Log.i(TAG, "onCreateView: TotalPages " + totalPages);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    private void loadMoreMovies(int pages) {
        apiService.getTrendingShows(API_KEY, pages).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        TvResponse tvShows = response.body();

                        if (pages == 1){
                            tvList = tvShows.getResults();
                            totalPages = tvShows.getTotalPages();
                            trending_recycleview.setAdapter(adapter);
                        }
                        else {
                            List<Tv> listMovies = tvShows.getResults();
                            for (Tv movie : listMovies){
                                tvList.add(movie);
                                adapter.notifyItemInserted(tvList.size() - 1);
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

}
