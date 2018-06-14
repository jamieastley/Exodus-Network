package com.jastley.warmindfordestiny2.Milestones.ViewModels;

import com.jastley.warmindfordestiny2.Milestones.models.MilestoneModel;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.Respository;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class MilestonesViewModel extends ViewModel {
    // TODO: Implement the ViewModel

//    private MilestoneRepository repository;
    private Context mContext;


    public MilestonesViewModel(){

    }

//    BungieAPI mBungieAPI = RetrofitHelper.getAuthBungieAPI(mContext, baseURL);

//    public List<MilestoneModel> getMilestones() {
//
//        mBungieAPI.getMilestones()
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(response_getMilestones -> {
//
//
//                });
//    }
}
