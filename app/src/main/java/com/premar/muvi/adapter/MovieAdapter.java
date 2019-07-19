package com.premar.muvi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    public MovieAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView movieDescription;
        RatingBar rating;
        ImageView movieImage;
        TextView release;
        TextView views;
        public MovieViewHolder(@NonNull View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieImage = (ImageView) v.findViewById(R.id.movie_image);
            movieTitle = (TextView) v.findViewById(R.id.title);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (RatingBar) v.findViewById(R.id.rating);
            release = (TextView) v.findViewById(R.id.release_date);
            views = (TextView) v.findViewById(R.id.votes);
        }
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, final int position) {
        String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(movieViewHolder.movieImage);
        movieViewHolder.movieTitle.setText(movies.get(position).getTitle());
        movieViewHolder.movieDescription.setText(movies.get(position).getOverview());

        double mRating = movies.get(position).getVoteAverage();
        double final_rating = (mRating / 10) * 5;
        float movie_rating = (float) final_rating;
        movieViewHolder.rating.setRating(movie_rating);

        movieViewHolder.release.setText(movies.get(position).getReleaseDate());

        int votes = movies.get(position).getVoteCount();
        movieViewHolder.views.setText(String.valueOf(votes));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}
