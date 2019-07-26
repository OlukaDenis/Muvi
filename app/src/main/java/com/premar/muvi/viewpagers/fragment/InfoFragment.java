package com.premar.muvi.viewpagers.fragment;

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
import com.premar.muvi.adapter.ImageAdapter;
import com.premar.muvi.adapter.MovieTrailerAdapter;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.Genre;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.images.Backdrops;
import com.premar.muvi.model.images.ImageResponse;
import com.premar.muvi.model.trailers.Trailer;
import com.premar.muvi.model.trailers.TrailerResponse;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.temporary_storage.MovieCache;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.constants.AppConstants.ENGLISH_LANGUAGE;

public class InfoFragment extends Fragment {
    private TextView overview, originalTitle, originalLanguage, budget, homepage, revenue, status, releaseDate;
    private TextView progress_text;
    private ProgressBar rating;
    private int movieId;
    private static final String API_KEY = AppConstants.API_KEY;
    private ApiService apiService;
    private static String TAG = InfoFragment.class.getSimpleName();

    private RecyclerView genreRecyclerView;
    private RecyclerView trailerRecyclerView;
    private RecyclerView imageRecyclerview;

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
        originalLanguage = view.findViewById(R.id.detail_original_language);
        originalTitle = view.findViewById(R.id.detail_original_title);
        budget = view.findViewById(R.id.detail_budget);
        revenue = view.findViewById(R.id.detail_revenue);
        status = view.findViewById(R.id.detail_status);
        releaseDate = view.findViewById(R.id.detail_release_date);
        homepage = view.findViewById(R.id.detail_homepage);

        movieId = MovieCache.movieId;
        apiService = ApiUtils.getApiService();

        genreRecyclerView = view.findViewById(R.id.genre_recycler_view);
        trailerRecyclerView = view.findViewById(R.id.trailer_recyler_view);
        imageRecyclerview = view.findViewById(R.id.image_recycler_view);


        //genre layout manager
        genreRecyclerView.setHasFixedSize(true);
        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        genreRecyclerView.setLayoutManager(genreLayoutManager);

        //trailer layout manager
        trailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);

        //trailer layout manager
        imageRecyclerview.setHasFixedSize(true);
        LinearLayoutManager imageLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerview.setLayoutManager(imageLayoutManager);

        //Background API methods
        fetchMovieDetails();
        return view;
    }

    private void fetchMovieDetails() {
        apiService.getMovie(movieId, API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, Response<Movie> response) {
                assert response.body() != null;

                Movie movieDetails = response.body();

                 overview.setText(movieDetails.getOverview());
                 double mRating = movieDetails.getVoteAverage();
                 int rate = (int) mRating;
                 rating.setProgress(rate);
                 progress_text.setText(String.valueOf(mRating));

                 String budgetCurr = AppConstants.formatCurrency(movieDetails.getBudget());
                 budget.setText(budgetCurr);

                 String revenueCurr = AppConstants.formatCurrency(movieDetails.getRevenue());
                 revenue.setText(revenueCurr);

                 releaseDate.setText(movieDetails.getReleaseDate());

                 status.setText(movieDetails.getStatus());

                 String lang = AppConstants.formatLanguage(movieDetails.getOriginalLanguage());
                 originalLanguage.setText(lang);

                 originalTitle.setText(movieDetails.getOriginalTitle());

                 homepage.setText(movieDetails.getHomepage());

                List<Genre> genres = movieDetails.getGenres();
                GenreAdapter adapter = new GenreAdapter(getActivity(), genres, R.layout.layout_genre);
                genreRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

        apiService.getMovieTrailers(movieId, API_KEY, ENGLISH_LANGUAGE).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                assert response.body() != null;

                TrailerResponse trailers = response.body();

                List<Trailer> trailerList = trailers.getResults();
                MovieTrailerAdapter adapter = new MovieTrailerAdapter(getActivity(), trailerList, R.layout.layout_trailers);
                trailerRecyclerView.setAdapter(adapter);

                Log.i(TAG, String.valueOf(trailers.getMovieId()));
                Toast.makeText(getActivity(), String.valueOf(trailers.getMovieId()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        apiService.getMovieImages(movieId, API_KEY).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                assert response.body() != null;

                ImageResponse images = response.body();

                List<Backdrops> backdrops = images.getBackdrops();
                ImageAdapter adapter = new ImageAdapter(getActivity(), backdrops, R.layout.layout_image);
                imageRecyclerview.setAdapter(adapter);

                Log.i(TAG, String.valueOf(images.getId()));
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }


}
