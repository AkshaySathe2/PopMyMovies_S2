package com.udacity.akkisathe2.popmymovies_s2.contract;

/**
 * Created by 836158 on 25-05-2016.
 */
public class MovieContract {

    // Created a class for each table in SQL Lite
    public static final String TABLE_FAVOURITE_MOVIE = "favourite_movie";
    // Common column names
    public static final String KEY_MOVIE_ID = "movie_id";
    public static final String KEY_POSTER_PATH = "poster_path";



    public static final String CREATE_TABLE_FAVOURITE_MOVIE = "CREATE TABLE " + TABLE_FAVOURITE_MOVIE
            + "("
            + KEY_MOVIE_ID + " INTEGER PRIMARY KEY,"
            + KEY_POSTER_PATH + " TEXT"
            + ")";
}
