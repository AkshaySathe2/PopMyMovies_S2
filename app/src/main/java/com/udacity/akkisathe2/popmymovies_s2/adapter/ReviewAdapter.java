package com.udacity.akkisathe2.popmymovies_s2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.akkisathe2.popmymovies_s2.R;
import com.udacity.akkisathe2.popmymovies_s2.model.Movie;
import com.udacity.akkisathe2.popmymovies_s2.model.Review;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 836158 on 23-05-2016.
 */
public class ReviewAdapter extends BaseAdapter {

    List<Review> reviewsList = new ArrayList<>();
    Context mContext;

    public ReviewAdapter(Context context, List<Review> reviews) {
        reviewsList.addAll(reviews);
        mContext = context;
    }


    @Override
    public int getCount() {
        return reviewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_review, null);
            // Now we can fill the layout with the right values
            viewHolder = new ViewHolder();
            viewHolder.reviewerName = (TextView) convertView.findViewById(R.id.txt_reviewer_name);
            viewHolder.review = (TextView) convertView.findViewById(R.id.txt_review);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.review.setText(reviewsList.get(position).getContent());
        viewHolder.reviewerName.setText(reviewsList.get(position).getAuthor());
        if (position % 2 == 0) {
            viewHolder.review.setBackgroundResource(R.color.light_gray);
        }

        return convertView;
    }


    private class ViewHolder {

        TextView reviewerName;
        TextView review;

    }
}
