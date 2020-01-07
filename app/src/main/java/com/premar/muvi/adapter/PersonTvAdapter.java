package com.premar.muvi.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.R;
import com.premar.muvi.activity.MovieDetailActivity;
import com.premar.muvi.activity.TvDetailActivity;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.PersonMovie;
import com.premar.muvi.model.PersonTv;
import com.premar.muvi.temporary_storage.MovieCache;
import com.premar.muvi.utils.AppConstants;
import com.premar.muvi.viewholders.MovieHomeViewHolder;
import com.premar.muvi.viewholders.TvViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;
import static com.premar.muvi.utils.AppConstants.BACKDROP_URL_BASE_PATH;
import static com.premar.muvi.utils.AppConstants.IMAGE_URL_BASE_PATH;

public class PersonTvAdapter extends RecyclerView.Adapter<TvViewHolder> {
    private ApiService apiService = ApiUtils.getApiService();
    private Context context;
    private List<PersonTv> personShows;
    private int rowLayout;
    private Movie selectedMovie;
    private static String TAG = PersonMoviesAdapter.class.getSimpleName();

    public PersonTvAdapter(Context context, List<PersonTv> personShows) {
        this.context = context;
        this.personShows = personShows;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movies, parent, false);
        return new TvViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        String image_url = IMAGE_URL_BASE_PATH + personShows.get(position).getPoster_path();
        String backdrop_url = BACKDROP_URL_BASE_PATH + personShows.get(position).getBackdrop_path();

        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_muvi_default)
                .error(R.drawable.ic_muvi_default)
                .into(holder.movieImage);
        holder.movieTitle.setText(personShows.get(position).getName());

        holder.setItemClickListener((view, i, isLongClick) -> {

            MovieCache.tvId = personShows.get(i).getId();
            MovieCache.movieTitle = personShows.get(i).getName();
            Movie movie = AppConstants.getSelectedMovie(personShows.get(i).getId());

            Intent detailIntent = new Intent(context, TvDetailActivity.class);
            detailIntent.putExtra("movie_title", personShows.get(i).getName());
            detailIntent.putExtra("movie_poster", image_url);
            detailIntent.putExtra("movie_backdrop", backdrop_url);
            detailIntent.putExtra("movie_date", personShows.get(i).getFirst_air_date());
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(detailIntent);

        });
    }

    @Override
    public int getItemCount() {
        return personShows.size();
    }
}
