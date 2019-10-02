package com.premar.muvi.model.people;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.SerializedName;
import com.premar.muvi.model.Movie;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("gender")
    private int gender;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("known_for")
    private List<Movie> movies;

    @SerializedName("also_known_as")
    private List<String> alsoKnownAs;

    @SerializedName("profile_path")
    private String profile_path;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("biography")
    private String biography;

    @SerializedName("place_of_birth")
    private String place_of_birth;

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Person person){
        view.setVisibility(person != null ? View.VISIBLE : View.GONE);
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }
}
