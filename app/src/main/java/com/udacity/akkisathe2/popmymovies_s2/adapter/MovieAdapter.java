package com.udacity.akkisathe2.popmymovies_s2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.akkisathe2.popmymovies_s2.model.Movie;
import com.udacity.akkisathe2.popmymovies_s2.utility.UrlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 836158 on 01-04-2016.
 */
public class MovieAdapter extends BaseAdapter {

    List<Movie> movieList = new ArrayList<>();
    Context mContext;

    public MovieAdapter(Context context, List<Movie> movies) {
        movieList.addAll(movies);
        mContext = context;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Movie getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext).load(UrlBuilder.buildPosterUrl("w185")+movie.getPosterPath()).into(imageView);
        return imageView;
    }


}
