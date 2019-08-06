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
import com.premar.muvi.activity.TvDetailActivity;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.tv.Tv;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.MovieHomeViewHolder;
import com.premar.muvi.viewholders.TvViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.constants.AppConstants.BACKDROP_URL_BASE_PATH;
import static com.premar.muvi.constants.AppConstants.IMAGE_URL_BASE_PATH;

public class TvAdapter extends RecyclerView.Adapter<TvViewHolder> {
    private List<Tv> shows;
    private int rowLayout;
    private Context context;

    public TvAdapter(List<Tv> shows, int rowLayout, Context context) {
        this.shows = shows;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        String image_url = IMAGE_URL_BASE_PATH + shows.get(position).getPoster_path();
        String backdrop_url = BACKDROP_URL_BASE_PATH + shows.get(position).getBackdrop_path();

        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(holder.movieImage);
        holder.movieTitle.setText(shows.get(position).getName());

        holder.setItemClickListener((view, i, isLongClick) -> {
            Intent detailIntent = new Intent(context, TvDetailActivity.class);

            //save temporary the movie details
           // MovieCache.movieId = shows.get(i).getId();
            MovieCache.setTvId(shows.get(i).getId());
            MovieCache.movieTitle = shows.get(i).getName();

            detailIntent.putExtra("movie_title", shows.get(i).getName());
            detailIntent.putExtra("movie_date", shows.get(i).getFirst_air_date());
            detailIntent.putExtra("movie_poster", image_url);
            detailIntent.putExtra("movie_backdrop", backdrop_url);
            detailIntent.putExtra("movie_votes", shows.get(i).getVote_count());
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }
}
