package com.premar.muvi.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.model.images.Backdrops;
import com.premar.muvi.viewholders.ImageViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.utils.AppConstants.BACKDROP_URL_BASE_PATH;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private Context context;
    private List<Backdrops> imageList;
    private int rowLayout;

    public ImageAdapter(Context context, List<Backdrops> imageList, int rowLayout) {
        this.context = context;
        this.imageList = imageList;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String image_url = BACKDROP_URL_BASE_PATH + imageList.get(position).getFile_path();
        Picasso.get()
                .load(image_url)
                .noFade()
                .placeholder(R.drawable.ic_muvi_default)
                .error(R.drawable.ic_muvi_default)
                .into(holder.image);

        int votes = imageList.get(position).getVote_count();
        holder.imageVotes.setText(String.valueOf(votes));

        holder.setItemClickListener((view, i, isLongClick) -> {

        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
