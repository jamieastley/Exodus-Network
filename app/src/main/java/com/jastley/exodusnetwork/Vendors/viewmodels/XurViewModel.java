package com.jastley.exodusnetwork.Vendors.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.Vendors.XurRepository;
import com.jastley.exodusnetwork.Vendors.models.XurVendorModel;
import com.jastley.exodusnetwork.api.models.Response_GetXurWeekly;
import com.jastley.exodusnetwork.app.App;

import javax.inject.Inject;

public class XurViewModel extends AndroidViewModel {

    @Inject
    XurRepository mXurRepository;

    private LiveData<Response_GetXurWeekly> xurItemList;
    private LiveData<XurVendorModel> xurLocationData;

    public XurViewModel(@NonNull Application application) {
        super(application);

        App.getApp().getAppComponent().inject(this);

//        xurItemList = mXurRepository.getXurInventory();
//        xurLocationData = mXurRepository.getXurData();
    }

    public LiveData<Response_GetXurWeekly> getXurData() {
        if(xurItemList == null) {
            xurItemList = mXurRepository.getXurInventory();
        }
        return xurItemList;
    }

    public LiveData<XurVendorModel> getXurLocationData() {
        if(xurLocationData == null) {
            xurLocationData = mXurRepository.getXurLocationData();
        }
        return xurLocationData;
    }
}
