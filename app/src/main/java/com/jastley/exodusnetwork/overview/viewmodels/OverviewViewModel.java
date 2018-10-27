package com.jastley.exodusnetwork.overview.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jastley.exodusnetwork.Utils.SingleLiveEvent;
import com.jastley.exodusnetwork.Utils.SnackbarMessage;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.api.models.Response_GetProfileOverview.ProgressionsData;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.OverviewRepository;

import java.util.List;

import javax.inject.Inject;

public class OverviewViewModel extends ViewModel {

    private LiveData<ProgressionsData> gloryLiveData;
    private LiveData<ProgressionsData> valorLiveData;
    private LiveData<ProgressionsData> infamyLiveData;
    private SingleLiveEvent<SnackbarMessage> snackbarMessage;
    private LiveData<List<Response_GetAllCharacters.CharacterData>> characterData;

    @Inject
    OverviewRepository repository;

    public OverviewViewModel() {
        App.getApp().getAppComponent().inject(this);
    }

    public void getProfileOverview() {
        repository.getProfileOverview();
    }

    public LiveData<ProgressionsData> getGloryLiveData() {
        return gloryLiveData = repository.getGloryProgression();
    }

    public LiveData<ProgressionsData> getValorLiveData() {
        return valorLiveData = repository.getValorProgression();
    }

    public LiveData<ProgressionsData> getInfamyLiveData() {
        return infamyLiveData = repository.getInfamyProgression();
    }

    public SingleLiveEvent<SnackbarMessage> getSnackbarMessage() {
        return snackbarMessage = repository.getSnackbarMessage();
    }

    public LiveData<List<Response_GetAllCharacters.CharacterData>> getCharacterData() {
        characterData = repository.getCharacterData();
        return characterData;
    }
}
