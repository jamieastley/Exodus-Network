package com.jastley.exodusnetwork.Vendors;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Vendors.models.XurVendorModel;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetXurWeekly;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.FactionsDAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.jastley.exodusnetwork.Definitions.theNine;
import static com.jastley.exodusnetwork.api.apiKey.braytechApiKey;

@Singleton
public class XurRepository {

    private MutableLiveData<Response_GetXurWeekly> xurLiveDataList;
    private MutableLiveData<XurVendorModel> xurLocationData = new MutableLiveData<>();
    private List<InventoryItemModel> xurItemsList = new ArrayList<>();
    private XurVendorModel vendorModel;


    @Inject
    @Named("braytechApi")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    FactionsDAO mFactionsDao;

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

                        }, err -> {
                                xurLiveDataList.postValue(new Response_GetXurWeekly(err.getLocalizedMessage()));
                        }
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

}
