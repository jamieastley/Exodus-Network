package com.jastley.warmindfordestiny2.repositories;

import com.jastley.warmindfordestiny2.api.BungieAPI;

import com.jastley.warmindfordestiny2.components.DaggerRetrofitComponent;
import com.jastley.warmindfordestiny2.database.MilestoneDAO;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class MilestoneRepository {

    private MilestoneDAO mMilestoneDao;


    @Inject
    @Named("bungieAuthRetrofit")
    Retrofit retrofit;

    public MilestoneRepository() {
        DaggerRetrofitComponent.builder().build();
    }

    public void getMilestones() {
        Disposable disposable = retrofit.create(BungieAPI.class).getMilestones()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_getMilestones -> {
                    //TODO
                }, throwable -> {
                    //TODO
                }, () -> {
                    //TODO
                });
    }


}
