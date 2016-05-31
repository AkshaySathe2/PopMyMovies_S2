package com.udacity.akkisathe2.popmymovies_s2.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.udacity.akkisathe2.popmymovies_s2.R;
import com.udacity.akkisathe2.popmymovies_s2.adapter.ReviewAdapter;
import com.udacity.akkisathe2.popmymovies_s2.adapter.TrailerAdapter;
import com.udacity.akkisathe2.popmymovies_s2.controller.MovieController;
import com.udacity.akkisathe2.popmymovies_s2.model.Movie;
import com.udacity.akkisathe2.popmymovies_s2.model.Review;
import com.udacity.akkisathe2.popmymovies_s2.model.Trailer;
import com.udacity.akkisathe2.popmymovies_s2.utility.UrlBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class MovieDetailActivity extends AppCompatActivity {

    static Movie movie;
    static String movieId;
    Context mContext;
    View view;
    TextView title;
    TextView overview;
    ImageView poster;
    TextView movieYear;
    TextView voteAverage;
    RelativeLayout review, trailer;
    ImageView adultIcon;
    ImageButton addToFavourites;
    private TextView tagline;
    LinearLayout genre;
    GridView gridGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra("movieId")) {
            movieId = getIntent().getStringExtra("movieId");
        }
        // Inflate the layout for this fragment
        mContext = getApplicationContext();
        title = (TextView) findViewById(R.id.txt_movie_title);
        tagline = (TextView) findViewById(R.id.txt_movie_tagline);
        overview = (TextView) findViewById(R.id.txt_movie_overview);
        LinearLayout moviePosterDetails = (LinearLayout) findViewById(R.id.ll_movie_poster_details);
        poster = (ImageView) findViewById(R.id.img_movie_poster);
        movieYear = (TextView) moviePosterDetails.findViewById(R.id.txt_movie_year);
        voteAverage = (TextView) moviePosterDetails.findViewById(R.id.txt_movie_rating);
        review = (RelativeLayout) findViewById(R.id.rel_reviews);
        trailer = (RelativeLayout) findViewById(R.id.rel_trailers);
        adultIcon = (ImageView) moviePosterDetails.findViewById(R.id.img_adult);
        addToFavourites = (ImageButton) findViewById(R.id.btm_img_add_to_favourites);
        Log.v("MovieDetailFragment", "MovieId  =" + movieId);
        //markAsFavourite=(TextView)findViewById(R.id.txt_mark_as_favourite);

        //genre=(LinearLayout) findViewById(R.id.ll_genre_tags);

        /*gridGenre=(GridView)findViewById(R.id.grid_genre_tags);*/

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.getReviews().size() > 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MovieDetailActivity.this);
                    dialog.setTitle("Reviews");
                    final ReviewAdapter adapter = new ReviewAdapter(
                            MovieDetailActivity.this, movie.getReviews());
                    dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setNegativeButton("Cancel", null);
                    dialog.show();
                } else {
                    Toast.makeText(mContext, "No review available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MovieDetailActivity.this);
                dialog.setTitle("Trailers");
                final TrailerAdapter adapter = new TrailerAdapter(
                        MovieDetailActivity.this, movie.getTrailers());
                dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Trailer t = movie.getTrailers().get(which);
                        if (t.getSite().equalsIgnoreCase("youtube")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + t.getKey()));// Checking if youtube app is present
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {//If an exception is thrown means no youtube app
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://www.youtube.com/watch?v=" + t.getKey()));
                                startActivity(intent);
                            }
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.show();
            }
        });

        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleFavouriteStatusTask task = new ToggleFavouriteStatusTask();
                task.execute();
            }
        });

        /*markAsFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieController controller=new MovieController();
                controller.toggleMovieFavouriteStatus(movie);
            }
        });*/

        if (savedInstanceState == null) {
            FetchMovieData data = new FetchMovieData();
            data.execute(movieId);
        } else {
            movie = savedInstanceState.getParcelable("movie");
            updateUI();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movie != null) {
            outState.putParcelable("movie", movie);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //setHasOptionsMenu(false);
    }

    public void showReviews(View view) {


    }


    public class FetchMovieData extends AsyncTask<String, Void, String[]> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MovieDetailActivity.this);
            dialog.setMessage("Loading data Please wait...");
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            try {
                MovieController controller = new MovieController(mContext);
                String jsonString = controller.fetchMovie(params[0]);
                if (!(jsonString != null && jsonString.trim().equals(""))) {
                    movie = new Gson().fromJson(jsonString, Movie.class);

                }
                jsonString = controller.fetchReviews(params[0]);
                if (!(jsonString == null || jsonString.trim().equals(""))) {
                    JSONObject object = new JSONObject(jsonString);
                    JSONArray results = object.getJSONArray("results");
                    Review[] reviews = new Gson().fromJson(results.toString(), Review[].class);
                    movie.setReviews(Arrays.asList(reviews));
                }
                jsonString = controller.fetchTrailer(params[0]);
                if (!(jsonString == null || jsonString.trim().equals(""))) {
                    JSONObject object = new JSONObject(jsonString);
                    JSONArray results = object.getJSONArray("results");
                    Trailer[] trailers = new Gson().fromJson(results.toString(), Trailer[].class);
                    movie.setTrailers(Arrays.asList(trailers));
                }
                Boolean isAddedToFavourites = controller.getAddToFavouriteStatus(params[0]);
                movie.setAddedToFavourites(isAddedToFavourites);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] strings) {
            dialog.dismiss();
            updateUI();
            super.onPostExecute(strings);
        }
    }

    public void updateUI() {
        title.setText(movie.getTitle());
        tagline.setText(movie.getTagline());
        overview.setText(movie.getOverview());
        Picasso.with(mContext).load(UrlBuilder.buildPosterUrl("w780") + movie.getBackdropPath()).placeholder(R.drawable.icon_dummy_movie).error(R.drawable.icon_dummy_movie).into(poster);
        movieYear.setText(movie.getReleaseDate());
        voteAverage.setText(getString(R.string.total_rating, movie.getVoteAverage()));
        if (Boolean.valueOf(movie.getAdult())) {
            adultIcon.setVisibility(View.VISIBLE);
        }
        addToFavourites.setImageResource(movie.getAddedToFavourites() ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp);
        /*String[] genres=movie.getGenreNames();*//*
        TextView txtGenre = new TextView(mContext);
        txtGenre.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        txtGenre.setText("Genres : ");
        //genre.addView(txtGenre);
        Movie.Genres[] genres=movie.getGenres();
        for (Movie.Genres genre1 : genres) {
            TextView textView1 = new TextView(mContext);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setText(genre1.getName());
            textView1.setBackgroundResource(R.drawable.genre_tag);
            //genre.addView(textView1);

        }

        //New Approach
        *//*ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                R.layout.item_genre, genres);
        gridGenre.setAdapter(adapter);*//*
        genre.setVisibility(View.VISIBLE);*/

    }


    private class ToggleFavouriteStatusTask extends AsyncTask<Void, Void, Long> {

        @Override
        protected Long doInBackground(Void... params) {
            MovieController controller = new MovieController(mContext);
            return controller.toggleMovieFavouriteStatus(movie);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if (aLong == 1) {
                Toast.makeText(mContext, "Movie removed from favourites", Toast.LENGTH_SHORT).show();
                addToFavourites.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            } else {
                Toast.makeText(mContext, "Movie add to favourites", Toast.LENGTH_SHORT).show();
                addToFavourites.setImageResource(R.drawable.ic_favorite_white_24dp);
            }
            Intent intent = new Intent("Favourites Event");
            // You can also include some extra data.
            intent.putExtra("MovieId", movie.getId());
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }
    }
}



