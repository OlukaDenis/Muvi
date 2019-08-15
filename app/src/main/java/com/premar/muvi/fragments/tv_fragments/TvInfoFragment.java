package com.premar.muvi.fragments.tv_fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.activity.WikipediaProfile;
import com.premar.muvi.adapter.GenreAdapter;
import com.premar.muvi.adapter.ImageAdapter;
import com.premar.muvi.adapter.MovieTrailerAdapter;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.utils.AppConstants;
import com.premar.muvi.model.Genre;
import com.premar.muvi.model.images.Backdrops;
import com.premar.muvi.model.images.ImageResponse;
import com.premar.muvi.model.trailers.Trailer;
import com.premar.muvi.model.trailers.TrailerResponse;
import com.premar.muvi.model.tv.Tv;
import com.premar.muvi.temporary_storage.MovieCache;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;
import static com.premar.muvi.utils.AppConstants.ENGLISH_LANGUAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvInfoFragment extends Fragment {
    private TextView overview, originalTitle, originalLanguage, firstAir, homepage, lastAir, status;
    private TextView progress_text, viewWikipedia, season_number, episode_number, showType;
    private ProgressBar rating;
    private int tvId;
    private ApiService apiService;
    private static String TAG = TvInfoFragment.class.getSimpleName();

    private RecyclerView genreRecyclerView;
    private RecyclerView trailerRecyclerView;
    private RecyclerView imageRecyclerview;


    public TvInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_info, container, false);

        overview = view.findViewById(R.id.tv_info_detail_overview);
        progress_text = view.findViewById(R.id.tv_info_progress_text);
        rating = view.findViewById(R.id.tv_info_progressBar);
        originalLanguage = view.findViewById(R.id.tv_info_original_language);
        originalTitle = view.findViewById(R.id.tv_info_original_title);
        firstAir = view.findViewById(R.id.tv_info_first_air_date);
        lastAir = view.findViewById(R.id.tv_info_last_air_date);
        status = view.findViewById(R.id.tv_info_status);
        episode_number = view.findViewById(R.id.tv_info_episode_number);
        season_number = view.findViewById(R.id.tv_info_season_number);
        homepage = view.findViewById(R.id.tv_info_homepage);
        viewWikipedia = view.findViewById(R.id.tv_info_read_wikipedia);
        showType = view.findViewById(R.id.tv_info_show_type);

        tvId = MovieCache.tvId;
        apiService = ApiUtils.getApiService();

        genreRecyclerView = view.findViewById(R.id.tv_info_genre_recycler_view);
        trailerRecyclerView = view.findViewById(R.id.tv_info_trailer_recyler_view);
        imageRecyclerview = view.findViewById(R.id.tv_info_image_recycler_view);


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
        fetchTvDetails();

        return view;
    }

    private void fetchTvDetails() {
        apiService.getTvShow(tvId, API_KEY).enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(@NonNull Call<Tv> call, Response<Tv> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Tv tvDetails = response.body();
                        String title = tvDetails.getName();
                        MovieCache.wiki_profile_url = AppConstants.formatStringtoUnderscore(title);
                        Log.i(TAG, "Wiki url: " + MovieCache.wiki_profile_url);
                        viewWikipedia.setOnClickListener(view -> {
                            Intent wikiIntent = new Intent(getContext(), WikipediaProfile.class);
                            wikiIntent.putExtra("title", title);
                            startActivity(wikiIntent);
                        });

                        overview.setText(tvDetails.getOverview());

                        double mRating = tvDetails.getVote_average();
                        int rate = (int) mRating;
                        rating.setProgress(rate);
                        progress_text.setText(String.valueOf(mRating));

                        season_number.setText(String.valueOf(tvDetails.getSeason_number()));
                        episode_number.setText(String.valueOf(tvDetails.getEpisode_number()));

                        //first air date
                        String firstDate = null;
                        try {
                            firstDate = AppConstants.formatDate(tvDetails.getFirst_air_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        firstAir.setText(firstDate);


                        //last air date
                        String lastDate = null;
                        try {
                            lastDate = AppConstants.formatDate(tvDetails.getLast_air_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        lastAir.setText(lastDate);


                        status.setText(tvDetails.getStatus());
                        showType.setText(tvDetails.getType());

                        String lang = AppConstants.formatLanguage(tvDetails.getOriginal_language());
                        originalLanguage.setText(lang);

                        originalTitle.setText(tvDetails.getOriginal_name());

                        homepage.setText(tvDetails.getHomepage());

                        List<Genre> genres = tvDetails.getGenres();
                        GenreAdapter adapter = new GenreAdapter(getActivity(), genres, R.layout.layout_genre);
                        genreRecyclerView.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<Tv> call, Throwable throwable) {
                Log.e(TAG, "API tvshow error: " + throwable.toString());
            }
        });

        apiService.getTvTrailers(tvId, API_KEY, ENGLISH_LANGUAGE).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        TrailerResponse trailers = response.body();

                        List<Trailer> trailerList = trailers.getResults();
                        MovieTrailerAdapter adapter = new MovieTrailerAdapter(getActivity(), trailerList, R.layout.layout_trailers);
                        trailerRecyclerView.setAdapter(adapter);

                        Log.i(TAG, String.valueOf(trailers.getMovieId()));
                       // Toast.makeText(getActivity(), String.valueOf(trailers.getMovieId()), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        apiService.getTvImages(tvId, API_KEY).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ImageResponse images = response.body();

                        List<Backdrops> backdrops = images.getBackdrops();
                        ImageAdapter adapter = new ImageAdapter(getActivity(), backdrops, R.layout.layout_image);
                        imageRecyclerview.setAdapter(adapter);

                        Log.i(TAG, String.valueOf(images.getId()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

}
