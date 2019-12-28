package com.premar.muvi.fragments.person_detail_fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.PersonMovie;
import com.premar.muvi.model.PersonMovieResponse;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.utils.AppConstants;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;


public class PersonMoviesFragment extends Fragment {
    private Context context;
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
        context = getContext();
        apiService = ApiUtils.getApiService();
        personId = MovieCache.personId;
        setUpRecyclerView(view);
        getMovies();
        return view;
    }

    private void setUpRecyclerView(View view){
        recyclerView = view.findViewById(R.id.person_movies_recycler_view);
        if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        } else {
            recyclerView.setLayoutManager((new GridLayoutManager(context,5)));
        }
    }

    private void getMovies() {
        apiService.getPersonMovies(personId, API_KEY).enqueue(new Callback<PersonMovieResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PersonMovieResponse> call, Response<PersonMovieResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){

                        PersonMovieResponse personMovieResponse = response.body();

                        List<PersonMovie> personMovies = personMovieResponse.getPersonMovies();
//                        Collections.sort(personMovies, new DateComparator());
                        PersonMoviesAdapter adapter = new PersonMoviesAdapter(getContext(), personMovies, R.layout.layout_movies);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonMovieResponse> call, Throwable t) {

            }
        });
    }

    class DateComparator implements Comparator<PersonMovie> {
        public int compare(PersonMovie c1, PersonMovie c2){
            try {
                String year1 = AppConstants.getYear(c1.getRelease_date());
                String year2 = AppConstants.getYear(c2.getRelease_date());

                if(year1 == null || year2 == null){
                    return 0;
                }
                return year1.compareTo(year2);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;
        }
    }

}
