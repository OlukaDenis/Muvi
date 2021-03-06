package com.premar.muvi.utils;

import androidx.annotation.NonNull;

import com.premar.muvi.R;
import com.premar.muvi.adapter.PersonMoviesAdapter;
import com.premar.muvi.api.ApiService;
import com.premar.muvi.api.ApiUtils;
import com.premar.muvi.model.Movie;
import com.premar.muvi.model.PersonMovie;
import com.premar.muvi.model.PersonMovieResponse;
import com.premar.muvi.model.people.Person;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppConstants {

    public AppConstants() {
    }

    public static final String API_KEY = "617e3f93f561c0a9a2b934055ba31e6a";
    public static final String IMAGE_URL_BASE_PATH="https://image.tmdb.org/t/p/w342//";
    public static final String BACKDROP_URL_BASE_PATH="https://image.tmdb.org/t/p/w500//";
    public static final String YOUTUBE_VIDEO_URL = "https://img.youtube.com/vi/";
    public static final String YOUTUBE_SUFFIX = "/0.jpg";
    public static final String ENGLISH_LANGUAGE = "en-US";
    public static final String WIKIPEDIA_PAGE_URL = "https://en.m.wikipedia.org/wiki/";

    public static final int MOVIE_RETRIEVE = 110;
    public static final int MOVIE_INSERT = 111;
    public static final int MOVIE_RETRIEVE_SUCCESS = 115;
    public static final int MOVIE_RETRIEVE_FAIL = 116;
    public static final int MOVIE_DELETE = 120;
    public static final int MOVIE_DELETE_SUCCESS = 121;
    public static final int MOVIE_DELETE_FAIL = 122;

    public static final String POPULARITY_DESC = "popularity.desc";
    public static Movie movie;


    /**
     * @param value
     * @param places
     * @return rounded value to a desired decimal place
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String getYear(String releaseDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(releaseDate);
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        final String year = df.format(date);
        return year;
    }



    public static String formatDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = dateFormat.parse(date);
        return DateFormat.getDateInstance(DateFormat.LONG).format(dt);
    }

    public static String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + "h " + minutes + "m";
    }

    public static String formatCurrency(int money){
        NumberFormat formatter = new DecimalFormat("#,###");
        double currency = (double) money;
        String formattedCurrency = formatter.format(currency);
        return "$"+formattedCurrency;
    }


    public static String formatGender(int gender){
        if (gender == 1){
            return "Female";
        }
        else if(gender == 2){
            return "Male";
        }
        else {
            return "-";
        }
    }


    public static String formatStringtoUnderscore(String word){

        return word.replaceAll(" ", "_");
    }

    public static String getKnownAsName(List<String> names){
           return names.get(0);
    }

    public static Movie getSelectedMovie(int movieId){
        ApiUtils.getApiService().getMovie(movieId, API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                       movie = response.body();
                    }
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

        return movie;
    }

    public static String formatLanguage(String language){
        if (language.equals("en")){
            return "English";
        }
        else if(language.equals("fr")){
            return "French";
        }
        else if(language.equals("es")){
            return "Spanish";
        }
        else if(language.equals("kr")){
            return "Korean";
        }
        else if(language.equals("cs")) {
            return "Czech";
        }

        else if(language.equals("da")) {
            return "Danish";
        }

        else if(language.equals("ge")) {
            return "German";
        }

        else if(language.equals("fi")) {
            return "Finnish";
        }

        else if(language.equals("id")) {
            return "Indonesian";
        }

        else if(language.equals("it")) {
            return "Italian";
        }

        else if(language.equals("ja")) {
            return "Japanese";
        }

        else if(language.equals("la")) {
            return "Latin";
        }

        else if(language.equals("ml")) {
            return "Malayalam";
        }

        else if(language.equals("mn")) {
            return "Mongolian";
        }

        else if(language.equals("ne")) {
            return "Nepali";
        }

        else if(language.equals("nl")) {
            return "Dutch";
        }

        else if(language.equals("no")) {
            return "Norwegian";
        }

        else if(language.equals("ru")) {
            return "Russian";
        }

        else if(language.equals("ta")) {
            return "Tamil";
        }

        else if(language.equals("te")) {
            return "Telugu";
        }
        else if(language.equals("th")) {
            return "Thai";
        }

        else if(language.equals("uk")) {
            return "Ukrainian";
        }

        else if(language.equals("pt")) {
            return "Portuguese";
        }

        else if(language.equals("ur")) {
            return "Urdu";
        }

        else if(language.equals("vi")) {
            return "Vietnamese";
        }

        else if(language.equals("zh")) {
            return "Mandarin";
        }

        else if(language.equals("ar")) {
            return "Arabic";
        }

        else if(language.equals("bg")) {
            return "Bulgarian";
        }

        else if(language.equals("hi")) {
            return "Hindi";
        }

        else if(language.equals("pa")) {
            return "Punjabi";
        }

        else if(language.equals("bn")) {
            return "Bengali";
        }

        else if(language.equals("za")) {
            return "Zhuang";
        }

        else if(language.equals("cn")) {
            return "Cantonese";
        }
        else {
            return "-";
        }
    }
}
