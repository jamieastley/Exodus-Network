package com.jastley.exodusnetwork.Vendors.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.Vendors.XurRepository;
import com.jastley.exodusnetwork.Vendors.models.SocketModel;
import com.jastley.exodusnetwork.Vendors.models.XurVendorModel;
import com.jastley.exodusnetwork.api.models.Response_GetXurWeekly;
import com.jastley.exodusnetwork.api.models.XurSaleItemModel;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;

import javax.inject.Inject;

public class XurViewModel extends AndroidViewModel {

    @Inject
    XurRepository mXurRepository;

    private LiveData<XurSaleItemModel> xurData;

    private LiveData<Response_GetXurWeekly> xurItemList;
    private LiveData<XurVendorModel> xurLocationData;

    private InventoryItemJsonData itemDetailsModel;

    //Item inspect/stats


    public XurViewModel(@NonNull Application application) {
        super(application);

        App.getApp().getAppComponent().inject(this);

//        xurItemList = mXurRepository.getXurInventory();
//        xurLocationData = mXurRepository.getXurData();
    }

//    public LiveData<Response_GetXurWeekly> getXurData() {
//        if(xurItemList == null) {
//            xurItemList = mXurRepository.getXurInventory();
//        }
//        return xurItemList;
//    }

    public LiveData<XurVendorModel> getXurLocationData() {
        if(xurLocationData == null) {
            xurLocationData = mXurRepository.getXurLocationData();
        }
        return xurLocationData;
    }


    //Item inspect/stats
    public void setItemDetailsModel(InventoryItemJsonData itemDetailsModel) {
        this.itemDetailsModel = itemDetailsModel;
    }

    public InventoryItemJsonData getItemDetailsModel() {
        return itemDetailsModel;
    }

    public LiveData<InventoryItemJsonData> getInventoryItemData() {
        return mXurRepository.getInventoryItemData(itemDetailsModel.getHash());
    }

    public LiveData<SocketModel> getPerkSockets() {
        return mXurRepository.getPerkSockets();
    }

    public LiveData<SocketModel> getModSockets() {
        return mXurRepository.getModSockets();
    }

    public LiveData<SocketModel.StatValues> getStatData() {
        return mXurRepository.getStatData();
    }

    public LiveData<XurSaleItemModel> getXurData() {
        if(xurData == null) {
            mXurRepository.getXurVendor();
        }
        return mXurRepository.getXurSaleLiveDataModel();
    }
}
