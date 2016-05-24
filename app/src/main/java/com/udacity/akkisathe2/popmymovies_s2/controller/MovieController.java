package com.udacity.akkisathe2.popmymovies_s2.controller;

import com.udacity.akkisathe2.popmymovies_s2.loader.Dataloader;
import com.udacity.akkisathe2.popmymovies_s2.utility.UrlBuilder;

/**
 * Created by 836158 on 01-04-2016.
 */
public class MovieController {

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

}
