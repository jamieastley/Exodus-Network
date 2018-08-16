package com.jastley.exodusnetwork.checklists;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.api.models.Response_GetChecklists;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.ChecklistsRepository;

import javax.inject.Inject;

public class ChecklistsViewModel extends ViewModel {

    private LiveData<Response_GetChecklists> latentMemories;
    private LiveData<Response_GetChecklists> ghostLore;
    private LiveData<Response_GetChecklists> journals;
    private LiveData<Response_GetChecklists> sleeperNodes;
    private LiveData<Response_GetChecklists> raidLairs;
    private LiveData<Response_GetChecklists> forsakenItems;

    @Inject
    ChecklistsRepository repository;

    public ChecklistsViewModel(@NonNull Application application) {
        App.getApp().getAppComponent().inject(this);
    }

    public void loadChecklistData() {
        repository.getChecklistProgression();
    }

    public LiveData<Response_GetChecklists> getLatentMemories() {
        return latentMemories;
    }

    public LiveData<Response_GetChecklists> getGhostLore() {
        return ghostLore;
    }

    public LiveData<Response_GetChecklists> getJournals() {
        return journals;
    }

    public LiveData<Response_GetChecklists> getSleeperNodes() {
        return sleeperNodes;
    }

    public LiveData<Response_GetChecklists> getRaidLairs() {
        return raidLairs;
    }

    public LiveData<Response_GetChecklists> getForsakenItems() {
        return forsakenItems;
    }

    public void dispose() {
        repository.dispose();
    }
}
