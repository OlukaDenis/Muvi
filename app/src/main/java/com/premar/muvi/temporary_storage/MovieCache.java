package com.premar.muvi.temporary_storage;

public class MovieCache {
    public static int movieId;

    public static int tvId;

    public static String movieTitle;

    public static int personId;

    public static String wiki_profile_url;


    public static int getTvId() {
        return tvId;
    }

    public static void setTvId(int tvId) {
        MovieCache.tvId = tvId;
    }

    public static String youtube_trailer_id;
}
