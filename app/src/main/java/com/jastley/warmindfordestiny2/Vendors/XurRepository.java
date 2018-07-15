package com.jastley.warmindfordestiny2.Vendors;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.jastley.warmindfordestiny2.Inventory.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.Vendors.models.XurVendorModel;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.models.Response_GetXurWeekly;
import com.jastley.warmindfordestiny2.database.FactionsDAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.jastley.warmindfordestiny2.api.apiKey.braytechApiKey;

@Singleton
public class XurRepository {

    private MutableLiveData<Response_GetXurWeekly> xurLiveDataList;
    private LiveData<XurVendorModel> xurLocationData;
    private List<InventoryItemModel> xurItemsList = new ArrayList<>();
    private XurVendorModel vendorModel;
    private CompositeDisposable compDisposable = new CompositeDisposable();

    @Inject
    @Named("braytechApi")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    FactionsDAO mFactionsDao;

    public LiveData<Response_GetXurWeekly> getXurInventory() {

        xurLiveDataList = new MutableLiveData<>();

        //get Xur inventory from WhatsXurGot API
        Disposable disposable = retrofit.create(BungieAPI.class).getXurWeeklyInventory(braytechApiKey, "history", "xur")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_getXurWeekly -> {

                            if(!response_getXurWeekly.getResponse().getStatus().equals("200")) {
//                                Snackbar.make(getView(), "Couldn't get Xur's stock from the server.", Snackbar.LENGTH_LONG)
//                                        .setAction("Action", null)
//                                        .show();
//                                progressBar.setVisibility(View.GONE);
                                xurLiveDataList.postValue(new Response_GetXurWeekly(response_getXurWeekly.getResponse().getMessage()));
                            }
                            else {

                                xurItemsList.clear();
                                System.out.println(response_getXurWeekly.toString());

//                                xurWorldText.setText(response_getXurWeekly.getResponse().getData().getLocation().getWorld());

                                String region = response_getXurWeekly.getResponse().getData().getLocation().getRegion();
                                if(region.contains("&rsquo;")){
                                    region = region.replace("&rsquo;", "'");
                                }
//                                xurRegionText.setText(region);
//                                locationIndex = response_getXurWeekly.getResponse().getData().getLocation().getId();

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
                                        System.out.println("No cost: "+er);
                                    }

                                    try{
                                        xurItem.setEquippingBlock(response_getXurWeekly.getResponse().getData().getItems().get(i).getEquippingBlock().getDisplayStrings().get(0));
                                    }
                                    catch(Exception e){
                                        System.out.println("No equippingBlock set for item "+ i);
                                    }

                                    xurItemsList.add(xurItem);

                                }//end for loop

                                xurLiveDataList.postValue(new Response_GetXurWeekly(xurItemsList));
                                getLocationBanner(vendorModel);
//                                mXurRecyclerAdapter = new XurItemsRecyclerAdapter(getContext(), xurItemsList);
//                                mXurRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                mXurRecyclerView.setNestedScrollingEnabled(false);
//                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
//                                mXurRecyclerView.setLayoutAnimation(controller);
//                                mXurRecyclerView.setAdapter(mXurRecyclerAdapter);
////                                mXurRecyclerView.addItemDecoration(headerItemDecoration);
//                                progressBar.setVisibility(View.GONE);
//                                mSwipeRefresh.setRefreshing(false);
                            }

                        }, err -> {
                                xurLiveDataList.postValue(new Response_GetXurWeekly(err.getLocalizedMessage()));
                        }
//                        this::getLocationBanner
                );

        return xurLiveDataList;
    }

    public LiveData getLocationBanner(XurVendorModel vendorModel) {




        return xurLocationData;
    }

}
