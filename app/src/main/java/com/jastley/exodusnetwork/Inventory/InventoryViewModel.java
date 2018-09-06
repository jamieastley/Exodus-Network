package com.jastley.exodusnetwork.Inventory;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.InventoryRepository;

import javax.inject.Inject;

public class InventoryViewModel extends ViewModel {

    private LiveData<InventoryItemModel> firstSlotInventory;
    private LiveData<InventoryItemModel> secondSlotInventory;
    private LiveData<InventoryItemModel> thirdSlotInventory;
    private LiveData<InventoryItemModel> fourthSlotInventory;

    @Inject
    InventoryRepository mRepository;

    private LiveData<Response_GetAllCharacters> accountList;
    //TODO store clicked item here for bottomModalSheet


    public InventoryViewModel() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<Response_GetAllCharacters> getAccountList() {
        return accountList = mRepository.getAccountCharacters();
    }

    public void startInventoryRetrieval(int slot, boolean isVault) {
        mRepository.startItemRetrievel(slot, isVault);
    }

    public LiveData<InventoryItemModel> getFirstSlotInventory() {
        return firstSlotInventory = mRepository.getFirstSlotInventory();
    }

    public LiveData<InventoryItemModel> getSecondSlotInventory() {
        return secondSlotInventory = mRepository.getSecondSlotInventory();
    }

    public LiveData<InventoryItemModel> getThirdSlotInventory() {
        return thirdSlotInventory = mRepository.getThirdSlotInventory();
    }

    public LiveData<InventoryItemModel> getFourthSlotInventory() {
        return fourthSlotInventory = mRepository.getFourthSlotInventory();
    }
}
