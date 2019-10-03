package com.premar.muvi.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;
import com.premar.muvi.activity.WatchTrailerActivity;
import com.premar.muvi.model.trailers.Trailer;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.TrailerViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.utils.AppConstants.YOUTUBE_SUFFIX;
import static com.premar.muvi.utils.AppConstants.YOUTUBE_VIDEO_URL;

public class MovieTrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private Context context;
    private List<Trailer> trailers;
    private int rowLayout;


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

        String trailer_image_key = trailers.get(position).getTrailer_key();
        String image_url = YOUTUBE_VIDEO_URL + trailer_image_key + YOUTUBE_SUFFIX;

        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_muvi_default)
                .error(R.drawable.ic_muvi_default)
                .into(trailerViewHolder.trailerImage);


        trailerViewHolder.trailerType.setText(trailers.get(position).getType());

        trailerViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                MovieCache.youtube_trailer_id = trailers.get(position).getTrailer_key();

                context.startActivity(new Intent(context, WatchTrailerActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
