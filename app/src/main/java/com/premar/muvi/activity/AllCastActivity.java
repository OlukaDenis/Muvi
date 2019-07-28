package com.premar.muvi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.premar.muvi.R;
import com.premar.muvi.adapter.AllCastAdapter;
import com.premar.muvi.viewpagers.fragment.InfoFragment;
import com.premar.muvi.model.credits.Cast;
import com.premar.muvi.model.credits.Credits;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.temporary_storage.MovieCache;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.constants.AppConstants.API_KEY;

public class AllCastActivity extends AppCompatActivity {
    private ApiService apiService;
    private static String TAG = InfoFragment.class.getSimpleName();
    private RecyclerView castRecyclerView;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cast);
        setTitle("Cast");

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        movieId = MovieCache.movieId;

        apiService = ApiUtils.getApiService();


        castRecyclerView =findViewById(R.id.all_cast_recyclerview);

        //genre layout manager
        castRecyclerView.setHasFixedSize(true);
        LinearLayoutManager castLayoutManager = new LinearLayoutManager(this);
        castRecyclerView.setLayoutManager(castLayoutManager);

        getTrailers();
    }

    public void getTrailers() {
        apiService.getCredits(movieId, API_KEY).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                assert response.body() != null;

                Credits credits = response.body();

                List<Cast> castList = credits.getCastList();
                AllCastAdapter adapter = new AllCastAdapter(getApplicationContext(), castList, R.layout.layout_cast);
                castRecyclerView.setAdapter(adapter);

                Log.i(TAG, String.valueOf(credits.getMovie_id()));
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
