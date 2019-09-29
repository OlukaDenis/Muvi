package com.premar.muvi.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConstants {
    public static final String API_KEY = "617e3f93f561c0a9a2b934055ba31e6a";
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    public static final String BACKDROP_URL_BASE_PATH="http://image.tmdb.org/t/p/w500//";
    public static final String YOUTUBE_VIDEO_URL = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_SUFFIX = "/0.jpg";
    public static final String ENGLISH_LANGUAGE = "en-US";
    public static final String WIKIPEDIA_PAGE_URL = "https://en.m.wikipedia.org/wiki/";

    public static final String POPULARITY_DESC = "popularity.desc";


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
        else {
            return "-";
        }
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

}
