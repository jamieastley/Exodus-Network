package com.jastley.warmindfordestiny2.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.Response_GetProfile;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jastley.warmindfordestiny2.api.apiKey.apiKey;

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

        String baseURL = "https://www.bungie.net/";
        Context context = contexts[0];
//        SharedPreferences savedPrefs = context.getSharedPreferences("saved_prefs", context.MODE_PRIVATE);

        String membershipType = "2";
        String membershipId = "4611686018428911554";
//                savedPrefs.getString("membershipId"+membershipType, "");

//        String[] characterIds;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("X-API-Key", apiKey);

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        Retrofit retrofit = builder.build();

        BungieAPI bungieClient = retrofit.create(BungieAPI.class);
        Call<JsonElement> getProfileCall = bungieClient.getProfile();

        getProfileCall.enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                JsonParser jsonParse = new JsonParser();
                JsonElement json = response.body();
                JsonObject responseObj = (JsonObject) json;


                for(Iterator iterator = responseObj.getAsJsonObject("Response").getAsJsonObject("characters").getAsJsonObject("data").keySet().iterator(); iterator.hasNext();) {
                    String key = (String)iterator.next();
                    JsonObject characterIdObj = (JsonObject) responseObj.getAsJsonObject("Response").getAsJsonObject("characters").getAsJsonObject("data").get(key);

                    //TODO: write each characterObj toString() and store in db
                    String emblemPath = characterIdObj.get("emblemPath").getAsString();
                    System.out.println("Emblem path: " +emblemPath);
                    String something = "";
                }

//                while(iterator.hasNext()){
//
//                }
//                String characterId = (responseObj.getAsJsonObject("Response").getAsJsonObject("characters").getAsJsonObject("data").getAsJsonObject(get) getAsJsonArray("characters").get(0))
//                System.out.println(json);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


        return true;
    }




}
