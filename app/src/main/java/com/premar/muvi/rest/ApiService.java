package com.premar.muvi.rest;

import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;
import com.premar.muvi.model.credits.Credits;
import com.premar.muvi.model.people.PersonResponse;
import com.premar.muvi.model.trailers.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //movies
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse> getPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int id,
                         @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailers(@Path("movie_id") int vId,
                                           @Query("api_key") String api_key,
                                           @Query("language") String language);

    @GET("movie/{movie_id}/credits")
    Call<Credits> getCredits(@Path("movie_id") int cId,
                             @Query("api_key") String key);

    @GET("trending/movie/week")
    Call<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey);


    //tv shows

    @GET("tv/popular")
    Call<MovieResponse> getPopularShows(@Query("api_key") String apiKey);

    @GET("trending/tv/week")
    Call<MovieResponse> getTrendingShows(@Query("api_key") String apiKey);


    //people
    @GET("trending/person/week")
    Call<PersonResponse> getTrendingPeople(@Query("api_key") String apiKey);

}
