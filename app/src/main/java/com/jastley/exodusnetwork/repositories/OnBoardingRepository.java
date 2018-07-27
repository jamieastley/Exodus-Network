package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.onboarding.DownloadProgressModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

@Singleton
public class OnBoardingRepository {

    private MutableLiveData<DownloadProgressModel> downloadProgressModel = new MutableLiveData<>();

    @Inject
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public OnBoardingRepository() {
        App.getApp().getAppComponent().inject(this);
    }


}
