package com.jastley.warmindfordestiny2.Account.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jastley.warmindfordestiny2.api.models.Response_GetAllModesAccountStats;
import com.jastley.warmindfordestiny2.app.App;
import com.jastley.warmindfordestiny2.repositories.AccountStatsRepository;

import javax.inject.Inject;

public class AccountStatsViewModel extends AndroidViewModel {

    @Inject
    AccountStatsRepository repository;

    private LiveData<Response_GetAllModesAccountStats> pvpStatsList;
    private LiveData<Response_GetAllModesAccountStats> patrolStatsList;
    private LiveData<Response_GetAllModesAccountStats> raidStatsList;
    private LiveData<Response_GetAllModesAccountStats> storyStatsList;
    private LiveData<Response_GetAllModesAccountStats> allStrikesStatsList;

    private LiveData<Response_GetAllModesAccountStats> throwable;

    public AccountStatsViewModel(@NonNull Application application) {
        super(application);

        App.getApp().getAppComponent().inject(this);

        pvpStatsList = repository.getPvpStatsList();
        patrolStatsList = repository.getPatrolStatsList();
        raidStatsList = repository.getRaidStatsList();
        storyStatsList = repository.getStoryStatsList();
        allStrikesStatsList = repository.getAllStrikesStatsList();

        throwable = repository.getThrowable();
    }

    public LiveData<Response_GetAllModesAccountStats> getPvpStatsList() {
        return pvpStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getPatrolStatsList() {
        return patrolStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getRaidStatsList() {
        return raidStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getStoryStatsList() {
        return storyStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getAllStrikesStatsList() {
        return allStrikesStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getThrowable() {
        return throwable;
    }
}
