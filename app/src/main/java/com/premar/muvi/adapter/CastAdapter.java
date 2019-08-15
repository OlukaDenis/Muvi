package com.premar.muvi.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.activity.PersonDetailActivity;
import com.premar.muvi.model.credits.Cast;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.CastViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.utils.AppConstants.IMAGE_URL_BASE_PATH;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {
    private Context context;
    private int cast_limit = 10;
    private List<Cast> castList;
    private int rowLayout;

    public CastAdapter(Context context, List<Cast> castList, int rowLayout) {
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
        Picasso.get()
                .load(image_url)
                .noFade()
                .placeholder(R.drawable.ic_person_blue)
                .error(R.drawable.ic_person_blue)
                .into(holder.castImage);

        holder.setItemClickListener((view, i, isLongClick) -> {
            MovieCache.personId = castList.get(i).getId();
            Cast selectedCast = castList.get(position);

            Intent personIntent = new Intent(context, PersonDetailActivity.class);
            personIntent.putExtra("poster", image_url);
            personIntent.putExtra("backdrop", image_url);
            personIntent.putExtra("name", castList.get(i).getName());
            personIntent.putExtra("cast", selectedCast);

            personIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(personIntent);

        });
    }

    @Override
    public int getItemCount() {
        if (castList.size() > cast_limit){
            return cast_limit;
        }
        else {
            return castList.size();
        }
    }
}
