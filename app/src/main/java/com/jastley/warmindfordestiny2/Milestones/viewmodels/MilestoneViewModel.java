package com.jastley.warmindfordestiny2.Milestones.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.jastley.warmindfordestiny2.Milestones.models.MilestoneModel;
import com.jastley.warmindfordestiny2.api.models.Response_GetMilestones;
import com.jastley.warmindfordestiny2.app.App;
import com.jastley.warmindfordestiny2.repositories.MilestoneRepository;

import java.util.List;

import javax.inject.Inject;

public class MilestoneViewModel extends AndroidViewModel {

    private LiveData<Response_GetMilestones> milestoneManifestList;

    @Inject
    MilestoneRepository repository;


    public MilestoneViewModel(@NonNull Application application) {
        super(application);

        App.getApp().getAppComponent().inject(this);

        milestoneManifestList = repository.masterMethod();
    }

    public LiveData<Response_GetMilestones> getMilestones() {
        return milestoneManifestList;
    }

    public void refreshMilestones() {
        repository.refreshMilestones();
    }
}
