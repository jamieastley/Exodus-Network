package com.jastley.exodusnetwork.Vendors;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.Vendors.models.SocketModel;
import com.jastley.exodusnetwork.Vendors.models.XurVendorModel;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetVendor;
import com.jastley.exodusnetwork.api.models.Response_GetXurWeekly;
import com.jastley.exodusnetwork.api.models.XurSaleItemModel;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.AppManifestDatabase;
import com.jastley.exodusnetwork.database.dao.FactionDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.InventoryItemDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.StatDefinitionDAO;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;
import com.jastley.exodusnetwork.database.jsonModels.StatDefinitionData;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;
import com.jastley.exodusnetwork.database.models.DestinyStatDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import static com.jastley.exodusnetwork.Definitions.xurVendor;

@Singleton
public class XurRepository {

    private MutableLiveData<Response_GetXurWeekly> xurLiveDataList;
    private MutableLiveData<XurSaleItemModel> xurSaleLiveDataModel = new MutableLiveData<>();
    private MutableLiveData<XurVendorModel> xurLocationData = new MutableLiveData<>();
    private List<InventoryItemModel> xurItemsList = new ArrayList<>();
    private XurVendorModel vendorModel;

    //Item inspection data
    private MutableLiveData<InventoryItemJsonData> inventoryItemJsonData = new MutableLiveData<>();
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
    @Named("bungieAuthRetrofit")
    Retrofit authRetrofit;

    @Inject
    @Named("savedPrefs")
    SharedPreferences sharedPreferences;

    @Inject
    AppManifestDatabase manifestDatabase;

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
        Disposable disposable = retrofit.create(BungieAPI.class).getXurWeeklyInventory("xur", "history")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(xurData -> {

                            if(!xurData.getResponse().getStatus().equals("200")) {
                                xurLiveDataList.postValue(new Response_GetXurWeekly(xurData.getResponse().getMessage()));
                            }
                            else {

                                xurItemsList.clear();

                                try {
                                    String region = xurData.getResponse().getData().getLocation().getRegion();
                                    if(region.contains("&rsquo;")){
                                        region = region.replace("&rsquo;", "'");
                                    }

                                    vendorModel = new XurVendorModel(xurData.getResponse().getData().getLocation().getWorld(),
                                            region,
                                            xurData.getResponse().getData().getLocation().getId());


//                                    int listSize = response_getXurWeekly.getResponse().getData().getItems().size();
//
//                                    Response_GetXurWeekly.Items itemList = new Response_GetXurWeekly.Items();
//                                    itemList.
//
//                                    //add all items to List<>
//                                    for(int i = 0; i < listSize; i++){
//
//                                        InventoryItemJsonData xurItem = new InventoryItemJsonData();
//
//                                        xurItem.setDisplayProperties(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties());
//                                        xurItem.setItemName(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getName());
//                                        xurItem.setItemIcon(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getIcon());
//                                        xurItem.setItemHash(response_getXurWeekly.getResponse().getData().getItems().get(i).getHash());
//                                        xurItem.setItemTypeDisplayName(response_getXurWeekly.getResponse().getData().getItems().get(i).getItemTypeDisplayName());
//                                        xurItem.setSaleHistoryCount(response_getXurWeekly.getResponse().getData().getItems().get(i).getSalesCount());
//                                        xurItem.setDescription(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getDescription());
//
//                                        try{ //if item has a cost
//                                            xurItem.setCostItemIcon(response_getXurWeekly.getResponse().getData().getItems().get(i).getCost().getIcon());
//                                            xurItem.setCostsQuantity(response_getXurWeekly.getResponse().getData().getItems().get(i).getCost().getQuantity());
//                                        }
//                                        catch(Exception er){
//                                            Log.e("XUR_COST: ", er.getMessage());
//                                        }
//
//                                        try{
//                                            xurItem.setEquippingBlock(response_getXurWeekly.getResponse().getData().getItems().get(i).getEquippingBlock().getDisplayStrings().get(0));
//                                        }
//                                        catch(Exception e){
//                                            Log.e("XUR_EQUIP_BLOCK", String.valueOf(i));
//                                        }
//
//                                        xurItemsList.add(xurItem);
//
//                                    }//end for loop

                                    if(!xurData.getResponse().getData().getItems().isEmpty()) {
                                        xurLiveDataList.postValue(new Response_GetXurWeekly(xurData.getResponse().getData().getItems(),
                                                                                            xurData.getResponse().getData().getLocation()));
                                    }
//                                    getLocationBanner(vendorModel);
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

    public LiveData<InventoryItemJsonData> getInventoryItemData(String key) {

        String itemHash = UnsignedHashConverter.getPrimaryKey(key);

        Disposable disposable = mInventoryItemDefinitionDAO.getItemByKey(itemHash)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(jsonData -> {

                    //Map item retrieved from DB to model class
                    try{
//                        InventoryItemJsonData jsonData = gson.fromJson(destinyInventoryItemDefinition.getValue(), InventoryItemJsonData.class);
//
//                        //trigger observer to get basic info (name/screenshot)
                        inventoryItemJsonData.postValue(jsonData.getValue());
////
//
//                        //Perks list from sockets
                        List<String> perkHashes = new ArrayList<>();
                        List<String> modHashes = new ArrayList<>();
                        List<String> statHashes = new ArrayList<>();

//                        //Initialise Lists here to prevent remembering last selected item
                        perksModelList = new ArrayList<>();
                        modsModelList = new ArrayList<>();
                        statValuesList = new ArrayList<>();
//
//                        //Perk socket indexes
                        List<Integer> perksSocketsIndexes = new ArrayList<>();
                        List<Integer> modsSocketsIndexes = new ArrayList<>();
//                        for(int i = 0; i < jsonData.getSockets().getSocketCategoriesList().size(); i++) {
                        for(InventoryItemJsonData.Sockets.SocketCategories socket : jsonData.getValue().getSockets().getSocketCategoriesList()) {
//
//                            //weapons/armor have DIFFERENT socketCategory hashes!
                            if(socket.getSocketCategoryHash().equals(weaponPerksSockets) ||
                                    socket.getSocketCategoryHash().equals(armorPerksSockets)) {

                                perksSocketsIndexes.addAll(socket.getSocketIndexes());
                            }
                            else if(socket.getSocketCategoryHash().equals(weaponModsSockets) ||
                                    socket.getSocketCategoryHash().equals(armorModsSockets)) {
                                modsSocketsIndexes.addAll(socket.getSocketIndexes());
                            }
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
                        }
//
//                        //Get perks
                        for (int index: perksSocketsIndexes) {
                            SocketModel plugItem = new SocketModel();
                            plugItem.setPlugItemHash(jsonData.getValue().getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash());
                            plugItem.setSocketIndex(index);
                            perkHashes.add(UnsignedHashConverter.getPrimaryKey(jsonData.getValue().getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash()));

                            perksModelList.add(plugItem);
                        }
                        getPerkPlugs(perkHashes);
//
//                        //Get mods
                        for (int index: modsSocketsIndexes) {
                            SocketModel plugItem = new SocketModel();
                            plugItem.setPlugItemHash(jsonData.getValue().getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash());
                            plugItem.setSocketIndex(index);
                            modHashes.add(UnsignedHashConverter.getPrimaryKey(jsonData.getValue().getSockets().getSocketEntriesList().get(index).getSingleInitialItemHash()));

                            modsModelList.add(plugItem);
                        }
                        getModPlugs(modHashes);
//
//                        //Get stat values
                        int positionCount = 1;
                        for (InventoryItemJsonData.InvestmentStats statValue : jsonData.getValue().getInvestmentStatsList()) {

                            if(statValue.getValue() != 0){
                                statHashes.add(UnsignedHashConverter.getPrimaryKey(statValue.getStatTypeHash()));

                                SocketModel.InvestmentStats stat = new SocketModel.InvestmentStats(
                                        statValue.getStatTypeHash(),
                                        statValue.getValue(),
                                        positionCount
                                );
                                positionCount++;
                                statValuesList.add(stat);
                            }
                        }
                        getStatData(statHashes);

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
                        for(DestinyInventoryItemDefinition item: plugList) {
//                            InventoryItemJsonData itemData = gson.fromJson(item.getValue(), InventoryItemJsonData.class);

                            for (int i = 0; i < perksModelList.size(); i++) {
                                if(item.getValue().getHash().equals(perksModelList.get(i).getPlugItemHash())) {
                                    perksModelList.get(i).setSocketName(item.getValue().getDisplayProperties().getName());
                                    perksModelList.get(i).setSocketDescription(item.getValue().getDisplayProperties().getDescription());

                                    if(item.getValue().getDisplayProperties().isHasIcon()) {
                                        perksModelList.get(i).setSocketIcon(item.getValue().getDisplayProperties().getIcon());
                                    }
                                }
                            }
                        }
//                        //Sort list based on display order
                        Collections.sort(perksModelList, (socketModel, t1) -> Long.compare(socketModel.getSocketIndex(), t1.getSocketIndex()));
                        perksLiveData.postValue(new SocketModel(perksModelList));
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
                        for(DestinyInventoryItemDefinition item: plugList) {
////                            InventoryItemJsonData itemData = gson.fromJson(item.getValue(), InventoryItemJsonData.class);
////
                            for (int i = 0; i < modsModelList.size(); i++) {
                                if(item.getValue().getHash().equals(modsModelList.get(i).getPlugItemHash())) {
                                    modsModelList.get(i).setSocketName(item.getValue().getDisplayProperties().getName());
                                    modsModelList.get(i).setSocketDescription(item.getValue().getDisplayProperties().getDescription());

                                    if(item.getValue().getDisplayProperties().isHasIcon()) {
                                        modsModelList.get(i).setSocketIcon(item.getValue().getDisplayProperties().getIcon());
                                    }
                                }
                            }
                        }
                        //Sort list based on display order
                        Collections.sort(modsModelList, (socketModel, t1) -> Long.compare(socketModel.getSocketIndex(), t1.getSocketIndex()));
                        modsLiveData.postValue(new SocketModel(modsModelList));
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

    public void getXurVendor() {

        String mType = sharedPreferences.getString("selectedPlatform", "");
        String mId = sharedPreferences.getString("membershipId"+mType, "");
        String cId = sharedPreferences.getString(mType+"characterId0", "");

        Disposable disposable = authRetrofit.create(BungieAPI.class)
                .getVendorData(mType, mId, cId, xurVendor)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(xurVendor -> {

                    //Xur not present in game
                    if(xurVendor.getErrorCode().equals("1627")) {
                        xurSaleLiveDataModel.postValue(new XurSaleItemModel(xurVendor.getMessage()));
                    }
                    //API error
                    else if(!xurVendor.getErrorCode().equals("1")) {
                        xurSaleLiveDataModel.postValue(new XurSaleItemModel(xurVendor.getMessage()));
                    }
                    //Success
                    else {

                        List<String> itemHashList = new ArrayList<>();
                        List<XurSaleItemModel> saleModelList = new ArrayList<>();

                        Iterator<Map.Entry<String, Response_GetVendor.SalesData>> it = xurVendor.getResponse().getSales().getSalesData().entrySet().iterator();
                        while(it.hasNext()) {
                            Map.Entry<String, Response_GetVendor.SalesData> saleItem = it.next();

                            itemHashList.add(UnsignedHashConverter.getPrimaryKey(saleItem.getValue().getItemHash()));

                            //Five of Swords don't have a cost, so check whether item costs anything
                            if(saleItem.getValue().getCostsList() != null) {
                                for(Response_GetVendor.Costs itemCost : saleItem.getValue().getCostsList()) {
                                    //Most items only have one cost, but this could change so let's just iterate
                                    // over the list in-case items start requiring > 1 cost
                                    itemHashList.add(UnsignedHashConverter.getPrimaryKey(itemCost.getItemHash()));
                                }
                            }
                            XurSaleItemModel item = new XurSaleItemModel();
                            item.setSalesData(saleItem.getValue());
                            saleModelList.add(item);
                        }

                        getSalesItemData(itemHashList, saleModelList);

                    }

                }, throwable -> {
                    xurSaleLiveDataModel.postValue(new XurSaleItemModel(throwable));
                });
    }

    public void getSalesItemData(List<String> hashes, List<XurSaleItemModel> xurResponse) {

        Disposable disposable = manifestDatabase.getInventoryItemDAO()
                .getItemsListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(definitionData -> {

                    for(DestinyInventoryItemDefinition itemDefinition : definitionData) {

                        for(int i = 0; i < xurResponse.size(); i++) {

                            //Item for sale
                            if(xurResponse.get(i).getSalesData().getItemHash()
                                    .equals(itemDefinition.getValue().getHash())) {

                                xurResponse.get(i).setItemData(itemDefinition.getValue());

                            }

                            //Item cost could potentially change in the future so can't hardcode
                            // legendary shards as cost, look up what is required to buy from Xur
                            for(Response_GetVendor.Costs itemCost : xurResponse.get(i).getSalesData().getCostsList()) {

                                if(itemCost.getItemHash().equals(itemDefinition.getValue().getHash())) {
                                    xurResponse.get(i).setItemCost(itemDefinition.getValue());
                                }
                            }
                        }
                    }

                    xurSaleLiveDataModel.postValue(new XurSaleItemModel(xurResponse));

                }, throwable -> xurSaleLiveDataModel.postValue(new XurSaleItemModel(throwable)));
    }

    public LiveData<XurSaleItemModel> getXurSaleLiveDataModel() {
        return xurSaleLiveDataModel;
    }
}
