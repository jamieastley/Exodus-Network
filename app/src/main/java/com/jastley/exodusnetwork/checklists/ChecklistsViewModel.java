package com.jastley.exodusnetwork.checklists;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.api.models.Response_GetChecklists;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.ChecklistsRepository;

import javax.inject.Inject;

public class ChecklistsViewModel extends AndroidViewModel {

    private LiveData<Response_GetChecklists> latentMemories;
    private LiveData<Response_GetChecklists> ghostLore;
    private LiveData<Response_GetChecklists> journals;
    private LiveData<Response_GetChecklists> sleeperNodes;
    private LiveData<Response_GetChecklists> raidLairs;

    private LiveData<Response_GetAllCharacters> characters;

    @Inject
    ChecklistsRepository repository;

    public ChecklistsViewModel(@NonNull Application application) {
        super(application);
        App.getApp().getAppComponent().inject(this);
    }

    public void loadChecklistData() {
        repository.getAccountChecklistProgression();
    }

    public LiveData<Response_GetChecklists> getLatentMemories() {
        this.latentMemories = repository.getLatentMemoriesChecklist();
        return latentMemories;
    }

    public LiveData<Response_GetChecklists> getGhostLore() {
        this.ghostLore = repository.getGhostLoreChecklist();
        return ghostLore;
    }

    public LiveData<Response_GetChecklists> getJournals() {
        this.journals = repository.getJournalsChecklist();
        return journals;
    }

    public LiveData<Response_GetChecklists> getSleeperNodes() {
        this.sleeperNodes = repository.getSleeperNodesChecklist();
        return sleeperNodes;
    }

    public LiveData<Response_GetChecklists> getRaidLairs() {
        this.raidLairs = repository.getRaidLairsChecklist();
        return raidLairs;
    }

    public LiveData<Response_GetAllCharacters> getCharacters() {
        this.characters = repository.getAccountData();
        return characters;
    }

    public void dispose() {
        repository.dispose();
    }
}
