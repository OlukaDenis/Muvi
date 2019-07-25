package com.premar.muvi.constants;

public class AppConstants {
    public static final String API_KEY = "617e3f93f561c0a9a2b934055ba31e6a";
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    public static final String BACKDROP_URL_BASE_PATH="http://image.tmdb.org/t/p/w500//";
    public static final String YOUTUBE_VIDEO_URL = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_SUFFIX = "/0.jpg";
    public static final String ENGLISH_LANGUAGE = "en-US";

    public static final String POPULARITY_DESC = "popularity.desc";


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
