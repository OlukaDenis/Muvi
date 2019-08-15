package com.premar.muvi.fragments.person_detail_fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.premar.muvi.R;
import com.premar.muvi.activity.WikipediaProfile;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.utils.AppConstants;
import com.premar.muvi.model.people.Person;
import com.premar.muvi.temporary_storage.MovieCache;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.muvi.utils.AppConstants.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonInfoFragment extends Fragment {
    private int personId;
    private ApiService apiService;
    private TextView birthday, birthplace, profile, moreWiki, also_known_as;


    public PersonInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);

        //personId = MovieCache.personId;
        //
        // Cast selectedCast = PersonDetailActivity.selectedCast;
        personId = MovieCache.personId;
        apiService = ApiUtils.getApiService();
        birthday = view.findViewById(R.id.person_birthday);
        birthplace = view.findViewById(R.id.person_birthplace);
        profile = view.findViewById(R.id.person_biography);
        moreWiki = view.findViewById(R.id.person_profile_wiki);
        also_known_as = view.findViewById(R.id.person_other_names);

        getPersonDetails();
        return view;
    }

    private void getPersonDetails() {
        apiService.getPersonDetails(personId, API_KEY).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Person person = response.body();

                        String bd = person.getBirthday();
                        if (bd.equals("")){
                            birthday.setText("-");
                        }else {
                            birthday.setText(bd);
                        }

                        String bp = person.getPlace_of_birth();
                        if (bp.equals("")){
                                birthplace.setText("-");
                            }
                            else {
                                birthplace.setText(bp);
                            }

                        profile.setText(person.getBiography());

                        List<String> names = person.getAlsoKnownAs();
                        if(names == null){
                            also_known_as.setText("-");
                        }else {
                            String known_as = names.get(0);
                            also_known_as.setText(known_as);
                        }


                        String name = person.getName();
                        MovieCache.wiki_profile_url = AppConstants.formatStringtoUnderscore(name);
                        moreWiki.setOnClickListener(view -> {
                            Intent wikiIntent = new Intent(getContext(), WikipediaProfile.class);
                            wikiIntent.putExtra("title", name);
                            startActivity(wikiIntent);
                        });
                    } else {
                        Toast.makeText(getContext(), "No biography", Toast.LENGTH_SHORT).show();
                        Log.i("PersonInfoFragment", "No biography: ");
                    }
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }

}
