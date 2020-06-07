package com.premar.muvi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.premar.muvi.R;
import com.premar.muvi.adapter.AllCastAdapter;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.credits.Cast;
import com.premar.muvi.model.credits.Credits;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;

public class AllTvCastActivity extends AppCompatActivity {
    private static final String TAG = "AllTvCastActivity";
    private int tvId;
    private ApiService apiService;
    private RecyclerView castRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tv_cast);
        setTitle("Cast");

        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        tvId = Objects.requireNonNull(intent.getExtras()).getInt("tvId");


        apiService = ApiUtils.getApiService();

        castRecyclerView =findViewById(R.id.tv_cast_recyclerview);

        //genre layout manager
        castRecyclerView.setHasFixedSize(true);
        LinearLayoutManager castLayoutManager = new LinearLayoutManager(this);
        castRecyclerView.setLayoutManager(castLayoutManager);
        getTvCredits();
    }

    public void getTvCredits() {
        apiService.getTvCredits(tvId, API_KEY).enqueue(new Callback<Credits>() {
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
