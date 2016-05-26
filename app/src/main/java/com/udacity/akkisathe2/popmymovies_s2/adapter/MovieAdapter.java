package com.udacity.akkisathe2.popmymovies_s2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.akkisathe2.popmymovies_s2.R;
import com.udacity.akkisathe2.popmymovies_s2.controller.MovieController;
import com.udacity.akkisathe2.popmymovies_s2.model.Movie;
import com.udacity.akkisathe2.popmymovies_s2.utility.UrlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 836158 on 01-04-2016.
 */
public class MovieAdapter extends BaseAdapter {

    List<Movie> movieList = new ArrayList<>();
    List<Movie> favMovie=new ArrayList<>();
    Context mContext;

    public MovieAdapter(Context context, List<Movie> movies) {
        movieList.addAll(movies);
        mContext = context;
        favMovie.addAll(new MovieController(mContext).fetchFavMoviesFromDB());
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
        ViewHolder viewHolder;
        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_movie, null);
            viewHolder = new ViewHolder();
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.img_movie_poster);
            viewHolder.favIcon = (ImageView) convertView.findViewById(R.id.btm_img_add_to_favourites);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlBuilder.buildPosterUrl("w185")+movie.getPosterPath()).into(viewHolder.poster);
        Boolean b=checkIfIDExists(movie.getId());
        viewHolder.favIcon.setImageResource(b?R.drawable.ic_favorite_white_24dp:R.drawable.ic_favorite_border_white_24dp);
        return convertView;
    }

    private Boolean checkIfIDExists(String id) {
        Boolean matches=false;
        for (Movie m:favMovie)
        {
            if(m.getId().equalsIgnoreCase(id))
            {
                matches=true;
                break;
            }
        }
        return matches;
    }


    private class ViewHolder {
        ImageView poster;
        ImageView favIcon;
    }
}
