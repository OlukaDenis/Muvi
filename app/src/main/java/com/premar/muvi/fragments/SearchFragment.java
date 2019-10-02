package com.premar.muvi.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.adapter.MovieHomeAdapter;
import com.premar.muvi.model.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private RecyclerView searchRecycleview;
    private GridLayoutManager layoutManager;
    MovieHomeAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private ArrayList<Movie> movieList=new ArrayList<>();
    private Context context;
    private int totalPages;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        /**

        context=getContext();
        movieList= HomeActivity.movieList;

        adapter = new MovieHomeAdapter(movieList, R.layout.layout_movies, context);
        searchRecycleview = view.findViewById(R.id.search_results_recycleview);
        layoutManager = new GridLayoutManager(getContext(), 3);

        searchRecycleview.setLayoutManager(layoutManager);
        searchRecycleview.setAdapter(adapter);
        searchRecycleview.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
*/
        return view;

    }


}
