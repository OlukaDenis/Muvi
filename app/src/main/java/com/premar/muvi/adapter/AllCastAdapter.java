package com.premar.muvi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.premar.muvi.Interface.ItemClickListener;
import com.premar.muvi.R;
import com.premar.muvi.model.credits.Cast;
import com.premar.muvi.viewholders.CastViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.constants.AppConstants.IMAGE_URL_BASE_PATH;

public class AllCastAdapter extends RecyclerView.Adapter<CastViewHolder> {
    private Context context;
    private List<Cast> castList;
    private int rowLayout;

    public AllCastAdapter(Context context, List<Cast> castList, int rowLayout) {
        this.context = context;
        this.castList = castList;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.castName.setText(castList.get(position).getName());
        holder.castCharacter.setText(castList.get(position).getCharacter());

        String image_url = IMAGE_URL_BASE_PATH + castList.get(position).getProfile_path();
        Picasso.with(context)
                .load(image_url)
                .noFade()
                .placeholder(R.drawable.ic_picture)
                .error(R.drawable.ic_picture)
                .into(holder.castImage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(context, String.valueOf( castList.get(position).getId()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}
