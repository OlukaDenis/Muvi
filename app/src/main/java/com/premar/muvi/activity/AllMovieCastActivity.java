package com.premar.muvi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.premar.muvi.R;
import com.premar.muvi.adapter.AllCastAdapter;
import com.premar.muvi.fragments.movie_fragment.InfoFragment;
import com.premar.muvi.model.credits.Cast;
import com.premar.muvi.model.credits.Credits;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;

public class AllMovieCastActivity extends AppCompatActivity {
    private ApiService apiService;
    private static String TAG = InfoFragment.class.getSimpleName();
    private RecyclerView castRecyclerView;
    private int movieId;
    private int tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cast);
        setTitle("Cast");

        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        movieId = Objects.requireNonNull(intent.getExtras()).getInt("movieId");

//
//        if (intent.hasExtra("movieId")) {
//
//        } else {
//            tvId = Objects.requireNonNull(intent.getExtras()).getInt("tvId");
//            getTvCredits();
//        }


        apiService = ApiUtils.getApiService();

        castRecyclerView =findViewById(R.id.all_cast_recyclerview);

        //genre layout manager
        castRecyclerView.setHasFixedSize(true);
        LinearLayoutManager castLayoutManager = new LinearLayoutManager(this);
        castRecyclerView.setLayoutManager(castLayoutManager);

        getMovieCredits();
    }

    public void getMovieCredits() {
        apiService.getCredits(movieId, API_KEY).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(@NonNull Call<Credits> call, @NonNull Response<Credits> response) {
               if (response.body() != null) {

                   Credits credits = response.body();
                   Log.d(TAG, "Credits: " + credits.getCastList());

                   List<Cast> castList = credits.getCastList();
                   AllCastAdapter adapter = new AllCastAdapter(getApplicationContext(), castList, R.layout.layout_cast);
                   castRecyclerView.setAdapter(adapter);

                   Log.i(TAG, String.valueOf(credits.getMovie_id()));
               }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
