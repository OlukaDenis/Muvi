package com.premar.muvi.detail_viewpager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.adapter.MovieTrailerAdapter;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.trailers.Trailer;
import com.premar.muvi.model.trailers.TrailerResponse;
import com.premar.muvi.rest.ApiService;
import com.premar.muvi.rest.ApiUtils;
import com.premar.muvi.temporary_storage.MovieCache;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CastFragment extends Fragment {
    private int movieId;
    private static final String API_KEY = AppConstants.API_KEY;
    private ApiService apiService;
    private static String TAG = InfoFragment.class.getSimpleName();
    private RecyclerView trailerRecyclerView;

    public CastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cast, container, false);
        movieId = MovieCache.movieId;
       // trailerRecyclerView = view.findViewById(R.id.trailer_recyler_view);
        //trailer layout manager

        //trailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //trailerRecyclerView.setLayoutManager(trailerLayoutManager);

        apiService = ApiUtils.getApiService();
       // getTrailers();

        return view;
    }

    public void getTrailers(){
        apiService.getMovieTrailers(movieId, API_KEY).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                assert response.body() != null;
                List<Trailer> movieTrailer = response.body().getResults();
                //trailerRecyclerView.setAdapter(new MovieTrailerAdapter(getContext(), movieTrailer, R.layout.layout_trailers));

            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

}
