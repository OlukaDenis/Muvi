package com.premar.muvi.fragments.person_detail_fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.BindingAdapter;
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
import com.premar.muvi.databinding.FragmentPersonInfoBinding;
import com.premar.muvi.utils.AppConstants;
import com.premar.muvi.model.people.Person;
import com.premar.muvi.temporary_storage.MovieCache;

import java.text.ParseException;
import java.util.Calendar;
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
    FragmentPersonInfoBinding mBinding;


    public PersonInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPersonInfoBinding.inflate(inflater);

        personId = MovieCache.personId;
        apiService = ApiUtils.getApiService();

        getPersonDetails();
        return mBinding.getRoot();
    }

    private void getPersonDetails() {
        apiService.getPersonDetails(personId, API_KEY).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Person person = response.body();
                        mBinding.setPerson(person);

                        mBinding.personLayout.setVisibility(View.VISIBLE);
                        mBinding.knownAsLayout.setVisibility(View.VISIBLE);
                        mBinding.placeLayout.setVisibility(View.VISIBLE);
                        mBinding.personLayout.setVisibility(View.VISIBLE);
                        mBinding.profileLayout.setVisibility(View.VISIBLE);

                        if (person.getAlsoKnownAs().isEmpty()){
                            mBinding.knownAsLayout.setVisibility(View.GONE);
                        } else {
                            mBinding.personOtherNames.setText(AppConstants.getKnownAsName(person.getAlsoKnownAs()));
                        }

                        if (person.getBirthday() == null){
                            mBinding.birthdayLayout.setVisibility(View.GONE);
                        } else {
                            try {
                                mBinding.personBirthday.setText(AppConstants.formatDate(person.getBirthday()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            try {
                                int birth_year = Integer.parseInt( AppConstants.getYear(person.getBirthday()));
                                int current_year = Calendar.getInstance().get(Calendar.YEAR);
                                String age = String.valueOf(current_year - birth_year);
                                mBinding.personAge.setText("("+age+")");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        if (person.getBiography() == null){
                            mBinding.profileLayout.setVisibility(View.GONE);
                        } else {
                            mBinding.personBiography.setText(person.getBiography());
                        }

                        if (person.getPlace_of_birth() == null){
                            mBinding.placeLayout.setVisibility(View.GONE);
                        } else {
                            mBinding.personBirthplace.setText(person.getPlace_of_birth());
                        }



                        String name = person.getName();
                        MovieCache.wiki_profile_url = AppConstants.formatStringtoUnderscore(name);
                        mBinding.personProfileWiki.setOnClickListener(view -> {
                            Intent wikiIntent = new Intent(getContext(), WikipediaProfile.class);
                            wikiIntent.putExtra("title", name);
                            startActivity(wikiIntent);
                        });
                    } else {
                        mBinding.personLayout.setVisibility(View.GONE);
                        mBinding.noBiographyLayout.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "No biography", Toast.LENGTH_SHORT).show();
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
