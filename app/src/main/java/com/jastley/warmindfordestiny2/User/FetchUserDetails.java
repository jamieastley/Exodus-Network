package com.jastley.warmindfordestiny2.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by jamie on 21/3/18.
 */

public class FetchUserDetails extends AsyncTask<Context, Void, Boolean> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //Start ProgressBar spinner
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        //Hide loading
    }

    @Override
    protected Boolean doInBackground(Context... contexts) {

        Context context = contexts[0];
        SharedPreferences savedPrefs = context.getSharedPreferences("saved_prefs", context.MODE_PRIVATE);
        Long tokenAge = savedPrefs.getLong("token_age", 0);

        Long timeNow = System.currentTimeMillis();
        Long diff = timeNow - tokenAge;

        Long mins = TimeUnit.MILLISECONDS.toMinutes(diff);

        if (mins >= 60) { //Refresh access_token

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        }
        return true;
    }




}
