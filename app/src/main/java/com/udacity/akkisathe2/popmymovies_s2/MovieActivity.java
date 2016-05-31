package com.udacity.akkisathe2.popmymovies_s2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MovieActivity extends AppCompatActivity {

    public static Boolean mTwoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mTwoPane=findViewById(R.id.fragment_movie_detail)!=null;
    }

}
