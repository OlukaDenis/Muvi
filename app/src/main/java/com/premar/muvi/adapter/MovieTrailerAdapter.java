package com.premar.muvi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.constants.AppConstants;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.trailers.Trailer;
import com.premar.muvi.viewholders.TrailerViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private Context context;
    private List<Trailer> trailers;
    private int rowLayout;

    private String YOUTUBE_URL_PREFIX = AppConstants.YOUTUBE_VIDEO_URL;
    private String YOUTUBE_SUFFIX = AppConstants.YOUTUBE_SUFFIX;

    public MovieTrailerAdapter(Context context, List<Trailer> trailers, int rowLayout) {
        this.context = context;
        this.trailers = trailers;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int position) {
        String trailer_image_key = trailers.get(position).getKey();
        String image_url = YOUTUBE_URL_PREFIX + trailer_image_key + YOUTUBE_SUFFIX;

        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(trailerViewHolder.trailerImage);
        trailerViewHolder.trailerType.setText(trailers.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
