package com.premar.muvi.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.activity.MovieDetailActivity;
import com.premar.muvi.model.Movie;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.MovieHomeViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.utils.AppConstants.BACKDROP_URL_BASE_PATH;
import static com.premar.muvi.utils.AppConstants.IMAGE_URL_BASE_PATH;

public class MovieHomeAdapter extends RecyclerView.Adapter<MovieHomeViewHolder> {
    private Context context;
    private List<Movie> movies;


    public MovieHomeAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movies, parent, false);
        return new MovieHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHomeViewHolder movieHomeViewHolder, int position) {
        String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        String backdrop_url = BACKDROP_URL_BASE_PATH + movies.get(position).getBackdropPath();

        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_muvi_default)
                .error(R.drawable.ic_muvi_default)
                .into(movieHomeViewHolder.movieImage);
        movieHomeViewHolder.movieTitle.setText(movies.get(position).getTitle());

        movieHomeViewHolder.setItemClickListener((view, i, isLongClick) -> {
            Movie selectedMovie = movies.get(position);
            Intent detailIntent = new Intent(context, MovieDetailActivity.class);
            //save temporary the movie details
            MovieCache.movieId = movies.get(i).getId();

            detailIntent.putExtra("movie", selectedMovie);
            detailIntent.putExtra("movie_title", movies.get(i).getTitle());
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return movies==null?0 : movies.size();
    }
}

