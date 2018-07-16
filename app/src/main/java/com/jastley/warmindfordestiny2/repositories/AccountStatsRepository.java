package com.jastley.warmindfordestiny2.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.models.Response_GetAllModesAccountStats;
import com.jastley.warmindfordestiny2.app.App;
import com.jastley.warmindfordestiny2.modules.SharedPrefsModule;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.Retrofit;

@Singleton
public class AccountStatsRepository {

    private MutableLiveData<Response_GetAllModesAccountStats> accountStats;

    @Inject
    @Named("bungieAuthRetrofit")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public AccountStatsRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<Response_GetAllModesAccountStats> getAccountStats() {



//        retrofit.create(BungieAPI.class).getAllModesAccountStats()


        return accountStats;
    }

}
