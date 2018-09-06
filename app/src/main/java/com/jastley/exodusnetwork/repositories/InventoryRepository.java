package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import com.jastley.exodusnetwork.Definitions;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.api.models.Response_GetCharacterInventory;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.AppDatabase;
import com.jastley.exodusnetwork.database.AppManifestDatabase;
import com.jastley.exodusnetwork.database.models.Account;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class InventoryRepository {

    private MutableLiveData<Response_GetAllCharacters> characterList = new MutableLiveData<>();
    private MutableLiveData<InventoryItemModel> firstSlotInventory = new MutableLiveData<>();
    private MutableLiveData<InventoryItemModel> secondSlotInventory = new MutableLiveData<>();
    private MutableLiveData<InventoryItemModel> thirdSlotInventory = new MutableLiveData<>();

    //Vault slot if account has max characters
    private MutableLiveData<InventoryItemModel> fourthSlotInventory = new MutableLiveData<>();

    @Inject
    @Named("Account")
    AppDatabase accountDatabase;

    @Inject
    AppManifestDatabase manifestDatabase;

    @Inject
    @Named("bungieAuthRetrofit")
    Retrofit authRetrofit;

    @Inject
    @Named("savedPrefs")
    SharedPreferences sharedPreferences;

    @Inject
    public InventoryRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<Response_GetAllCharacters> getAccountCharacters() {

        Disposable disposable = accountDatabase.getAccountDAO().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(accounts -> {

                    String tempMId = "0";
                    String tempMType = "0";

                    String selectedPlatform = sharedPreferences.getString("selectedPlatform", "");
                    List<Response_GetAllCharacters.CharacterData> tempCharacterList = new ArrayList<>();


                    for (Account account : accounts) {

                        //Account table may have multiple platforms character data stored,
                        //only retrieve for the selected platform
                        if(account.getValue().getMembershipType().equals(selectedPlatform)) {
                            tempCharacterList.add(account.getValue());
                            tempMId = account.getValue().getMembershipId();
                            tempMType = account.getValue().getMembershipType();
                        }
                    }

                    //Create vault "character" to append on the end for vault storage
                    Response_GetAllCharacters.CharacterData vault = new Response_GetAllCharacters.CharacterData();
                    vault.setMembershipId(tempMId);
                    vault.setMembershipType(tempMType);
                    vault.setClassType("vault");

                    tempCharacterList.add(vault);

                    characterList.postValue(new Response_GetAllCharacters(tempCharacterList));
                });

        return characterList;
    }

    public void startItemRetrievel(int slot, boolean isVault) {

        String mType = sharedPreferences.getString("selectedPlatform", "");
        String mId = sharedPreferences.getString("membershipId" + mType, "");
        String cId = sharedPreferences.getString(mType+"characterId"+slot, "");

        if(isVault) {
            getVaultItems(slot, mType, mId);
        }
        else {
            getInventoryItems(slot, mType, mId, cId);
        }
    }

    private void getInventoryItems(int characterSlot, String mType, String mId, String cId) {

        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .getCharacterInventory(mType, mId, cId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(inventory -> {

                    if(!inventory.getErrorCode().equals("1")) {
                        //TODO push error
                    }
                    else {

                        //temp list to hold itemHashes
                        List<String> itemHashList = new ArrayList<>();
                        List<InventoryItemModel> itemList = new ArrayList<>();

                        //non-equipped items stored on character
                        for(Response_GetCharacterInventory.Items item : inventory.getResponse().getInventory().getData().getItems()) {

//                            int pos = inventory.getResponse().getInventory().getData().getItems().indexOf(item);

                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(item.getItemHash()));

                            InventoryItemModel itemModel = new InventoryItemModel();

                            String id = null;

                            itemModel.setItemHash(item.getItemHash());
                            itemModel.setPrimaryKey(UnsignedHashConverter.getPrimaryKey(item.getItemHash()));
                            itemModel.setBucketHash(item.getBucketHash());
                            itemModel.setSlot(Definitions.sortBuckets(item.getBucketHash()));

                            if(item.getItemInstanceId()!= null) {
                                id = item.getItemInstanceId();
                                itemModel.setItemInstanceId(item.getItemInstanceId());

                                if(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType() != null) {
                                    itemModel.setDamageType(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType());
                                }
                                itemModel.setIsEquipped(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getIsEquipped());
                                itemModel.setCanEquip(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getCanEquip());
                            }

                            try {
                                itemModel.setPrimaryStatValue(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat().getValue());
                            }
                            catch(Exception e) {
                                Log.e("INVENTORY_ITEMS", e.getLocalizedMessage());
                            }

                            itemModel.setSlot(Definitions.sortBuckets(item.getBucketHash()));
                            itemList.add(itemModel);
                        }

                        //equipped items stored on character
                        for(Response_GetCharacterInventory.Items equippedItem : inventory.getResponse().getEquipment().getEquipmentData().getEquipmentItems()) {

//                            int pos = inventory.getResponse().getEquipment().getEquipmentData().getEquipmentItems().indexOf(equippedItem);

                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(equippedItem.getItemHash()));

                            InventoryItemModel itemModel = new InventoryItemModel();


                            itemModel.setItemHash(equippedItem.getItemHash());
                            itemModel.setPrimaryKey(UnsignedHashConverter.getPrimaryKey(equippedItem.getItemHash()));
                            itemModel.setBucketHash(equippedItem.getBucketHash());
                            itemModel.setSlot(Definitions.sortBuckets(equippedItem.getBucketHash()));

                            if(equippedItem.getItemInstanceId()!= null) {
                                String id = equippedItem.getItemInstanceId();
                                itemModel.setItemInstanceId(equippedItem.getItemInstanceId());

                                if(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType() != null) {
                                    itemModel.setDamageType(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType());
                                }
                                if(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat() != null) {
                                    itemModel.setPrimaryStatValue(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat().getValue());
                                }
                                itemModel.setIsEquipped(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getIsEquipped());
                                itemModel.setCanEquip(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getCanEquip());


                            }

                            itemModel.setSlot(Definitions.sortBuckets(equippedItem.getBucketHash()));
                            itemList.add(itemModel);

//                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(equippedItem.getItemHash()));
//
//                            InventoryItemModel itemModel = new InventoryItemModel();
//                            itemModel.setItemData(equippedItem);
//
//                            if(equippedItem.getItemInstanceId() != null) {
//                                String id = equippedItem.getItemInstanceId();
//                                itemModel.setInstanceData(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id));
//                            }
//
//                            itemModel.setSlot(Definitions.sortBuckets(equippedItem.getBucketHash()));
//                            itemList.add(itemModel);
                        }

                        //Sort lists by primary key value
                        Collections.sort(itemHashList, ((s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1))));
                        Collections.sort(itemList, (inventoryItemModel, t1) -> Long.valueOf(inventoryItemModel.getPrimaryKey()).compareTo(Long.valueOf(t1.getPrimaryKey())));

                        getManifestData(characterSlot, itemHashList, itemList);

                    }

                }, throwable -> {
                    switch (characterSlot){
                        case 0:
                            firstSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 1:
                            secondSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 2:
                            thirdSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 3:
                            fourthSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                    }
                });
    }

    private void getManifestData(int characterSlot, List<String> hashes, List<InventoryItemModel> itemList) {

        Disposable disposable = manifestDatabase.getInventoryItemDAO()
                .getItemsListByKey(hashes)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(definitions -> {

                    //iterate through all database results
//                    for(DestinyInventoryItemDefinition definitionData : definitions) {
                    for(int k = 0; k < definitions.size(); k++) {

//                        int pos = definitions.indexOf(definitionData);

                        //iterate through each item acquired from API
                        for(int i = 0; i < itemList.size(); i++) {

                            if(itemList.get(i).getItemHash().equals(definitions.get(k).getValue().getHash())) {
//                                itemList.get(i).setDisplayProperties(definitionData.getValue().getDisplayProperties());
                                itemList.get(i).setItemName(definitions.get(k).getValue().getDisplayProperties().getName());
                                itemList.get(i).setItemTypeDisplayName(definitions.get(k).getValue().getItemTypeDisplayName());
                                try{
                                    Log.d("InventoryAPIListHash: ", itemList.get(i).getItemHash());
                                    Log.d("ManifestItemHash: ", definitions.get(k).getValue().getHash());
                                    itemList.get(i).setItemIcon(definitions.get(k).getValue().getDisplayProperties().getIcon());
                                }
                                catch(Exception e){
                                    Log.d("getManifestData: ", e.getLocalizedMessage());
                                }
                            }
                        }
                    }

                    //Sort by item type so it's ready to display
//                    Collections.sort(itemList, (inventoryItemModel, t1) -> inventoryItemModel.getSlot() - t1.getSlot());
                    switch (characterSlot){
                        case 0:
                            firstSlotInventory.postValue(new InventoryItemModel(itemList));
                            break;
                        case 1:
                            secondSlotInventory.postValue(new InventoryItemModel(itemList));
                            break;
                        case 2:
                            thirdSlotInventory.postValue(new InventoryItemModel(itemList));
                            break;
                        case 3:
                            fourthSlotInventory.postValue(new InventoryItemModel(itemList));
                            break;
                    }
                }, throwable -> {
                    switch (characterSlot){
                        case 0:
                            firstSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 1:
                            secondSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 2:
                            thirdSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 3:
                            fourthSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                    }
                });
    }

    private void getVaultItems(int characterSlot, String mType, String mId) {

        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .getVaultInventory(mType, mId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(vaultItems -> {

                    if(!vaultItems.getErrorCode().equals("1")) {
                        switch (characterSlot){
                            case 0:
                                firstSlotInventory.postValue(new InventoryItemModel(vaultItems.getMessage()));
                                break;
                            case 1:
                                secondSlotInventory.postValue(new InventoryItemModel(vaultItems.getMessage()));
                                break;
                            case 2:
                                thirdSlotInventory.postValue(new InventoryItemModel(vaultItems.getMessage()));
                                break;
                            case 3:
                                fourthSlotInventory.postValue(new InventoryItemModel(vaultItems.getMessage()));
                                break;
                        }
                    }
                    else {

                        //temp list to hold hashes
                        List<String> itemHashList = new ArrayList<>();
                        List<InventoryItemModel> itemList = new ArrayList<>();

                        /**no equipment in vault, just get all items from getProfileInventory(), NOT getInventory() */
//                        for(Response_GetCharacterInventory.Items vaultItem : vaultItems.getResponse().getProfileInventory().getData().getItems()) {
                            for(int i = 0; i < vaultItems.getResponse().getProfileInventory().getData().getItems().size(); i++) {
                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getItemHash()));

                            InventoryItemModel itemModel = new InventoryItemModel();

//                            itemModel.setItemData(vaultItem);

                            itemModel.setItemHash(vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getItemHash());
                            itemModel.setPrimaryKey(UnsignedHashConverter.getPrimaryKey(vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getItemHash()));
                            itemModel.setBucketHash(vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getBucketHash());
                            itemModel.setSlot(Definitions.sortBuckets(vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getBucketHash()));

                            if(vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getItemInstanceId() != null) {
                                String id = vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getItemInstanceId();
                                itemModel.setItemInstanceId(id);

                                if(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType() != null) {
                                    itemModel.setDamageType(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType());
                                }
                                if(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat() != null) {
                                    itemModel.setPrimaryStatValue(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat().getValue());
                                }

                                itemModel.setIsEquipped(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getIsEquipped());
                                itemModel.setCanEquip(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getCanEquip());

                            }



                            itemModel.setSlot(Definitions.sortBuckets(vaultItems.getResponse().getProfileInventory().getData().getItems().get(i).getBucketHash()));
                            itemList.add(itemModel);

                            //Sort list by primary key value
                            Collections.sort(itemHashList, (s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1)));

                            //Sort itemlist based on alphanumeric value of converted primary keys
                            Collections.sort(itemList, (inventoryItemModel, t1) ->
                                    Long.valueOf(inventoryItemModel.getPrimaryKey()).compareTo(Long.valueOf(t1.getPrimaryKey())));

                            getManifestData(characterSlot, itemHashList, itemList);
                        }
                    }

                }, throwable -> {
                    switch (characterSlot){
                        case 0:
                            firstSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 1:
                            secondSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 2:
                            thirdSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                        case 3:
                            fourthSlotInventory.postValue(new InventoryItemModel(throwable));
                            break;
                    }
                });
    }


    //Get liveData

    public MutableLiveData<InventoryItemModel> getFirstSlotInventory() {
        return firstSlotInventory;
    }

    public MutableLiveData<InventoryItemModel> getSecondSlotInventory() {
        return secondSlotInventory;
    }

    public MutableLiveData<InventoryItemModel> getThirdSlotInventory() {
        return thirdSlotInventory;
    }

    public MutableLiveData<InventoryItemModel> getFourthSlotInventory() {
        return fourthSlotInventory;
    }
}
