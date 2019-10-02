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
import com.premar.muvi.utils.AppConstants;
import com.premar.muvi.model.people.Person;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.viewholders.PersonViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.premar.muvi.utils.AppConstants.IMAGE_URL_BASE_PATH;

public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {
    private Context context;
    private List<Person> personList;
    private int rowLayout;

    public PersonAdapter(Context context, List<Person> personList, int rowLayout) {
        this.context = context;
        this.personList = personList;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        String image_url = IMAGE_URL_BASE_PATH + personList.get(position).getProfile_path();
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_person_blue)
                .error(R.drawable.ic_person_blue)
                .into(holder.personImage);

        holder.personName.setText(personList.get(position).getName());

        double popularity = personList.get(position).getPopularity();
        double pop = AppConstants.round(popularity, 1);
        holder.personPopularity.setText(String.valueOf(pop));

        holder.setItemClickListener((view, i, isLongClick) -> {
            MovieCache.personId = personList.get(i).getId();

            Intent personIntent = new Intent(context, PersonDetailActivity.class);
            personIntent.putExtra("poster", image_url);
            personIntent.putExtra("backdrop", image_url);
            personIntent.putExtra("name", personList.get(i).getName());
            personIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(personIntent);

        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
