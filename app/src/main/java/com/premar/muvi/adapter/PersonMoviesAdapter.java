package com.premar.muvi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.activity.MovieDetailActivity;
import com.premar.muvi.model.PersonMovie;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.MovieHomeViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.constants.AppConstants.BACKDROP_URL_BASE_PATH;
import static com.premar.muvi.constants.AppConstants.IMAGE_URL_BASE_PATH;

public class PersonMoviesAdapter extends RecyclerView.Adapter<MovieHomeViewHolder> {
    private Context context;
    private List<PersonMovie> personMovies;
    private int rowLayout;

    public PersonMoviesAdapter(Context context, List<PersonMovie> personMovies, int rowLayout) {
        this.context = context;
        this.personMovies = personMovies;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public MovieHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHomeViewHolder movieHomeViewHolder, int position) {
        String image_url = IMAGE_URL_BASE_PATH + personMovies.get(position).getPoster_path();
        String backdrop_url = BACKDROP_URL_BASE_PATH + personMovies.get(position).getBackdrop_path();

        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(movieHomeViewHolder.movieImage);
        movieHomeViewHolder.movieTitle.setText(personMovies.get(position).getTitle());

        movieHomeViewHolder.setItemClickListener((view, i, isLongClick) -> {

            MovieCache.movieId = personMovies.get(i).getId();
            MovieCache.movieTitle = personMovies.get(i).getTitle();

            Intent detailIntent = new Intent(context, MovieDetailActivity.class);
            detailIntent.putExtra("movie_title", personMovies.get(i).getTitle());
            detailIntent.putExtra("movie_poster", image_url);
            detailIntent.putExtra("movie_backdrop", backdrop_url);
            detailIntent.putExtra("movie_date", personMovies.get(i).getRelease_date());
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailIntent);

        });
    }

    @Override
    public int getItemCount() {
        return personMovies.size();
    }
}
