package com.jastley.exodusnetwork.Vendors;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.exodusnetwork.Definitions;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.Vendors.models.SocketModel;
import com.jastley.exodusnetwork.Vendors.models.XurVendorModel;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetXurWeekly;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.dao.FactionDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.InventoryItemDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.StatDefinitionDAO;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemData;
import com.jastley.exodusnetwork.database.jsonModels.StatDefinitionData;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;
import com.jastley.exodusnetwork.database.models.DestinyStatDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.jastley.exodusnetwork.Definitions.armorModsSockets;
import static com.jastley.exodusnetwork.Definitions.armorPerksSockets;
import static com.jastley.exodusnetwork.Definitions.theNine;
import static com.jastley.exodusnetwork.Definitions.weaponModsSockets;
import static com.jastley.exodusnetwork.Definitions.weaponPerksSockets;
import static com.jastley.exodusnetwork.api.apiKey.braytechApiKey;

@Singleton
public class XurRepository {

    private MutableLiveData<Response_GetXurWeekly> xurLiveDataList;
    private MutableLiveData<XurVendorModel> xurLocationData = new MutableLiveData<>();
    private List<InventoryItemModel> xurItemsList = new ArrayList<>();
    private XurVendorModel vendorModel;

    //Item inspection data
    private MutableLiveData<InventoryItemData> inventoryItemJsonData = new MutableLiveData<>();
    private MutableLiveData<SocketModel> perksLiveData = new MutableLiveData<>();
    private MutableLiveData<SocketModel> modsLiveData = new MutableLiveData<>();
    private MutableLiveData<SocketModel.InvestmentStats> statsLiveData = new MutableLiveData<>();
    //TODO stats
    private List<SocketModel> perksModelList;// = new ArrayList<>();
    private List<SocketModel> modsModelList;// = new ArrayList<>();
    private List<SocketModel.InvestmentStats> statValuesList;

    private MutableLiveData<String> singleError = new MutableLiveData<>();

    @Inject
    @Named("braytechApi")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    FactionDefinitionDAO mFactionsDao;

    @Inject
    InventoryItemDefinitionDAO mInventoryItemDefinitionDAO;

    @Inject
    StatDefinitionDAO mStatDefinitionDAO;

    @Inject
    public XurRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<Response_GetXurWeekly> getXurInventory() {

        xurLiveDataList = new MutableLiveData<>();

        //get Xur inventory from WhatsXurGot API
        Disposable disposable = retrofit.create(BungieAPI.class).getXurWeeklyInventory(braytechApiKey, "history", "xur")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_getXurWeekly -> {

                            if(!response_getXurWeekly.getResponse().getStatus().equals("200")) {
                                xurLiveDataList.postValue(new Response_GetXurWeekly(response_getXurWeekly.getResponse().getMessage()));
                            }
                            else {

                                xurItemsList.clear();

                                try {
                                    String region = response_getXurWeekly.getResponse().getData().getLocation().getRegion();
                                    if(region.contains("&rsquo;")){
                                        region = region.replace("&rsquo;", "'");
                                    }

                                    vendorModel = new XurVendorModel(response_getXurWeekly.getResponse().getData().getLocation().getWorld(),
                                            region,
                                            response_getXurWeekly.getResponse().getData().getLocation().getId());

                                    int listSize = response_getXurWeekly.getResponse().getData().getItems().size();


                                    //add all items to List<>
                                    for(int i = 0; i < listSize; i++){

                                        InventoryItemModel xurItem = new InventoryItemModel();

                                        xurItem.setItemName(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getName());
                                        xurItem.setItemIcon(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getIcon());
                                        xurItem.setItemHash(response_getXurWeekly.getResponse().getData().getItems().get(i).getHash());
                                        xurItem.setItemTypeDisplayName(response_getXurWeekly.getResponse().getData().getItems().get(i).getItemTypeDisplayName());
                                        xurItem.setSaleHistoryCount(response_getXurWeekly.getResponse().getData().getItems().get(i).getSalesCount());
                                        xurItem.setDescription(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getDescription());

                                        try{ //if item has a cost
                                            xurItem.setCostItemIcon(response_getXurWeekly.getResponse().getData().getItems().get(i).getCost().getIcon());
                                            xurItem.setCostsQuantity(response_getXurWeekly.getResponse().getData().getItems().get(i).getCost().getQuantity());
                                        }
                                        catch(Exception er){
                                            Log.e("XUR_COST: ", er.getMessage());
                                        }

                                        try{
                                            xurItem.setEquippingBlock(response_getXurWeekly.getResponse().getData().getItems().get(i).getEquippingBlock().getDisplayStrings().get(0));
                                        }
                                        catch(Exception e){
                                            Log.e("XUR_EQUIP_BLOCK", String.valueOf(i));
                                        }

                                        xurItemsList.add(xurItem);

                                    }//end for loop

                                    xurLiveDataList.postValue(new Response_GetXurWeekly(xurItemsList));
                                    getLocationBanner(vendorModel);
                                }
                                catch(Exception e) {
                                    singleError.postValue("Error retrieving Xur data from server");
                                }
                            }

                        }, throwable -> xurLiveDataList.postValue(new Response_GetXurWeekly(throwable.getLocalizedMessage()))
                );

        return xurLiveDataList;
    }

    public LiveData<XurVendorModel> getXurLocationData() {
        return xurLocationData;
    }

    public LiveData<XurVendorModel> getLocationBanner(XurVendorModel vendorModel) {

        Disposable disposable = mFactionsDao.getFactionByKey(theNine, null)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(faction -> {

                    JsonParser parser = new JsonParser();
                    JsonObject factionObj = (JsonObject) parser.parse(faction.getValue());

                    int vendorIndex = Integer.parseInt(vendorModel.getLocationIndex());

                    //add in missing trailing slash from API url
                    vendorModel.setLocationBanner("/" + factionObj.get("vendors").getAsJsonArray().get(vendorIndex).getAsJsonObject().get("backgroundImagePath").getAsString());

                    xurLocationData.postValue(vendorModel);

                }, throwable -> {
                    vendorModel.setErrorMessage(throwable.getMessage());
                    xurLocationData.postValue(vendorModel);
                });

        return xurLocationData;
    }


    /** Item inspection data
    1. Lookup item in InventoryItemDefinition via itemHash
    2. collect statHashes into list, query StatDefinition for all stats
    3. separate lists for mods/perks, work out which is which via sockets.socketCategories[*].socketIndexes,
          sockets.socketCategories[*].socketCategoryHash refers to whether objects in slots are either mods/perks
            for either weapons or armor items

    4. use slotIndexes for ordering to output in correct display order (Room query returns data in alphanumeric order of PK)
    5. Query InventoryItemDefinitions for each mod/perks list and get names/icons to display
    **/

    //Getters to initialise observers
    public LiveData<SocketModel> getPerkSockets() {
        return perksLiveData;
    }

    public LiveData<SocketModel> getModSockets() {
        return modsLiveData;
    }

    public LiveData<SocketModel.InvestmentStats> getStatData() {
        return statsLiveData;
    }

    public LiveData<InventoryItemData> getInventoryItemData(String key) {

        String itemHash = UnsignedHashConverter.getPrimaryKey(key);

        Disposable disposable = mInventoryItemDefinitionDAO.getItemByKey(itemHash)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(destinyInventoryItemDefinition -> {

                    //Map item retrieved from DB to model class
                    try{
//                        InventoryItemData jsonData = gson.fromJson(destinyInventoryItemDefinition.getValue(), InventoryItemData.class);
//
//                        //trigger observer to get basic info (name/screenshot)
//                        inventoryItemJsonData.postValue(jsonData);
//
//
//
//                        //Perks list from sockets
//                        List<String> perkHashes = new ArrayList<>();
//                        List<String> modHashes = new ArrayList<>();
//                        List<String> statHashes = new ArrayList<>();
//
//                        //Initialise Lists here to prevent remembering last selected item
//                        perksModelList = new ArrayList<>();
//                        modsModelList = new ArrayList<>();
//                        statValuesList = new ArrayList<>();
//
//                        //Perk socket indexes
//                        List<Integer> perksSocketsIndexes = new ArrayList<>();
//                        List<Integer> modsSocketsIndexes = new ArrayList<>();
//                        for(int i = 0; i < jsonData.getSockets().getSocketCategoriesList().size(); i++) {
//
//                            //weapons/armor have DIFFERENT socketCategory hashes!
//                            if(jsonData.getSockets().getSocketCategoriesList().get(i).getSocketCategoryHash().equals(weaponPerksSockets) ||
//                                jsonData.getSockets().getSocketCategoriesList().get(i).getSocketCategoryHash().equals(armorPerksSockets)) {
//
//                                perksSocketsIndexes.addAll(jsonData.getSockets().getSocketCategoriesList().get(i).getSocketIndexes());
//
//                            }
//                            else if(jsonData.getSockets().getSocketCategoriesList().get(i).getSocketCategoryHash().equals(weaponModsSockets) ||
//                                    jsonData.getSockets().getSocketCategoriesList().get(i).getSocketCategoryHash().equals(armorModsSockets)) {
//
//                                modsSocketsIndexes.addAll(jsonData.getSockets().getSocketCategoriesList().get(i).getSocketIndexes());
//
//                            }
//                        }
//
//                        //Get perks
//                        for (int index: perksSocketsIndexes) {
//                            SocketModel plugItem = new SocketModel();
//                            plugItem.setPlugItemHash(jsonData.getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash());
//                            plugItem.setSocketIndex(index);
//                            perkHashes.add(UnsignedHashConverter.getPrimaryKey(jsonData.getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash()));
//
//                            perksModelList.add(plugItem);
//                        }
//                        getPerkPlugs(perkHashes);
//
//                        //Get mods
//                        for (int index: modsSocketsIndexes) {
//                            SocketModel plugItem = new SocketModel();
//                            plugItem.setPlugItemHash(jsonData.getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash());
//                            plugItem.setSocketIndex(index);
//                            modHashes.add(UnsignedHashConverter.getPrimaryKey(jsonData.getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash()));
//
//                            modsModelList.add(plugItem);
//                        }
//                        getModPlugs(modHashes);
//
//                        //Get stat values
//                        int positionCount = 1;
//                        for (InventoryItemData.InvestmentStats statValue : jsonData.getInvestmentStatsList()) {
//
//                            if(statValue.getValue() != 0){
//                                statHashes.add(UnsignedHashConverter.getPrimaryKey(statValue.getStatTypeHash()));
//
//                                SocketModel.InvestmentStats stat = new SocketModel.InvestmentStats(
//                                        statValue.getStatTypeHash(),
//                                        statValue.getValue(),
//                                        positionCount
//                                );
//                                positionCount++;
//                                statValuesList.add(stat);
//                            }
//                        }
//                        getStatData(statHashes);

                    }
                    catch(Exception e) {
                        singleError.postValue("Error retrieving item perks/mods");
                    }

                }, throwable -> {
                    modsLiveData.postValue(new SocketModel(throwable));
                    perksLiveData.postValue(new SocketModel(throwable));
                }, () -> {

                });

        return inventoryItemJsonData;
    }

    private void getPerkPlugs(List<String> hashes) {

        Disposable disposable = mInventoryItemDefinitionDAO.getItemsListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(plugList -> {

                    try {
//                        for(DestinyInventoryItemDefinition item: plugList) {
//                            InventoryItemData itemData = gson.fromJson(item.getValue(), InventoryItemData.class);
//
//                            for (int i = 0; i < perksModelList.size(); i++) {
//                                if(itemData.getHash().equals(perksModelList.get(i).getPlugItemHash())) {
//                                    perksModelList.get(i).setSocketName(itemData.getDisplayProperties().getName());
//                                    perksModelList.get(i).setSocketDescription(itemData.getDisplayProperties().getDescription());
//
//                                    if(itemData.getDisplayProperties().isHasIcon()) {
//                                        perksModelList.get(i).setSocketIcon(itemData.getDisplayProperties().getIcon());
//                                    }
//                                }
//                            }
//                        }
//                        //Sort list based on display order
//                        Collections.sort(perksModelList, (socketModel, t1) -> Long.compare(socketModel.getSocketIndex(), t1.getSocketIndex()));
//                        perksLiveData.postValue(new SocketModel(perksModelList));
                    }
                    catch(Exception e) {
                        singleError.postValue(e.getLocalizedMessage());
                    }

                }, throwable -> perksLiveData.postValue(new SocketModel(throwable)));
    }

    private void getModPlugs(List<String> hashes) {

        Disposable disposable = mInventoryItemDefinitionDAO.getItemsListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(plugList -> {

                    try {
//                        for(DestinyInventoryItemDefinition item: plugList) {
////                            InventoryItemData itemData = gson.fromJson(item.getValue(), InventoryItemData.class);
////
////                            for (int i = 0; i < modsModelList.size(); i++) {
////                                if(itemData.getHash().equals(modsModelList.get(i).getPlugItemHash())) {
////                                    modsModelList.get(i).setSocketName(itemData.getDisplayProperties().getName());
////                                    modsModelList.get(i).setSocketDescription(itemData.getDisplayProperties().getDescription());
////
////                                    if(itemData.getDisplayProperties().isHasIcon()) {
////                                        modsModelList.get(i).setSocketIcon(itemData.getDisplayProperties().getIcon());
////                                    }
////                                }
////                            }
////                        }
////                        //Sort list based on display order
////                        Collections.sort(modsModelList, (socketModel, t1) -> Long.compare(socketModel.getSocketIndex(), t1.getSocketIndex()));
////                        modsLiveData.postValue(new SocketModel(modsModelList));
                    }
                    catch(Exception e) {
                        singleError.postValue(e.getLocalizedMessage());
                    }

                }, throwable -> modsLiveData.postValue(new SocketModel(throwable)));
    }

    private void getStatData(List<String> hashes) {

        Disposable disposable = mStatDefinitionDAO.getStatDefinitionList(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(statList -> {

                    try {
                        for(DestinyStatDefinition data : statList) {
                            StatDefinitionData statData = gson.fromJson(data.getValue(), StatDefinitionData.class);

                            for(int i = 0; i < statValuesList.size(); i++) {
                                if(statData.getHash().equals(statValuesList.get(i).getStatTypeHash())) {
                                    statValuesList.get(i).setStatName(statData.getDisplayProperties().getName());
                                }
                            }
                        }
                        statsLiveData.postValue(new SocketModel.InvestmentStats(statValuesList));
                    }
                    catch(Exception e) {
                        statsLiveData.postValue(new SocketModel.InvestmentStats("Error retrieving stat data"));
                    }
                }, throwable -> statsLiveData.postValue(new SocketModel.InvestmentStats(throwable)));
    }
}
