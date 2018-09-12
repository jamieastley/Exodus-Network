package com.jastley.exodusnetwork.Inventory;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Inventory.models.TransferEquipStatus;
import com.jastley.exodusnetwork.api.models.EquipItemRequestBody;
import com.jastley.exodusnetwork.api.models.PostmasterTransferRequest;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.api.models.TransferItemRequestBody;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.InventoryRepository;

import java.util.List;

import javax.inject.Inject;

public class InventoryViewModel extends ViewModel {

    private LiveData<InventoryItemModel> firstSlotInventory;
    private LiveData<InventoryItemModel> secondSlotInventory;
    private LiveData<InventoryItemModel> thirdSlotInventory;
    private LiveData<InventoryItemModel> fourthSlotInventory;


    //Item transfer
    private InventoryItemModel clickedItem;
    private List<Response_GetAllCharacters.CharacterData> accountList;
    private LiveData<TransferEquipStatus> getTransferEquipStatus;

    @Inject
    InventoryRepository mRepository;

    private LiveData<Response_GetAllCharacters> accountListLiveData;
    //TODO store clicked item here for bottomModalSheet


    public InventoryViewModel() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<Response_GetAllCharacters> getAccountLiveData() {
        return accountListLiveData = mRepository.getAccountCharacters();
    }

    public void startInventoryRetrieval(int slot, boolean isVault) {
        mRepository.startItemRetrieval(slot, isVault);
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



    //Item Transfer/equip modal
    public void setAccountList(List<Response_GetAllCharacters.CharacterData> accountList) {
        this.accountList = accountList;
    }

    public void setClickedItem(InventoryItemModel item) {
        this.clickedItem = item;
    }

    public InventoryItemModel getClickedItem() {
        return clickedItem;
    }

    public List<Response_GetAllCharacters.CharacterData> getAccountList() {
        return accountList;
    }


    //Item transfer/equip methods
    public void transferToVault(TransferItemRequestBody transferBody,
                                TransferItemRequestBody toCharacterBody,
                                EquipItemRequestBody equipBody,
                                boolean isEquipping,
                                boolean vaultToCharacter) {
        mRepository.transferToVault(transferBody, toCharacterBody, equipBody, isEquipping, vaultToCharacter);
    }

    public void transferToCharacter(TransferItemRequestBody transferBody,
                                    EquipItemRequestBody equipBody,
                                    boolean isEquipping) {
        mRepository.transferToCharacter(transferBody, equipBody, isEquipping);
    }

    public void equipItem(EquipItemRequestBody equipBody,
                          boolean sameCharacter) {
        mRepository.equipItem(equipBody, sameCharacter);
    }

    public void pullFromPostmaster(PostmasterTransferRequest request) {
        mRepository.pullFromPostmaster(request);
    }

    public LiveData<TransferEquipStatus> getTransferEquipStatus() {
        return getTransferEquipStatus = mRepository.getTransferEquipStatus();
    }
}
