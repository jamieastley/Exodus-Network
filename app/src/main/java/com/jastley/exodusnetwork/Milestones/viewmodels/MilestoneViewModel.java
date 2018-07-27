package com.jastley.exodusnetwork.Milestones.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.api.models.Response_GetMilestones;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.MilestoneRepository;

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
