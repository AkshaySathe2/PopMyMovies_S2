package com.udacity.akkisathe2.popmymovies_s2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 836158 on 01-04-2016.
 */
public class Movie
{
    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("adult")
    private String adult;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("genre_ids")
    private String[] genreIds;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("vote_count")
    private String voteCount;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("video")
    private String video;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("budget")
    private String budget;

    @SerializedName("genres")
    private Genres[] genres;

    @SerializedName("status")
    private String status;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("revenue")
    private String revenue;

    private Boolean isAddedToFavourites;

    private List<Review> reviews;

    private List<Trailer> trailers;

    public Boolean getAddedToFavourites() {
        return isAddedToFavourites;
    }

    public void setAddedToFavourites(Boolean addedToFavourites) {
        isAddedToFavourites = addedToFavourites;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>();
        this.reviews.addAll(reviews);
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers=new ArrayList<>();
        this.trailers.addAll(trailers);
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String[] genreIds) {
        this.genreIds = genreIds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Genres[] getGenres() {
        return genres;
    }

    public void setGenres(Genres[] genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String[] getGenreNames()
    {
        Genres[] genres=getGenres();
        String[] genreString=new String[genres.length];
        for(int i=0;i<genres.length;i++)
        {
            genreString[i]=genres[i].getName();
        }
        return genreString;
    }



    @Override
    public String toString()
    {
        return "MoviePojo [budget = "+budget+", vote_average = "+voteAverage+", backdrop_path = "+backdropPath+", genres = "+genres+", status = "+status+", runtime = "+runtime+", adult = "+adult+", homepage = "+homepage+", id = "+id+", title = "+title+", overview = "+overview+", release_date = "+releaseDate+", original_title = "+originalTitle+", vote_count = "+voteCount+", poster_path = "+posterPath+", video = "+video+", tagline = "+tagline+", revenue = "+revenue+", popularity = "+popularity+"]";
    }

    public class Genres
    {
        private String id;

        private String name;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [id = "+id+", name = "+name+"]";
        }
    }


}
