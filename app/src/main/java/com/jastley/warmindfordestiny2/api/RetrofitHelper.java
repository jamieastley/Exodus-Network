package com.jastley.warmindfordestiny2.api;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.jastley.warmindfordestiny2.api.apiKey.apiKey;

/**
 * Created by jamie1192 on 21/4/18.
 */

public class RetrofitHelper {

    private static String baseURL = "https://www.bungie.net";


    public BungieAPI getBungieAPI() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(BungieAPI.class);
    }

    public BungieAPI getAuthBungieAPI(Context context) {
        final Retrofit retrofit = createAuthRetrofit(context);
        return retrofit.create(BungieAPI.class);
    }


    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            final Request.Builder requestBuilder = original.newBuilder()
                    .header("X-API-Key", apiKey);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }


    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();

    }

    private OkHttpClient createAuthOkHttpClient(Context context) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            //Get access_token from shared_prefs
            SharedPreferences settings = context.getSharedPreferences("saved_prefs", MODE_PRIVATE);
            String accessToken = settings.getString("access_token", "");

            final Request.Builder requestBuilder = original.newBuilder()
                    .header("X-API-Key", apiKey)
                    .header("Authorization", "Bearer " + accessToken);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }

    private Retrofit createAuthRetrofit(Context context) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createAuthOkHttpClient(context))
                .build();
    }
}
