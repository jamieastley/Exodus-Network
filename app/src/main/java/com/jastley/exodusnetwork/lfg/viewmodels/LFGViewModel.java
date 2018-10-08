package com.jastley.exodusnetwork.lfg.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.api.models.Response_GetPublicFireteams;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.LFGRepository;

import javax.inject.Inject;

public class LFGViewModel extends AndroidViewModel {

    private LiveData<Response_GetPublicFireteams> fireteamsLiveData;

    @Inject
    LFGRepository lfgRepository;

    public LFGViewModel(@NonNull Application application) {
        super(application);
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<Response_GetPublicFireteams> getAllFireteams(String platform, String aType, String dRange, String slotFilter, String page) {
        fireteamsLiveData = lfgRepository.getAllPublicFireteams(platform, aType, dRange, slotFilter, page);
        return fireteamsLiveData;
    }
}
