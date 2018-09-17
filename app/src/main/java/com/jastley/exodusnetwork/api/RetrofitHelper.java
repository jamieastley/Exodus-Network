package com.jastley.exodusnetwork.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.jastley.exodusnetwork.Utils.NetworkInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.jastley.exodusnetwork.api.apiKey.apiKey;

/**
 * Created by jamie1192 on 21/4/18.
 */

public class RetrofitHelper {

    /** SERVICE **/

    public static BungieAPI getBungieAPI(String baseURL, Context context) {
        final Retrofit retrofit = createRetrofit(baseURL, context);
        return retrofit.create(BungieAPI.class);
    }

    public static BungieAPI getAuthBungieAPI(Context context, String baseURL) {
        final Retrofit retrofit = createAuthRetrofit(context, baseURL);
        return retrofit.create(BungieAPI.class);
    }

    public static BungieAPI getOAuthRequestBungieAPI(String baseURL, Context context) {
        final Retrofit retrofit = createOAuthRetrofit(baseURL, context);
                return retrofit.create(BungieAPI.class);
    }


    /** INTERCEPTORS **/

    private static OkHttpClient createOkHttpClient(Context context) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new NetworkInterceptor(context));
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            final Request.Builder requestBuilder = original.newBuilder()
                    .header("X-API-Key", apiKey);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }

    private static OkHttpClient createAuthOkHttpClient(Context context) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new NetworkInterceptor(context));
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

    private static OkHttpClient createOAuthClient(Context context) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new NetworkInterceptor(context));
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            final Request.Builder requestBuilder = original.newBuilder();

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }


    /** RETROFIT **/

    private static Retrofit createRetrofit(String baseURL, Context context) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient(context))
                .build();
    }

    private static Retrofit createAuthRetrofit(Context context, String baseURL) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createAuthOkHttpClient(context))
                .build();
    }

    private static Retrofit createOAuthRetrofit(String baseURL, Context context) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOAuthClient(context))
                .build();
    }
}
