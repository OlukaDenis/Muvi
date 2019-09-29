package com.premar.muvi.fragments.allmovies;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.activity.MainActivity;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestMoviesFragment extends Fragment {
    private RecyclerView latest_recycleview;
    private ApiService apiService;
    private static final String TAG = MainActivity.class.getSimpleName();


    public LatestMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest_movies, container, false);

        latest_recycleview = view.findViewById(R.id.recycler_view_all_latest);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        latest_recycleview.setLayoutManager(layoutManager);
        latest_recycleview.setHasFixedSize(true);

        apiService = ApiUtils.getApiService();

        return view;
    }

}
