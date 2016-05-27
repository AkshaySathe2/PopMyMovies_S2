package com.udacity.akkisathe2.popmymovies_s2.utility;

import android.net.Uri;

/**
 * Created by 836158 on 01-04-2016.
 */
public class UrlBuilder {

    //for movie list
    private static final String baseUrl="api.themoviedb.org";
    private static final String firstParam="3";
    private static final String secondParam="movie";

    //for poster
    public static final String basePosterUrl="image.tmdb.org";
    private static final String firstPosterParam="t";
    private static final String secondPosterParam="p";


    public static String sortByPopular="popular";
    public static String sortByTopRated="top_rated";
    public static String sortByFavourites="favourites";
    public static String api_key="ffb27d728b00f6331cc5ea05f10af28d";
    private static String reviewUrl="reviews";
    private static String trailerUrl="videos";

    public static String buildMovieListUrl(String sortBy, String page)
    {
        //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=<<YOUR_API_KEY>>
        ///http://api.themoviedb.org/3/discover/movie/popular?api_key=<<YOUR_API_KEY>> or /movie/top_rated
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority(baseUrl)
                    .appendPath(firstParam)
                    .appendPath(secondParam)
                    .appendPath(sortBy)
                    .appendQueryParameter("api_key", api_key)
                    .appendQueryParameter("page",page);
            return builder.build().toString();
    }

    public static String buildMovieDetailUrl(String movieId)
    {
        //https://api.themoviedb.org/3/movie/209112/reviews?api_key=ffb27d728b00f6331cc5ea05f10af28d
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(baseUrl)
                .appendPath(firstParam)
                .appendPath(secondParam)
                .appendPath(movieId)
                .appendQueryParameter("api_key", api_key);
        return builder.build().toString();
    }


    public static String buildPosterUrl(String size)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(basePosterUrl)
                .appendPath(firstPosterParam)
                .appendPath(secondPosterParam)
                .appendPath(size);
        return builder.build().toString();
    }

    public static String buildMovieReviewUrl(String movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(baseUrl)
                .appendPath(firstParam)
                .appendPath(secondParam)
                .appendPath(movieId)
                .appendPath(reviewUrl)
                .appendQueryParameter("api_key", api_key);
        return builder.build().toString();
    }

    public static String buildMovieTrailerUrl(String movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(baseUrl)
                .appendPath(firstParam)
                .appendPath(secondParam)
                .appendPath(movieId)
                .appendPath(trailerUrl)
                .appendQueryParameter("api_key", api_key);
        return builder.build().toString();
    }

}
