package com.jastley.warmindfordestiny2.Vendors.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jastley.warmindfordestiny2.Vendors.XurRepository;
import com.jastley.warmindfordestiny2.api.models.Response_GetXurWeekly;
import com.jastley.warmindfordestiny2.app.App;

import javax.inject.Inject;

public class XurViewModel extends AndroidViewModel {

    @Inject
    XurRepository mXurRepository;

    private LiveData<Response_GetXurWeekly> xurItemList;

    public XurViewModel(@NonNull Application application) {
        super(application);

        App.getApp().getAppComponent().inject(this);

        xurItemList = mXurRepository.getXurInventory();
    }

    public LiveData<Response_GetXurWeekly> getXurData() {
        return xurItemList;
    }

}
