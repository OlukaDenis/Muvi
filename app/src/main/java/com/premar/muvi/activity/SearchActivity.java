package com.premar.muvi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.premar.muvi.R;
import com.premar.muvi.adapter.SearchAdapter;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.search.Search;
import com.premar.muvi.model.search.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.constants.AppConstants.API_KEY;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerview;
    private MaterialSearchBar searchBar;
    private List<String> suggestList = new ArrayList<>();
    private List<Search> searches = new ArrayList<>();
    private SearchAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        apiService = ApiUtils.getApiService();

        searchRecyclerview = findViewById(R.id.search_recyleview);
        searchBar = findViewById(R.id.searchBar);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        searchRecyclerview.setLayoutManager(layoutManager);

        searchBar.setHint("Search");
        searchBar.setLastSuggestions(suggestList);
        searchBar.setCardViewElevation(4);

        adapter = new SearchAdapter(searches, R.layout.layout_movies, getApplicationContext());
        searchResults(adapter);
    }

    private void searchResults(SearchAdapter adapter) {
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    searchRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_BACK) {

                }
            }
        });
    }

    private void startSearch(CharSequence text) {
        String query = text.toString();
        apiService.searchMovies(API_KEY, query).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        SearchResponse searchResponse = response.body();

                        searches = searchResponse.getResults();
                        searchRecyclerview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }


}
