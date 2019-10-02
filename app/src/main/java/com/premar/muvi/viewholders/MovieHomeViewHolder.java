package com.premar.muvi.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;

public class MovieHomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public LinearLayout rootLayout;
    public TextView movieTitle;
    public ImageView movieImage;
    private ItemClickListener itemClickListener;

    public MovieHomeViewHolder(@NonNull View itemView) {
        super(itemView);
        rootLayout = (LinearLayout) itemView.findViewById(R.id.moviesHomeLayout);
        movieImage = (ImageView) itemView.findViewById(R.id.movie_image_home);
        movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title_home);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
