package com.jastley.warmindfordestiny2.api;

import android.content.Context;

import com.jastley.warmindfordestiny2.api.models.Response_GetMilestones;

import io.reactivex.Observable;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class Respository {

    private BungieAPI bungieAPI;

    public Respository(BungieAPI bungieAPI) {
        this.bungieAPI = bungieAPI;
    }
//
//    public Observable<Response_GetMilestones> getMilestonesObservable() {
//
//        return bungieAPI = RetrofitHelper.getBungieAPI(baseURL, ).getMilestones();
//    }
}
