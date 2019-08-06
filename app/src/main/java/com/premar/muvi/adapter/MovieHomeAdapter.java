package com.premar.muvi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;
import com.premar.muvi.activity.MovieDetailActivity;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.Movie;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.MovieHomeViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieHomeAdapter extends RecyclerView.Adapter<MovieHomeViewHolder> {
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private String IMAGE_URL = AppConstants.IMAGE_URL_BASE_PATH;
    private String BACKDROP_URL = AppConstants.BACKDROP_URL_BASE_PATH;

    public MovieHomeAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHomeViewHolder movieHomeViewHolder, int position) {
        String image_url = IMAGE_URL + movies.get(position).getPosterPath();
        String backdrop_url = BACKDROP_URL + movies.get(position).getBackdropPath();

        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(movieHomeViewHolder.movieImage);
        movieHomeViewHolder.movieTitle.setText(movies.get(position).getTitle());

        movieHomeViewHolder.setItemClickListener((view, i, isLongClick) -> {
            Movie selectedMovie = movies.get(position);
            Intent detailIntent = new Intent(context, MovieDetailActivity.class);
            //save temporary the movie details
           MovieCache.movieId = movies.get(i).getId();

            detailIntent.putExtra("movie", selectedMovie);
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return movies==null?0 : movies.size();
    }
}

