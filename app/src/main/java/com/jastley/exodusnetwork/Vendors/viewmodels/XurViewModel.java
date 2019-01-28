package com.jastley.exodusnetwork.Vendors.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.jastley.exodusnetwork.Utils.SingleLiveEvent;
import com.jastley.exodusnetwork.Utils.SnackbarMessage;
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
    private LiveData<SnackbarMessage> snackbarMessage;
    private LiveData<Response_GetXurWeekly> xurItemList;
    private LiveData<XurVendorModel> xurLocationData;

    private InventoryItemJsonData itemDetailsModel;

    //Item inspect/stats


    public XurViewModel(@NonNull Application application) {
        super(application);

        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<XurVendorModel> getXurLocationData() {
        if(xurLocationData == null) {
            mXurRepository.getXurLocation();
            xurLocationData = mXurRepository.getLocationLiveData();
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

    public LiveData<InventoryItemJsonData> getInventoryItemData(String hash) {
        return mXurRepository.getInventoryItemData(hash);
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

    public LiveData<SnackbarMessage> getSnackbarMessage() {
        return snackbarMessage = mXurRepository.getSnackbarMessage();
    }

}
