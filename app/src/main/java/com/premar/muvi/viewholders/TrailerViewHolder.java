package com.premar.muvi.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView trailerType;
    public ImageView trailerImage;
    public ItemClickListener itemClickListener;

    public TrailerViewHolder(@NonNull View itemView) {
        super(itemView);

        trailerImage = itemView.findViewById(R.id.trailer_image);
        trailerType = itemView.findViewById(R.id.trailer_type);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
