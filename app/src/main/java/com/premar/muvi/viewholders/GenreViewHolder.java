package com.premar.muvi.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

