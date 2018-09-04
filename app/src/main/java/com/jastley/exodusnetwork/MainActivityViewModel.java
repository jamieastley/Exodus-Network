package com.jastley.exodusnetwork;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import com.jastley.exodusnetwork.Utils.SnackbarMessage;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {

    private LiveData<SnackbarMessage> snackbarMessage;
    private LiveData<ArrayList<String>> platformSelector;
    private LiveData<AccountRepository.EmblemIconDownload> emblemDownload;

    @Inject
    AccountRepository mRepository;

    public MainActivityViewModel() {
        App.getApp().getAppComponent().inject(this);
    }


    public LiveData<SnackbarMessage> getSnackbarMessage() {
        return snackbarMessage = mRepository.getSnackbarMessage();
    }

    public LiveData<ArrayList<String>> getPlatformSelector() {
        return this.platformSelector = mRepository.getPlatformSelector();
    }

    public void getAccessToken(String code) {
        mRepository.getAccessToken(code);
    }

    public void refreshAccessToken() {
        mRepository.refreshAccessToken();
    }

    public boolean checkIsTokenExpired() {
        return mRepository.checkIsTokenExpired();
    }

    public void writeToSharedPrefs(String key, String value) {
        mRepository.writeToSharedPrefs(key, value);
    }

    public void getAllCharacters(String mType) {
        mRepository.getAllCharacters(mType);
    }

    public LiveData<AccountRepository.EmblemIconDownload> getEmblemDownload() {
        return emblemDownload = mRepository.getEmblemDownloadProgress();
    }

    public void logOut() {
        mRepository.logOut();
    }
}
