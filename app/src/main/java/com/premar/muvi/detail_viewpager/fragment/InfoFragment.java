package com.premar.muvi.detail_viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.premar.muvi.R;
import com.premar.muvi.adapter.GenreAdapter;
import com.premar.muvi.adapter.MovieTrailerAdapter;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.Genre;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.trailers.Trailer;
import com.premar.muvi.model.trailers.TrailerResponse;
import com.premar.muvi.rest.ApiService;
import com.premar.muvi.rest.ApiUtils;
import com.premar.muvi.temporary_storage.MovieCache;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment {
    private TextView overview;
    private TextView progress_text;
    private ProgressBar rating;
    private int movieId;
    private static final String API_KEY = AppConstants.API_KEY;
    private ApiService apiService;
    private static String TAG = InfoFragment.class.getSimpleName();

    private RecyclerView genreRecyclerView;
    private RecyclerView trailerRecyclerView;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        overview = view.findViewById(R.id.detail_overview);
        progress_text = view.findViewById(R.id.progress_text);
        rating = view.findViewById(R.id.progressBar);

        movieId = MovieCache.movieId;
        apiService = ApiUtils.getApiService();
        Toast.makeText(getContext(), API_KEY, Toast.LENGTH_SHORT).show();



        genreRecyclerView = view.findViewById(R.id.genre_recycler_view);
        trailerRecyclerView = view.findViewById(R.id.trailer_recyler_view);

        //genre layout manager
        genreRecyclerView.setHasFixedSize(true);
        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        genreRecyclerView.setLayoutManager(genreLayoutManager);

        //trailer layout manager
        trailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);

        //Background API methods
        fetchMovieDetails();
        getTrailers();
        return view;
    }

    private void fetchMovieDetails() {
        apiService.getMovie(movieId, API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                // assert response.body() != null;

                List<Genre> genres = response.body().getGenres();
                GenreAdapter adapter = new GenreAdapter(getActivity(), genres, R.layout.layout_genre);
                genreRecyclerView.setAdapter(adapter);
                overview.setText(response.body().getOverview());

                double mRating = response.body().getVoteAverage();
                int rate = (int) mRating;
                rating.setProgress(rate);

                progress_text.setText(String.valueOf(mRating));

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

    }

    public void getTrailers() {
        apiService.getMovieTrailers(movieId, API_KEY).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                assert response.body() != null;
                List<Trailer> movieTrailer = response.body().getResults();
                trailerRecyclerView.setAdapter(new MovieTrailerAdapter(getContext(), movieTrailer, R.layout.layout_trailers));
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

}
