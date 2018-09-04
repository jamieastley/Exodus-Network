package com.jastley.exodusnetwork.manifest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;
import com.jastley.exodusnetwork.repositories.ManifestRepository;

import javax.inject.Inject;

public class ManifestViewModel extends ViewModel {

    public final LiveData<PagedList<DestinyInventoryItemDefinition>> itemList;

    @Inject
    ManifestRepository mRepository;

    public ManifestViewModel() {
        App.getApp().getAppComponent().inject(this);
        this.itemList = mRepository.getPagedItemsList();
    }
}
