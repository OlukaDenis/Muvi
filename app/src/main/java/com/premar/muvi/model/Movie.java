package com.premar.muvi.model;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "favorite_movies")
public class Movie extends BaseObservable implements Serializable {
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    private boolean adult;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @Ignore
    @SerializedName("genres")
    private List<Genre> genres;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String originalTitle;

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String originalLanguage;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private double popularity;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int voteCount;

    @ColumnInfo(name = "video")
    @SerializedName("video")
    private boolean video;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;

    @ColumnInfo(name = "budget")
    @SerializedName("budget")
    private int budget;

    @ColumnInfo(name = "homepage")
    @SerializedName("homepage")
    private String homepage;

    @ColumnInfo(name = "imdb_id")
    @SerializedName("imdb_id")
    private String imdbId;

    @ColumnInfo(name = "revenue")
    @SerializedName("revenue")
    private int revenue;

    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    private int runtime;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status;

    @Ignore
    public Movie() {}

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, int id, String originalTitle, String originalLanguage, String title, String backdropPath, double popularity, Integer voteCount, boolean video, double voteAverage, int budget, String homepage, String imdbId, int revenue, int runtime, String status) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.budget = budget;
        this.homepage = homepage;
        this.imdbId = imdbId;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
    }

    @Bindable
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Bindable
    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Ignore
    public List<Genre> getGenres() {
        return genres;
    }

    @Ignore
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Bindable
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Bindable
    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Bindable
    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Bindable
    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    @Bindable
    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Bindable
    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Bindable
    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Bindable
    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Bindable
    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    @Bindable
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
