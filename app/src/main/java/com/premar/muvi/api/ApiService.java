package com.premar.muvi.api;

import com.premar.muvi.model.Movie;
import com.premar.muvi.model.MovieResponse;
import com.premar.muvi.model.PersonMovieResponse;
import com.premar.muvi.model.credits.Credits;
import com.premar.muvi.model.images.ImageResponse;
import com.premar.muvi.model.people.Person;
import com.premar.muvi.model.people.PersonResponse;
import com.premar.muvi.model.search.SearchResponse;
import com.premar.muvi.model.trailers.TrailerResponse;
import com.premar.muvi.model.tv.Tv;
import com.premar.muvi.model.tv.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //movies
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey,
                                          @Query("page") int page);

    @GET("movie/latest")
    Call<Movie> getLatestMovies(@Query("api_key") String apiKey,
                                @Query("page") int page);

    @GET("movie/now_playing")
    Call<MovieResponse> getPlayingMovies(@Query("api_key") String apiKey,
                                         @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey,
                                          @Query("page") int page);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey,
                                         @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int id,
                         @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailers(@Path("movie_id") int vId,
                                           @Query("api_key") String api_key,
                                           @Query("language") String language);

    @GET("movie/{movie_id}/images")
    Call<ImageResponse> getMovieImages(@Path("movie_id") int id,
                                       @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<Credits> getCredits(@Path("movie_id") int cId,
                             @Query("api_key") String key);

    @GET("trending/movie/week")
    Call<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey,
                                          @Query("page") int page);


    //tv shows

    @GET("tv/popular")
    Call<MovieResponse> getPopularShows(@Query("api_key") String apiKey);

    @GET("trending/tv/week")
    Call<TvResponse> getTrendingShows(@Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<Tv> getTvShow(@Path("tv_id") int id,
                       @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/videos")
    Call<TrailerResponse> getTvTrailers(@Path("tv_id") int vId,
                                        @Query("api_key") String api_key,
                                        @Query("language") String language);

    @GET("tv/{tv_id}/images")
    Call<ImageResponse> getTvImages(@Path("tv_id") int id,
                                    @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/credits")
    Call<Credits> getTvCredits(@Path("tv_id") int cId,
                               @Query("api_key") String key);


    //people
    @GET("trending/person/week")
    Call<PersonResponse> getTrendingPeople(@Query("api_key") String apiKey);

    @GET("person/{person_id}/movie_credits")
    Call<PersonMovieResponse> getPersonMovies(@Path("person_id") int personId,
                                              @Query("api_key") String apiKey);

    @GET("person/{person_id}")
    Call<Person> getPersonDetails(@Path("person_id") int personId,
                                  @Query("api_key") String apiKey);


    //Search
    @GET("search/multi")
    Call<SearchResponse> getSearchResults(@Query("api_key") String apiKey,
                                          @Query("query") String query);

    @GET("search/movie")
    Call<SearchResponse> searchMovies(@Query("api_key") String apiKey,
                                      @Query("include_adult") Boolean adult,
                                      @Query("query") String query);

}
