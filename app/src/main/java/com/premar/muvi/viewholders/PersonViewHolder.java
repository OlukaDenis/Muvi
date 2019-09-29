package com.premar.muvi.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;

public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    public ImageView personImage;
    public TextView personName, personPopularity;

    public PersonViewHolder(@NonNull View view) {
        super(view);
        view.setOnClickListener(this);
        personImage = view.findViewById(R.id.person_image);
        personName = view.findViewById(R.id.person_name);
        personPopularity = view.findViewById(R.id.person_popularity);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
