package com.udacity.akkisathe2.popmymovies_s2.loader;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 836158 on 25-05-2016.
 */
public class DBHelper extends SQLiteOpenHelper{

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
