package com.premar.muvi.viewpagers.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.activity.AllCastActivity;
import com.premar.muvi.adapter.CastAdapter;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.credits.Cast;
import com.premar.muvi.model.credits.Credits;
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
    private RecyclerView castRecyclerView;
    private TextView allCast;

    public CastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cast, container, false);
        movieId = MovieCache.movieId;

        apiService = ApiUtils.getApiService();


        castRecyclerView = view.findViewById(R.id.cast_recycleView);
        allCast = view.findViewById(R.id.more_cast);
        allCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllCastActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //genre layout manager
        castRecyclerView.setHasFixedSize(true);
        LinearLayoutManager castLayoutManager = new LinearLayoutManager(getContext());
        castRecyclerView.setLayoutManager(castLayoutManager);

        getTrailers();
        return view;
    }


    public void getTrailers() {
        apiService.getCredits(movieId, API_KEY).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                assert response.body() != null;

                Credits credits = response.body();

                List<Cast> castList = credits.getCastList();
                CastAdapter adapter = new CastAdapter(getActivity(), castList, R.layout.layout_cast);
                castRecyclerView.setAdapter(adapter);

                Log.i(TAG, String.valueOf(credits.getMovie_id()));
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


}
