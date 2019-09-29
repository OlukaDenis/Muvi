package com.premar.muvi.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.activity.MovieDetailActivity;
import com.premar.muvi.model.Movie;
import com.premar.muvi.temporary_storage.MovieCache;
import java.util.ArrayList;


public class SearchAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SearchView searchView;
    private ArrayList<Movie> movies;

    public SearchAdapter(Context context, Cursor c, boolean autoRequery, SearchView searchView, ArrayList<Movie> movies) {
        super(context, c, autoRequery);
        this.searchView = searchView;
        this.movies=movies;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.layout_search_list, parent, false);
        return view;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup arg2) {
        if (convertview == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertview = inflater.inflate(R.layout.layout_search_list,
                    null);
        }
        convertview.setTag(position);
        return super.getView(position, convertview, arg2);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title=cursor.getString(cursor.getColumnIndex("text"));
        TextView textView=view.findViewById(R.id.tv_search_item);
        textView.setText(title);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=(Integer) view.getTag();//here is the position
                Movie selectedMovie = movies.get(id);
                Intent detailIntent = new Intent(mContext, MovieDetailActivity.class);
                //save temporary the movie details
                MovieCache.movieId = movies.get(id).getId();

                detailIntent.putExtra("movie", selectedMovie);
               // detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(detailIntent);
            }
        });
    }
}
