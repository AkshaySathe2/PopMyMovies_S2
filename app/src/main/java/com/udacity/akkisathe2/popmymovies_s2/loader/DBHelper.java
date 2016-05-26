package com.udacity.akkisathe2.popmymovies_s2.loader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.BoringLayout;

import com.udacity.akkisathe2.popmymovies_s2.contract.MovieContract;
import com.udacity.akkisathe2.popmymovies_s2.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 836158 on 25-05-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "PopularMovies";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieContract.CREATE_TABLE_FAVOURITE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.CREATE_TABLE_FAVOURITE_MOVIE);
        onCreate(db);
    }

    public long toggleFavouriteMovieStatus(Movie m) {
        long todo_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieContract.KEY_MOVIE_ID, m.getId());
        values.put(MovieContract.KEY_POSTER_PATH, m.getPosterPath());
        todo_id = db.insert(MovieContract.TABLE_FAVOURITE_MOVIE, null, values);
        if (todo_id == -1) {
            todo_id = db.delete(MovieContract.TABLE_FAVOURITE_MOVIE, MovieContract.KEY_MOVIE_ID + "=" + m.getId(), null);
        }
        return todo_id;
    }


    public Boolean getFavouriteStatus(String movieId) {
        Boolean isEntryFound = false;
        String selectQuery = String.format("SELECT  * FROM %s where %s = '%s';", MovieContract.TABLE_FAVOURITE_MOVIE, MovieContract.KEY_MOVIE_ID, movieId);
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                isEntryFound = true;
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEntryFound;
    }

    public List<Movie> fetchFavMoviesFromDB() {
        List<Movie> list = new ArrayList<>();
        String selectQuery = String.format("SELECT  * FROM %s;", MovieContract.TABLE_FAVOURITE_MOVIE);
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {
                    Movie m = new Movie();
                    m.setId(c.getString((c.getColumnIndex(MovieContract.KEY_MOVIE_ID))));
                    m.setPosterPath(c.getString((c.getColumnIndex(MovieContract.KEY_POSTER_PATH))));
                    list.add(m);
                }
                while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
