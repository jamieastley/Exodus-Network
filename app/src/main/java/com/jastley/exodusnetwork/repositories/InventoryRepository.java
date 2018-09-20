package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.jastley.exodusnetwork.Definitions;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Utils.SingleLiveEvent;
import com.jastley.exodusnetwork.Inventory.models.TransferEquipStatus;
import com.jastley.exodusnetwork.Utils.UnauthorizedException;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.EquipItemRequestBody;
import com.jastley.exodusnetwork.api.models.PostmasterTransferRequest;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.api.models.Response_GetCharacterInventory;
import com.jastley.exodusnetwork.api.models.TransferItemRequestBody;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.AppDatabase;
import com.jastley.exodusnetwork.database.AppManifestDatabase;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;
import com.jastley.exodusnetwork.database.models.Account;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;
import com.jastley.exodusnetwork.database.models.DestinyObjectiveDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import static com.jastley.exodusnetwork.Definitions.isLocked;
import static com.jastley.exodusnetwork.Definitions.isMasterwork;
import static com.jastley.exodusnetwork.Definitions.isTracked;
import static com.jastley.exodusnetwork.Definitions.pursuits;

public class InventoryRepository {

    private MutableLiveData<Response_GetAllCharacters> characterList = new MutableLiveData<>();
    private MutableLiveData<InventoryItemModel> firstSlotInventory = new MutableLiveData<>();
    private MutableLiveData<InventoryItemModel> secondSlotInventory = new MutableLiveData<>();
    private MutableLiveData<InventoryItemModel> thirdSlotInventory = new MutableLiveData<>();

    //Vault slot if account has max characters
    private MutableLiveData<InventoryItemModel> fourthSlotInventory = new MutableLiveData<>();

    private List<InventoryItemModel> firstItemList = new ArrayList<>();
    private List<InventoryItemModel> secondItemList = new ArrayList<>();
    private List<InventoryItemModel> thirdItemList = new ArrayList<>();
    private List<InventoryItemModel> fourthItemList = new ArrayList<>();

    //Item transfer/equip
    private SingleLiveEvent<TransferEquipStatus> transferEquipStatus = new SingleLiveEvent<>();

    //Modal
    private MutableLiveData<InventoryItemJsonData> singleInventoryItemData = new MutableLiveData<>();
    private MutableLiveData<InventoryItemJsonData> inventoryItemList = new MutableLiveData<>();
    private MutableLiveData<List<Response_GetCharacterInventory.Objectives.ObjectiveData.ItemObjectives>> objectiveData = new MutableLiveData<>();

    @Inject
    @Named("Account")
    AppDatabase accountDatabase;

    @Inject
    AppManifestDatabase manifestDatabase;

    @Inject
    Gson gson;

    @Inject
    @Named("bungieAuthRetrofit")
    Retrofit authRetrofit;

    @Inject
    @Named("savedPrefs")
    SharedPreferences sharedPreferences;

    @Inject
    AccountRepository accountRepository;

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

    public void startItemRetrieval(int slot, boolean isVault) {

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

    private List<InventoryItemModel> getSlotList(int slot) {
        switch (slot){
            case 0:
                return firstItemList;

            case 1:
                return secondItemList;

            case 2:
                return thirdItemList;

            case 3:
                return fourthItemList;
        }
        return new ArrayList<>();
    }

    private void setSlotList(int slot, List<InventoryItemModel> list) {
        switch (slot){
            case 0:
                firstItemList = list;
                break;
            case 1:
                secondItemList = list;
                break;

            case 2:
                thirdItemList = list;
                break;

            case 3:
                fourthItemList = list;
                break;
        }
    }

    private void getInventoryItems(int characterSlot, String mType, String mId, String cId) {

        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .getCharacterInventory(mType, mId, cId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(inventory -> {

                    if(!inventory.getErrorCode().equals("1")) {
                        sendErrorData(characterSlot, new InventoryItemModel(inventory.getMessage()));
                    }
                    else {

                        //temp list to hold itemHashes
                        List<String> itemHashList = new ArrayList<>();
                        List<InventoryItemModel> itemList = new ArrayList<>();

                        //non-equipped items stored on character
                        for(Response_GetCharacterInventory.Items item : inventory.getResponse().getInventory().getData().getItems()) {

                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(item.getItemHash()));

                            InventoryItemModel itemModel = new InventoryItemModel();

                            String id = null;

                            itemModel.setItemHash(item.getItemHash());
                            itemModel.setPrimaryKey(UnsignedHashConverter.getPrimaryKey(item.getItemHash()));
                            itemModel.setBucketHash(item.getBucketHash());
                            itemModel.setSlot(Definitions.sortBuckets(item.getBucketHash()));
                            itemModel.setIsLocked(item.getState() & isLocked);
                            itemModel.setMasterwork(item.getState() & isMasterwork);
                            itemModel.setTracked(item.getState() & isTracked);
                            itemModel.setQuantity(item.getQuantity());

                            if(item.getItemInstanceId()!= null) {
                                id = item.getItemInstanceId();
                                itemModel.setItemInstanceId(item.getItemInstanceId());

                                if(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType() != null) {
                                    itemModel.setDamageType(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType());
                                }
                                itemModel.setIsEquipped(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getIsEquipped());
                                itemModel.setCanEquip(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getCanEquip());

                                //Objective data
                                if(item.getBucketHash().equals(pursuits)) {
                                    try {
                                        itemModel.setObjectivesList(inventory.getResponse().getItemComponents().getObjectives().getData().get(id).getItemObjectives());
                                        List<String> pkList = new ArrayList<>();

                                        for(Response_GetCharacterInventory.Objectives.ObjectiveData.ItemObjectives obj
                                                : inventory.getResponse().getItemComponents().getObjectives().getData().get(id).getItemObjectives()) {
                                            pkList.add(UnsignedHashConverter.getPrimaryKey(obj.getObjectiveHash()));
                                        }
                                        itemModel.setObjectiveHashList(pkList);
                                    }
                                    catch(Exception e) {
                                        Log.e("OBJ_DATA", e.getLocalizedMessage());
                                    }
                                }
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

                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(equippedItem.getItemHash()));

                            InventoryItemModel itemModel = new InventoryItemModel();


                            itemModel.setItemHash(equippedItem.getItemHash());
                            itemModel.setPrimaryKey(UnsignedHashConverter.getPrimaryKey(equippedItem.getItemHash()));
                            itemModel.setBucketHash(equippedItem.getBucketHash());
                            itemModel.setSlot(Definitions.sortBuckets(equippedItem.getBucketHash()));
                            itemModel.setIsLocked(equippedItem.getState() & isLocked);
                            itemModel.setMasterwork(equippedItem.getState() & isMasterwork);
                            itemModel.setTracked(equippedItem.getState() & isTracked);
                            itemModel.setQuantity(equippedItem.getQuantity());

                            if(equippedItem.getItemInstanceId()!= null) {
                                String id = equippedItem.getItemInstanceId();
                                itemModel.setItemInstanceId(equippedItem.getItemInstanceId());

                                if(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType() != null) {
                                    itemModel.setDamageType(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType());
                                }
                                if(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat() != null) {
                                    itemModel.setPrimaryStatValue(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat().getValue());
                                }
                                //Check equipReasonFailure
                                itemModel.setCannotEquipReason(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getCannotEquipReason());
//                                if(cannotEquipReason == 0) {
//                                    itemModel.setCanEquip(true);
//                                }
                                itemModel.setIsEquipped(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getIsEquipped());
//                                else {
//                                    itemModel.setCanEquip(false);
//                                }
                                //Needed separately to set item border
                                itemModel.setIsEquipped(inventory.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getIsEquipped());
                            }

                            itemModel.setSlot(Definitions.sortBuckets(equippedItem.getBucketHash()));
                            itemList.add(itemModel);
                        }

                        //Sort lists by primary key value
                        Collections.sort(itemHashList, ((s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1))));
                        Collections.sort(itemList, (inventoryItemModel, t1) -> Long.valueOf(inventoryItemModel.getPrimaryKey()).compareTo(Long.valueOf(t1.getPrimaryKey())));

                        setSlotList(characterSlot, itemList);

                        getManifestData(characterSlot, itemHashList);

                    }

                }, throwable -> {
                    if(throwable instanceof HttpException) {

                        if(((HttpException) throwable).code() == 401) {
                            Log.e("Http", "401");
                            Log.d("GET_INVENTORY", throwable.getMessage());
                            accountRepository.refreshAccessToken();
                            getInventoryItems(characterSlot, mType, mId, cId);
                        }
                    }
                    else {
                        Crashlytics.logException(throwable);
                        //TODO change below to one single snackbar message
                        switch (characterSlot) {
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
                        sendErrorData(characterSlot, new InventoryItemModel(vaultItems.getMessage()));
                    }
                    else {

                        //temp list to hold hashes
                        List<String> itemHashList = new ArrayList<>();
                        List<InventoryItemModel> itemList = new ArrayList<>();

                        /**no equipment in vault, just get all items from getProfileInventory(), NOT getInventory() */
//                        for(Response_GetCharacterInventory.Items vaultItem : vaultItems.getResponse().getProfileInventory().getData().getItems()) {
                        for(Response_GetCharacterInventory.Items item : vaultItems.getResponse().getProfileInventory().getData().getItems()) {

                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(item.getItemHash()));

                            InventoryItemModel itemModel = new InventoryItemModel();

//                            itemModel.setItemData(vaultItem);

                            itemModel.setItemHash(item.getItemHash());
                            itemModel.setPrimaryKey(UnsignedHashConverter.getPrimaryKey(item.getItemHash()));
                            itemModel.setBucketHash(item.getBucketHash());
                            itemModel.setSlot(Definitions.sortBuckets(item.getBucketHash()));
                            itemModel.setIsLocked(item.getState() & isLocked);
                            itemModel.setMasterwork(item.getState() & isMasterwork);
                            itemModel.setTracked(item.getState() & isTracked);
                            itemModel.setQuantity(item.getQuantity());


                            if(item.getItemInstanceId() != null) {
                                String id = item.getItemInstanceId();
                                itemModel.setItemInstanceId(id);

                                //DON'T USE FOR LOOP HERE FROM ABOVE! ItemComponents() is a separate object within the Response object
                                if(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType() != null) {
                                    itemModel.setDamageType(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getDamageType());
                                }
                                if(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat() != null) {
                                    itemModel.setPrimaryStatValue(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getPrimaryStat().getValue());
                                }

                                //All items in vault have canEquip set to false, so check flag enum instead
                                itemModel.setCannotEquipReason(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getCannotEquipReason());
//                                if((cannotEquipReason & itemUnequippable) == 0) {
//                                    itemModel.setCanEquip(true);
//                                }

                                itemModel.setIsEquipped(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getIsEquipped());
//                                itemModel.setCanEquip(vaultItems.getResponse().getItemComponents().getInstances().getInstanceData().get(id).getCanEquip());
                            }



                            itemModel.setSlot(Definitions.sortBuckets(item.getBucketHash()));
                            itemList.add(itemModel);

                            //Sort list by primary key value
                            Collections.sort(itemHashList, (s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1)));

                            //Sort itemlist based on alphanumeric value of converted primary keys
                            Collections.sort(itemList, (inventoryItemModel, t1) ->
                                    Long.valueOf(inventoryItemModel.getPrimaryKey()).compareTo(Long.valueOf(t1.getPrimaryKey())));


                            setSlotList(characterSlot, itemList);

//                          getManifestData(characterSlot, itemHashList);
                        }

                        getManifestData(characterSlot, itemHashList);
                    }

                }, throwable -> {
                    if(throwable instanceof UnauthorizedException) {
                        Log.d("GET_INVENTORY", throwable.getMessage());
                        accountRepository.refreshAccessToken();
                        getVaultItems(characterSlot, mType, mId);
                    }
                    Crashlytics.logException(throwable);
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


    private void getManifestData(int characterSlot, List<String> hashes) {

        Disposable disposable = manifestDatabase.getInventoryItemDAO()
                .getItemsListByKey(hashes)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(definitions -> {

                    //iterate through all database results
                    List<InventoryItemModel> itemList = getSlotList(characterSlot);

                    for(DestinyInventoryItemDefinition definitionData : definitions) {


                        for (int i = 0; i < itemList.size(); i++) {
                            if(definitionData.getValue().getHash().equals(itemList.get(i).getItemHash())) {

                                itemList.get(i).setClassType(String.valueOf(definitionData.getValue().getClassType()));
                                itemList.get(i).setItemName(definitionData.getValue().getDisplayProperties().getName());
                                itemList.get(i).setItemTypeDisplayName(definitionData.getValue().getItemTypeDisplayName());
                                try {
                                    /** Destiny.DestinyAmmunitionType
                                     * Primary : 1
                                     * Special : 2
                                     * Heavy : 3
                                     */
                                    itemList.get(i).setAmmoType(definitionData.getValue().getEquippingBlock().getAmmoType());
                                } catch(Exception e) {
                                    Log.e("MANIFEST_AMMO_TYPE", e.getLocalizedMessage());
                                }

                                try{
                                    itemList.get(i).setItemIcon(definitionData.getValue().getDisplayProperties().getIcon());
                                } catch(Exception e){
                                    Log.d("getManifestData: ", e.getLocalizedMessage());
                                }

                                //Objective data
                                itemList.get(i).setDescription(definitionData.getValue().getDisplayProperties().getDescription());
                                itemList.get(i).setItemTypeDisplayName(definitionData.getValue().getItemTypeDisplayName());
                                itemList.get(i).setDisplaySource(definitionData.getValue().getDisplaySource());

                                if(definitionData.getValue() != null) {
                                    //TODO
                                }

                            }

                        }

                    }

                    //Sort by item type so it's ready to display
                    Collections.sort(itemList, (inventoryItemModel, t1) -> inventoryItemModel.getSlot() - t1.getSlot());
                    switch (characterSlot){
                        case 0:
                            firstSlotInventory.postValue(new InventoryItemModel(firstItemList));
                            break;
                        case 1:
                            secondSlotInventory.postValue(new InventoryItemModel(secondItemList));
                            break;
                        case 2:
                            thirdSlotInventory.postValue(new InventoryItemModel(thirdItemList));
                            break;
                        case 3:
                            fourthSlotInventory.postValue(new InventoryItemModel(fourthItemList));
                            break;
                    }
                }, throwable -> {
                    Crashlytics.logException(throwable);
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


    //For pursuits/bounties/quests
    public MutableLiveData<InventoryItemJsonData> getInventoryItem(String hash) {

        String primaryKey = UnsignedHashConverter.getPrimaryKey(hash);

        Disposable disposable = manifestDatabase.getInventoryItemDAO()
                .getItemByKey(primaryKey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(itemDefinition -> {

                    List<String> rewardHashes = new ArrayList<>();

                    //Get a list of hashes of the objective rewards (if any)
                    if(itemDefinition.getValue().getValue() != null) {
                        for(InventoryItemJsonData.Value.ItemValues reward : itemDefinition.getValue().getValue().getItemValuesList()) {
                            if(!reward.getItemHash().equals("0")) {
                                rewardHashes.add(UnsignedHashConverter.getPrimaryKey(reward.getItemHash()));
                            }
                        }
                        singleInventoryItemData.postValue(new InventoryItemJsonData(itemDefinition.getValue()));

                        getRewardItemList(rewardHashes);
                    }
                },
                throwable -> singleInventoryItemData.postValue(new InventoryItemJsonData(throwable)));

        return singleInventoryItemData;
    }

    public void getRewardItemList(List<String> hashes) {

        Disposable disposable = manifestDatabase.getInventoryItemDAO()
                .getItemsListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(definitionList -> {

                    List<InventoryItemJsonData> rewardList = new ArrayList<>();

                    for(DestinyInventoryItemDefinition item : definitionList) {
                        rewardList.add(item.getValue());
                    }

                    inventoryItemList.postValue(new InventoryItemJsonData(rewardList));

                }, throwable -> inventoryItemList.postValue(new InventoryItemJsonData(throwable)));
    }

    public MutableLiveData<List<Response_GetCharacterInventory.Objectives.ObjectiveData.ItemObjectives>> getObjectiveData(List<String> hashes,
                                                                                                                    List<Response_GetCharacterInventory.Objectives.ObjectiveData.ItemObjectives> objInstanceList) {

        Disposable disposable = manifestDatabase.getObjectiveDAO()
                .getObjectiveDefinitionList(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(objectiveList -> {

                    for(DestinyObjectiveDefinition objectiveData : objectiveList) {

                        for(int i = 0; i < objInstanceList.size(); i++) {

                            if(objectiveData.getValue().getHash().equals(objInstanceList.get(i).getObjectiveHash())) {
                                objInstanceList.get(i).setObjectiveDataName(objectiveData.getValue().getProgressDescription());
                            }
                        }
                    }

                    objectiveData.postValue(objInstanceList);
                }, throwable -> {
                    //TODO
                });

        return objectiveData;
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

    public MutableLiveData<InventoryItemJsonData> getRewardItemList() {
        return inventoryItemList;
    }

    private void sendErrorData(int slot, InventoryItemModel data) {

        if(data.getThrowable() != null) {
            switch (slot) {
                case 0:
                    firstSlotInventory.postValue(new InventoryItemModel(data.getThrowable()));
                    break;
                case 1:
                    secondSlotInventory.postValue(new InventoryItemModel(data.getThrowable()));
                    break;
                case 2:
                    thirdSlotInventory.postValue(new InventoryItemModel(data.getThrowable()));
                    break;
                case 3:
                    fourthSlotInventory.postValue(new InventoryItemModel(data.getThrowable()));
                    break;
            }
        }
        else if(data.getMessage() != null) {
            switch (slot) {
                case 0:
                    firstSlotInventory.postValue(new InventoryItemModel(data.getMessage()));
                    break;
                case 1:
                    secondSlotInventory.postValue(new InventoryItemModel(data.getMessage()));
                    break;
                case 2:
                    thirdSlotInventory.postValue(new InventoryItemModel(data.getMessage()));
                    break;
                case 3:
                    fourthSlotInventory.postValue(new InventoryItemModel(data.getMessage()));
                    break;
            }
        }
    }


    //Transfer/equipping starting point, if transferring this MUST be called first
    public void transferToVault(TransferItemRequestBody transferBody,
                                TransferItemRequestBody toCharacterBody,
                                EquipItemRequestBody equipBody,
                                boolean isEquipping,
                                boolean vaultToCharacter) {



        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .transferItem(transferBody)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {

                    if(!response.getErrorCode().equals("1")) {
                        //TODO error
                        transferEquipStatus.postValue(new TransferEquipStatus(response.getMessage()));
                    }
                    else {
                        //player clicked equip on an item from a different originating character, so
                        //first transfer to vault, then transfer to target character
                        if(isEquipping) {
                            transferToCharacter(toCharacterBody, equipBody, true);
                        }
                        //item is just being transferred between characters, so now send to target character
                        else if(!isEquipping && vaultToCharacter) {
                            transferToCharacter(toCharacterBody, null, false);
                        }
                        //Item was only being transferred to vault, we're done
                        else {
                            //TODO push success message
                            transferEquipStatus.postValue(new TransferEquipStatus("Item Transferred"));
                        }

                    }
                }, throwable -> {
                    //TODO catch 401 errors
                    transferEquipStatus.postValue(new TransferEquipStatus(throwable));
                });

    }

    public void transferToCharacter(TransferItemRequestBody transferBody,
                                    EquipItemRequestBody equipBody,
                                    boolean isEquipping) {


        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .transferItem(transferBody)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {

                    if(!response.getErrorCode().equals("1")) {
                        //TODO error
                        transferEquipStatus.postValue(new TransferEquipStatus(response.getMessage()));
                    }
                    else {
                        //item was transferred to character, now equip it
                        if(isEquipping) {
                            equipItem(equipBody, false);
                        }
                        //just transferring, we're done
                        else {
                            transferEquipStatus.postValue(new TransferEquipStatus("Item Transferred"));
                        }
                    }
                }, throwable -> {
                    //TODO catch 401 errors
                    transferEquipStatus.postValue(new TransferEquipStatus(throwable));
                });


    }


    public void equipItem(EquipItemRequestBody equipBody,
                          boolean sameCharacter) {

        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .equipItem(equipBody)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {

                    if(!response.getErrorCode().equals("1")) {
                        //TODO error
                    }
                    else {
                        //TODO generate particular message
                        if(sameCharacter) {
                            transferEquipStatus.postValue(new TransferEquipStatus("Item Equipped"));
                        }
                        else {
                            transferEquipStatus.postValue(new TransferEquipStatus("Item Equipped"));
                        }
                    }

                }, throwable -> {
                    //TODO catch 401 errors
                    transferEquipStatus.postValue(new TransferEquipStatus(throwable));
                });
    }

    public void pullFromPostmaster(PostmasterTransferRequest request) {

        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .pullFromPostmaster(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {

                    //error !=1
                    if(!response.getErrorCode().equals("1")) {
                        transferEquipStatus.postValue(new TransferEquipStatus(response.getMessage()));
                    }
                    else {
                        transferEquipStatus.postValue(new TransferEquipStatus("Item pulled from Postmaster"));
                    }

                    //TODO success

                }, throwable -> {
                    if(((HttpException) throwable).code() == 401) {
                        Log.e("Http", "401");
                        Log.d("GET_INVENTORY", throwable.getMessage());
                        accountRepository.refreshAccessToken();
                        pullFromPostmaster(request);
                    }
                    else {
                        transferEquipStatus.postValue(new TransferEquipStatus(throwable.getLocalizedMessage()));
                    }
                });
    }

    public LiveData<TransferEquipStatus> getTransferEquipStatus() {
        return transferEquipStatus;
    }
}
