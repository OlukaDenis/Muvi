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
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.search.Search;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.MovieHomeViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.constants.AppConstants.BACKDROP_URL_BASE_PATH;
import static com.premar.muvi.constants.AppConstants.IMAGE_URL_BASE_PATH;

public class SearchAdapter extends RecyclerView.Adapter<MovieHomeViewHolder> {
    private List<Search> searches;
    private int rowLayout;
    private Context context;

    public SearchAdapter(List<Search> movies, int rowLayout, Context context) {
        this.searches = movies;
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
        String image_url = IMAGE_URL_BASE_PATH + searches.get(position).getPosterPath();
        String backdrop_url = BACKDROP_URL_BASE_PATH + searches.get(position).getBackdropPath();

        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(movieHomeViewHolder.movieImage);
        movieHomeViewHolder.movieTitle.setText(searches.get(position).getTitle());

        movieHomeViewHolder.setItemClickListener((view, position1, isLongClick) -> {
            Intent detailIntent = new Intent(context, MovieDetailActivity.class);
            //save temporary the movie details
            MovieCache.movieId = searches.get(position1).getId();
            MovieCache.movieTitle = searches.get(position1).getTitle();

            detailIntent.putExtra("movie_title", searches.get(position1).getTitle());
            detailIntent.putExtra("movie_date", searches.get(position1).getReleaseDate());
            detailIntent.putExtra("movie_poster", image_url);
            detailIntent.putExtra("movie_backdrop", backdrop_url);
            detailIntent.putExtra("movie_duration", searches.get(position1).getRuntime());
            detailIntent.putExtra("movie_votes", searches.get(position1).getVoteCount());
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }
}
