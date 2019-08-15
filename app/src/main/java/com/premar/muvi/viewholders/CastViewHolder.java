package com.premar.muvi.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    public CircleImageView castImage;
    public TextView castName, castCharacter;

    public CastViewHolder(@NonNull View view) {
        super(view);
        view.setOnClickListener(this);
        castImage = (CircleImageView) view.findViewById(R.id.cast_image);
        castName = (TextView) view.findViewById(R.id.cast_name);
        castCharacter = (TextView) view.findViewById(R.id.cast_character);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
