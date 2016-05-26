package com.udacity.akkisathe2.popmymovies_s2.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.udacity.akkisathe2.popmymovies_s2.R;
import com.udacity.akkisathe2.popmymovies_s2.adapter.MovieAdapter;
import com.udacity.akkisathe2.popmymovies_s2.controller.MovieController;
import com.udacity.akkisathe2.popmymovies_s2.model.Movie;
import com.udacity.akkisathe2.popmymovies_s2.utility.UrlBuilder;
import com.udacity.akkisathe2.popmymovies_s2.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieActivityFragment extends Fragment {

    GridView listMovie;
    List<Movie> movieList;
    static int page = 1;
    private Context mContext;
    FetchMovieData task;
    ProgressDialog dialog;
    private boolean isLoading;
    int visibleItemNumber=0;
    public MovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mContext = getContext();
        movieList = new ArrayList();
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        Util.setSortByChoice(getContext(),UrlBuilder.sortByPopular);
        listMovie = (GridView) rootView.findViewById(R.id.grid_movie);
        listMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MovieDetailFragment fragment=MovieDetailFragment.newInstance(movieList.get(position));
                MovieDetailFragment fragment = MovieDetailFragment.newInstance(movieList.get(position).getId());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.movieActivity, fragment).addToBackStack("MovieActivityFragment").commit();
            }
        });
        listMovie.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading) {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount != 0 && totalItemCount != visibleItemCount) {
                        // End has been reached
                        visibleItemNumber=firstVisibleItem;
                        //Toast.makeText(getContext(), "End reached", Toast.LENGTH_SHORT).show();
                        task = new FetchMovieData();
                        task.execute(String.valueOf(page++));
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
        if (Util.isNetworkAvailable(getContext())) {
            task = new FetchMovieData();
            task.execute(String.valueOf(page++));
        } else {
            Toast.makeText(getContext(), "Exiting app. Please turn ON internet settings", Toast.LENGTH_SHORT).show();
            Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
            getActivity().startActivityForResult(settingsIntent, 9003);
            getActivity().finish();
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_top_rated:
                movieList.clear();
                page=1;
                visibleItemNumber=0;
                Util.setSortByChoice(getContext(), UrlBuilder.sortByTopRated);
                task = new FetchMovieData();
                task.execute(String.valueOf(page++));
                return true;
            case R.id.action_sort_by_popular:
                movieList.clear();
                visibleItemNumber=0;
                page=1;
                Util.setSortByChoice(getContext(),UrlBuilder.sortByPopular);
                task = new FetchMovieData();
                task.execute(String.valueOf(page++));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class FetchMovieData extends AsyncTask<String, Void, String[]> {


        @Override
        protected void onPreExecute() {
            isLoading = true;
            super.onPreExecute();
            dialog = new ProgressDialog(mContext);
            dialog.setCancelable(false);
            dialog.setMessage("Loading data Please wait...");
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String[] p = new String[]{"success"};
            try {
                if (Util.hasActiveInternetConnection(getContext())) {
                    MovieController controller = new MovieController(mContext);
                    String jsonString = controller.fetchMovieList(Util.getSortByChoice(getContext()), params[0]);
                    if (!(jsonString == null || jsonString.trim().equals(""))) {
                        JSONObject object = new JSONObject(jsonString);
                        JSONArray results = object.getJSONArray("results");
                        Movie[] movie = new Gson().fromJson(results.toString(), Movie[].class);
                        movieList.addAll(Arrays.asList(movie));
                    } else {
                        p[0] = "Unable to connect to server. Please try again later.";
                    }
                } else {
                    p[0] = "Unable to connect to server. Please try again later.";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return p;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if (strings[0].equalsIgnoreCase("success")) {
                MovieAdapter adapter = new MovieAdapter(getContext(), movieList);
                listMovie.setAdapter(adapter);
                listMovie.setSelection(visibleItemNumber);
                listMovie.smoothScrollToPosition(visibleItemNumber);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), strings[0], Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
            isLoading = false;
            super.onPostExecute(strings);
        }
    }


}


