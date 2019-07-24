package com.premar.muvi.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;

public class GenreViewHolder extends RecyclerView.ViewHolder  {
    public LinearLayout rootLayout;
    public TextView genreName;

    public GenreViewHolder(@NonNull View itemView) {
        super(itemView);
        rootLayout = (LinearLayout) itemView.findViewById(R.id.genre_linear_layout);
        genreName = (TextView) itemView.findViewById(R.id.genre_name);
    }


}

