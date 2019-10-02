package com.premar.muvi.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;

public class ImageViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener  {
    private ItemClickListener itemClickListener;
    public ImageView image;
    public TextView  imageVotes;

    public ImageViewHolder(@NonNull View view) {
        super(view);
        view.setOnClickListener(this);
        image = view.findViewById(R.id.detail_images);
        imageVotes = view.findViewById(R.id.detail_image_votes);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
