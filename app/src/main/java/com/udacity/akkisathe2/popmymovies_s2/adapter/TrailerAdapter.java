package com.udacity.akkisathe2.popmymovies_s2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.udacity.akkisathe2.popmymovies_s2.R;
import com.udacity.akkisathe2.popmymovies_s2.model.Review;
import com.udacity.akkisathe2.popmymovies_s2.model.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 836158 on 24-05-2016.
 */
public class TrailerAdapter extends BaseAdapter {

    List<Trailer> trailersList = new ArrayList<>();
    Context mContext;

    public TrailerAdapter(Context context, List<Trailer> reviews) {
        trailersList.addAll(reviews);
        mContext = context;
    }

    @Override
    public int getCount() {
        return trailersList.size();
    }

    @Override
    public Object getItem(int position) {
        return trailersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_trailer, null);
            // Now we can fill the layout with the right values
            viewHolder = new ViewHolder();
            viewHolder.trailerName = (TextView) convertView.findViewById(R.id.txt_trailer_name);
            viewHolder.trailerSite = (TextView) convertView.findViewById(R.id.txt_trailer_site);
            viewHolder.trailerType = (TextView) convertView.findViewById(R.id.txt_trailer_type);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.trailerName.setText(trailersList.get(position).getName());
        viewHolder.trailerSite.setText(trailersList.get(position).getSite());
        viewHolder.trailerType.setText(trailersList.get(position).getType());

        return convertView;
    }

    private class ViewHolder {

        TextView trailerName;
        TextView trailerSite;
        TextView trailerType;

    }
}
