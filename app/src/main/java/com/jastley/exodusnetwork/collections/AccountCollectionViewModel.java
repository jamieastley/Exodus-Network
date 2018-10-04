package com.jastley.exodusnetwork.collections;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jastley.exodusnetwork.Utils.LiveDataResponseModel;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.CollectionsRepository;

import javax.inject.Inject;

public class AccountCollectionViewModel extends ViewModel {

    @Inject
    CollectionsRepository collectionsRepository;

    private LiveData<LiveDataResponseModel> profileCollectibles;

    public AccountCollectionViewModel() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<LiveDataResponseModel> getProfileCollectibles() {
        collectionsRepository.getProfileCollectibles();
        return profileCollectibles = collectionsRepository.getCollectiblesResponse();
    }
}
