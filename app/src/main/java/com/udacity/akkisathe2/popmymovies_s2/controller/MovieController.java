package com.udacity.akkisathe2.popmymovies_s2.controller;

import android.content.Context;

import com.udacity.akkisathe2.popmymovies_s2.loader.DBHelper;
import com.udacity.akkisathe2.popmymovies_s2.loader.Dataloader;
import com.udacity.akkisathe2.popmymovies_s2.model.Movie;
import com.udacity.akkisathe2.popmymovies_s2.utility.UrlBuilder;

import java.util.List;

/**
 * Created by 836158 on 01-04-2016.
 */
public class MovieController {

    private Context mContext;
    public MovieController(Context context) {
        mContext=context;
    }

    public String fetchMovieList(String sortBy, String page) {

        String url = UrlBuilder.buildMovieListUrl(sortBy, page);
        return fetchData(url);
    }

    public String fetchMovie(String movieId) {
        String url = UrlBuilder.buildMovieDetailUrl(movieId);
        return fetchData(url);
    }


    public String fetchReviews(String movieId) {
        String url = UrlBuilder.buildMovieReviewUrl(movieId);
        return fetchData(url);

    }

    public String fetchTrailer(String movieId) {
        String url = UrlBuilder.buildMovieTrailerUrl(movieId);
        return fetchData(url);

    }


    private String fetchData(String url) {
        Dataloader dataloader = new Dataloader();
        return dataloader.fetchData(url);
    }

    public long toggleMovieFavouriteStatus(Movie m){
        DBHelper helper=new DBHelper(mContext);
        long status=helper.toggleFavouriteMovieStatus(m);
        return status;
    }

    public Boolean getAddToFavouriteStatus(String movieId) {
        DBHelper helper=new DBHelper(mContext);
        return helper.getFavouriteStatus(movieId);
    }

    public List<Movie> fetchFavMoviesFromDB() {
        DBHelper helper=new DBHelper(mContext);
        return helper.fetchFavMoviesFromDB();

    }
}
