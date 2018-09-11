package com.jastley.exodusnetwork.modules;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.exodusnetwork.BuildConfig;
import com.jastley.exodusnetwork.Utils.NetworkInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.jastley.exodusnetwork.api.apiKey.apiKey;

@Module
public class RetrofitModule {

    String mBaseURL = "https://www.bungie.net";
    String plumbingURL = "https://destiny.plumbing/en/";
    String braytech = "https://whatsxurgot.com";

    public RetrofitModule() {}

    /** RETROFIT **/

    @Provides
    @Singleton
    @Named("bungieRetrofit")
    Retrofit createRetrofit(Gson gson, Application application) {
        return new Retrofit.Builder()
                .baseUrl(mBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient(application))
                .build();
    }

    @Provides
    @Singleton
    @Named("bungieAuthRetrofit")
    Retrofit createAuthRetrofit(Gson gson, Application application) {
        return new Retrofit.Builder()
                .baseUrl(mBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createAuthOkHttpClient(application))
                .build();
    }

    @Provides
    @Singleton
    @Named("OAuthRetrofit")
    Retrofit createOAuthRetrofit(Gson gson, Application application) {
        return new Retrofit.Builder()
                .baseUrl(mBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOAuthClient(application))
                .build();
    }

    @Provides
    @Singleton
    @Named("braytechApi")
    Retrofit createBraytechRetrofit(Gson gson, Application application) {
        return new Retrofit.Builder()
                .baseUrl(braytech)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createBrayTechClient(application))
                .build();
    }

    /** GSON **/
    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    /** SERVICES **/

//    @Provides
//    @Singleton
//    @NonNull
//    BungieAPI getBungieAPI(Retrofit retrofit) {
//        return retrofit.create(BungieAPI.class);
//    }
//
//    @Provides
//    @Singleton
//    @Named("bungieAuthRetrofit")
//    @NonNull
//    BungieAPI getAuthBungieAPI(Context context, String baseURL) {
//        final Retrofit retrofit = createAuthRetrofit(context, baseURL);
//        return retrofit.create(BungieAPI.class);
//    }
//
//    @Provides
//    @Singleton
//    @NonNull
//    public static BungieAPI getOAuthRequestBungieAPI(String baseURL, Context context) {
//        final Retrofit retrofit = createOAuthRetrofit(baseURL, context);
//        return retrofit.create(BungieAPI.class);
//    }


    /** INTERCEPTORS **/

    @Provides
    @Singleton
    OkHttpClient createOkHttpClient(Application application) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new NetworkInterceptor(application.getApplicationContext()));
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            final Request.Builder requestBuilder = original.newBuilder()
                    .header("X-API-Key", apiKey);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }

    @Provides
    @Singleton
    OkHttpClient createBrayTechClient(Application application) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new NetworkInterceptor(application));
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            final Request.Builder requestBuilder = original.newBuilder()
                    .header("X-Api-Key", BuildConfig.braytechApiKey);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return httpClient.build();
    }

    @Provides
    @Singleton
    OkHttpClient createAuthOkHttpClient(Application application) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new NetworkInterceptor(application));
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            //Get access_token from shared_prefs
            SharedPreferences settings = application.getSharedPreferences("saved_prefs", MODE_PRIVATE);
            String accessToken = settings.getString("access_token", "");

            final Request.Builder requestBuilder = original.newBuilder()
                    .header("X-API-Key", apiKey)
                    .header("Authorization", "Bearer " + accessToken);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });


        return httpClient.build();
    }

    @Provides
    @Singleton
    OkHttpClient createOAuthClient(Application application) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new NetworkInterceptor(application));
        httpClient.addInterceptor(chain -> {
            final Request original = chain.request();

            final Request.Builder requestBuilder = original.newBuilder();

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }



}
