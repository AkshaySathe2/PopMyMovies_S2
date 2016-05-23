package com.udacity.akkisathe2.popmymovies_s2.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 836158 on 14-04-2016.
 */
public class Util {

    public final static String LOG_TAG="Util";

    public static boolean hasActiveInternetConnection(Context context) {

        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(7000);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error checking internet connection", e);
            }
            catch (Exception ex){
                Log.e(LOG_TAG, "Error checking internet connection", ex);
            }
        } else {
            Log.d(LOG_TAG, "No network available!");
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private static SharedPreferences.Editor getSharedPreferenceEditor(Context mContext)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.edit();
    }

    private static SharedPreferences getSharedPreferenceInstance(Context mContext)
    {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static void setSortByChoice(Context context,String choice)
    {
        SharedPreferences.Editor editor=getSharedPreferenceEditor(context);
        editor.putString("SortByChoice", choice).commit();
    }

    public static String getSortByChoice(Context context)
    {
        SharedPreferences instance=getSharedPreferenceInstance(context);
        return instance.getString("SortByChoice", UrlBuilder.sortByPopular);
    }

}
