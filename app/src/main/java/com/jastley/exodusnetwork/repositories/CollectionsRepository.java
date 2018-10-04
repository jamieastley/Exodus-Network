package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.jastley.exodusnetwork.Utils.LiveDataResponseModel;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetProfileCollections.Response.ProfileCollectibles.Data.Collectibles;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.AppManifestDatabase;
import com.jastley.exodusnetwork.database.models.DestinyCollectibleDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class CollectionsRepository {

    private MutableLiveData<LiveDataResponseModel> collectiblesResponse = new MutableLiveData<>();

    @Inject
    @Named("savedPrefs")
    SharedPreferences sharedPreferences;

    @Inject
    AppManifestDatabase manifestDatabase;

    @Inject
    @Named("bungieRetrofit")
    Retrofit retrofit;

    @Inject
    public CollectionsRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public void getProfileCollectibles() {

        String mType = sharedPreferences.getString("selectedPlatform", "");
        if (!mType.equals("")) {
            String mId = sharedPreferences.getString("membershipId"+mType, "");

            Disposable disposable = retrofit.create(BungieAPI.class)
                    .getProfileCollectibles(mType, mId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(response -> {

                        if(response.getErrorCode() != 1) {
                            //TODO error handling
                            collectiblesResponse.postValue(new LiveDataResponseModel(response.getMessage()));
                        }
                        else {
                            /*
                            - create list of Collectibles
                            - retain list position from response so it can be re-sorted for displaying
                            */
                            List<String> hashList = new ArrayList<>();
                            int counter = 1;
                            for(Map.Entry<String, Collectibles> entry : response.getResponse().getProfileCollectibles().getData().getCollectibles().entrySet()) {

                                String key = entry.getKey();
                                hashList.add(UnsignedHashConverter.getPrimaryKey(key));
                                entry.getValue().setListPosition(counter);

                                counter++;
                            }

                            getCollectibleData(hashList, response.getResponse().getProfileCollectibles().getData().getCollectibles());
                        }

                    }, throwable -> collectiblesResponse.postValue(new LiveDataResponseModel(throwable)));

        }
        else {
            collectiblesResponse.postValue(new LiveDataResponseModel("There was an error retrieving account data"));
        }
        //TODO error handling
    }

    private void getCollectibleData(List<String> hashes, Map<String, Collectibles> collectiblesMap) {

        Disposable disposable = manifestDatabase.getCollectibleDefinitionDAO()
                .getItemListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(collectibles -> {

                    List<DestinyCollectibleDefinition> collectibleDefinitionList = new ArrayList<>();

                    for(DestinyCollectibleDefinition collectible : collectibles) {
                        try {
                            collectible.getValue().setProfileState(collectiblesMap.get(collectible.getValue().getHash()).getState());
                            collectible.getValue().setListPosition(collectiblesMap.get(collectible.getValue().getHash()).getListPosition());

                            /**Other flag enums are irrelevant here, just check whether acquired (1)
                             * {@link com.jastley.exodusnetwork.Utils.BitwiseCalculator} **/
                            collectible.getValue().setAcquired((collectiblesMap.get(collectible.getValue().getHash()).getState() & 1) == 0);
                            collectibleDefinitionList.add(collectible);
                        }
                        catch(Exception e) {
                            Crashlytics.logException(e);
                            Log.e("COLLECTIBLE_DATA", e.getLocalizedMessage());
                        }
                    }

                    Collections.sort(collectibleDefinitionList, (destinyCollectibleDefinition, t1) -> destinyCollectibleDefinition.getValue().getListPosition() - t1.getValue().getListPosition());

                    collectiblesResponse.postValue(new LiveDataResponseModel(collectibleDefinitionList));

                }, throwable -> collectiblesResponse.postValue(new LiveDataResponseModel(throwable)));

    }

    public MutableLiveData<LiveDataResponseModel> getCollectiblesResponse() {
        return collectiblesResponse;
    }
}
