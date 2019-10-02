package com.premar.muvi.fragments.person_detail_fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.adapter.PersonMoviesAdapter;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.PersonMovie;
import com.premar.muvi.model.PersonMovieResponse;
import com.premar.muvi.temporary_storage.MovieCache;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;


public class PersonMoviesFragment extends Fragment {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private int personId;


    public PersonMoviesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_movies, container, false);

        apiService = ApiUtils.getApiService();
        personId = MovieCache.personId;

        recyclerView = view.findViewById(R.id.person_movies_recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        getMovies();
        return view;
    }

    private void getMovies() {
        apiService.getPersonMovies(personId, API_KEY).enqueue(new Callback<PersonMovieResponse>() {
            @Override
            public void onResponse(Call<PersonMovieResponse> call, Response<PersonMovieResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){

                        PersonMovieResponse personMovieResponse = response.body();

                        List<PersonMovie> personMovies = personMovieResponse.getPersonMovies();
                        PersonMoviesAdapter adapter = new PersonMoviesAdapter(getContext(), personMovies, R.layout.layout_movies);
                        recyclerView.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<PersonMovieResponse> call, Throwable t) {

            }
        });
    }


}
