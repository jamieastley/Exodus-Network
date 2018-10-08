package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetPublicFireteams;
import com.jastley.exodusnetwork.app.App;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class LFGRepository {

    private MutableLiveData<Response_GetPublicFireteams> fireteamsList = new MutableLiveData<>();

    @Inject
    @Named("bungieRetrofit")
    Retrofit retrofit;

    @Inject
    public LFGRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<Response_GetPublicFireteams> getAllPublicFireteams(String platform, String aType, String dRange, String slotFilter, String page) {

        Disposable disposable = retrofit.create(BungieAPI.class)
                .getPublicFireteams(platform, aType, dRange, slotFilter, page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(fireteams -> {

                    if(!fireteams.getErrorCode().equals("1")) {
                        fireteamsList.postValue(new Response_GetPublicFireteams(fireteams.getMessage()));
                    }
                    else {
                        fireteamsList.postValue(new Response_GetPublicFireteams(fireteams.getResponse().getResultsList()));
                    }
                }, throwable -> fireteamsList.postValue(new Response_GetPublicFireteams(throwable)));

        return fireteamsList;
    }
}
