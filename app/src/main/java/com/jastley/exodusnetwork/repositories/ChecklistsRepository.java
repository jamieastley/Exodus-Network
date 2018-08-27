package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetChecklists;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.checklists.models.ChecklistModel;
import com.jastley.exodusnetwork.database.dao.ChecklistDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.InventoryItemDefinitionDAO;
import com.jastley.exodusnetwork.database.jsonModels.ChecklistData;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.jastley.exodusnetwork.Definitions.caydeJournals;
import static com.jastley.exodusnetwork.Definitions.forsakenCollection;
import static com.jastley.exodusnetwork.Definitions.ghostLore;
import static com.jastley.exodusnetwork.Definitions.latentMemories;
import static com.jastley.exodusnetwork.Definitions.sleeperNodes;

@Singleton
public class ChecklistsRepository {

    private MutableLiveData<Response_GetChecklists> checklistsData = new MutableLiveData<>();
    private MutableLiveData<Response_GetChecklists> latentMemoriesChecklist = new MutableLiveData<>();
    private MutableLiveData<Response_GetChecklists> ghostLoreChecklist = new MutableLiveData<>();
    private MutableLiveData<Response_GetChecklists> journalsChecklist = new MutableLiveData<>();
    private MutableLiveData<Response_GetChecklists> sleeperNodesChecklist = new MutableLiveData<>();
    private MutableLiveData<Response_GetChecklists> raidLairsChecklist = new MutableLiveData<>();
    private MutableLiveData<Response_GetChecklists> forsakenChecklist = new MutableLiveData<>();

    private List<String> checklistCategories;
    private List<ChecklistModel> latentMemoriesList;
    private List<ChecklistModel> ghostLoreList;
    private List<ChecklistModel> journalsList;
    private List<ChecklistModel> sleeperNodesList;
    private List<ChecklistModel> raidLairsList;
    private List<ChecklistModel> forsakenList;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    @Named("bungieRetrofit")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    Context context;

    @Inject
    @Named("savedPrefs")
    SharedPreferences sharedPreferences;

    @Inject
    ChecklistDefinitionDAO checklistDefinitionDAO;

    @Inject
    InventoryItemDefinitionDAO inventoryItemDefinitionDAO;

    @Inject
    public ChecklistsRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public void getChecklistProgression() {

        checklistCategories = new ArrayList<>();
        latentMemoriesList = new ArrayList<>();
        ghostLoreList = new ArrayList<>();
        journalsList = new ArrayList<>();
        sleeperNodesList = new ArrayList<>();
        raidLairsList = new ArrayList<>();
        forsakenList = new ArrayList<>();

        String mType = sharedPreferences.getString("selectedPlatform" , "");
        String mId = sharedPreferences.getString("membershipId" + mType, "");

        Disposable disposable = retrofit.create(BungieAPI.class).getProfileChecklists(mType, mId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {

                    if(response.getErrorCode() != 1) {
                        checklistsData.postValue(new Response_GetChecklists(response.getMessage()));
                    }
                    else {

                        //Checklist category
                        for (Iterator iterator = response.getResponse().getProfileProgression().getData().getChecklistDefinition().keySet().iterator(); iterator.hasNext(); ) {

                            String checklistDefinition = (String) iterator.next();
                            String checklistHash = UnsignedHashConverter.getPrimaryKey(checklistDefinition);

                            checklistCategories.add(checklistHash);

                            JsonObject currentChecklist = response.getResponse().getProfileProgression().getData().getChecklistDefinition().get(checklistDefinition);

                            int counter = 1;


                            //All objectives for each checklist category
                            for (Iterator innerIterator = currentChecklist.keySet().iterator(); innerIterator.hasNext(); ) {

                                String checklistObjective = (String) innerIterator.next();

                                boolean bool = Boolean.parseBoolean(currentChecklist.get(checklistObjective).getAsString());

                                ChecklistModel objective = new ChecklistModel(checklistObjective,
                                        bool,
                                        counter,
                                        checklistDefinition
                                        );

                                //for quick Collections.sort()
                                counter++;

                                switch(checklistDefinition) {

                                    case latentMemories:
                                        latentMemoriesList.add(objective);
                                        break;
                                    case ghostLore:
                                        ghostLoreList.add(objective);
                                        break;
                                    case caydeJournals:
                                        journalsList.add(objective);
                                        break;
                                    case sleeperNodes:
                                        sleeperNodesList.add(objective);
                                        break;
                                    case forsakenCollection:
                                        forsakenList.add(objective);
                                        break;
                                }

                            }

                        }
                        getChecklistData(checklistCategories);
                    }
                }, throwable -> checklistsData.postValue(new Response_GetChecklists(throwable)));
        compositeDisposable.add(disposable);
    }

    public void getChecklistData(List<String> keys) {

        Disposable disposable = checklistDefinitionDAO.getChecklistDefinitionList(keys)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(definitions -> {

                    for(ListIterator iterator = definitions.listIterator(); iterator.hasNext(); iterator.next()) {

                        int position = iterator.nextIndex();
                        Log.e(definitions.get(position).getId(), "pos: " + position);

                        ChecklistData data = gson.fromJson(definitions.get(position).getValue(), ChecklistData.class);

                        //assign data to objectives
                        switch(data.getHash()) {

                            case latentMemories:
                                for(ListIterator listIterator = latentMemoriesList.listIterator(); listIterator.hasNext(); listIterator.next()) {
                                    int listPosition = listIterator.nextIndex();
                                    latentMemoriesList.get(listPosition)
                                            .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                }
                                break;
                            case ghostLore:
                                for(ListIterator ghostIterator = ghostLoreList.listIterator(); ghostIterator.hasNext(); ghostIterator.next()) {
                                    int listPosition = ghostIterator.nextIndex();
                                    ghostLoreList.get(listPosition)
                                                 .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                }

                                break;
                            case caydeJournals:
                                for(ListIterator listIterator = journalsList.listIterator(); listIterator.hasNext(); listIterator.next()) {
                                    int listPosition = listIterator.nextIndex();
                                    journalsList.get(listPosition)
                                                .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                }
                                break;
                            case sleeperNodes:

                                List<String> nodeHashes = new ArrayList<>();

                                for(ListIterator listIterator = sleeperNodesList.listIterator(); listIterator.hasNext(); listIterator.next()) {
                                    int listPosition = listIterator.nextIndex();
                                    sleeperNodesList.get(listPosition)
                                                    .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());

                                    String frequencyHash = data.getEntriesList().get(listPosition).getItemHash();
                                    nodeHashes.add(UnsignedHashConverter.getPrimaryKey(frequencyHash));

                                    sleeperNodesList.get(listPosition)
                                            .setItemHash(frequencyHash);
                                }
                                getSleeperNodesInfo(nodeHashes);
                                break;
                            case forsakenCollection:
                                for(ListIterator listIterator = forsakenList.listIterator(); listIterator.hasNext(); listIterator.next()) {
                                    int listPosition = listIterator.nextIndex();
                                    forsakenList.get(listPosition)
                                                .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                    forsakenList.get(listPosition)
                                            .setItemImage(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getIcon());
                                }
                                break;
                        }

                    }

                    latentMemoriesChecklist.postValue(new Response_GetChecklists(latentMemoriesList));
                    ghostLoreChecklist.postValue(new Response_GetChecklists(ghostLoreList));
                    journalsChecklist.postValue(new Response_GetChecklists(journalsList));
                    raidLairsChecklist.postValue(new Response_GetChecklists(raidLairsList));
//                    sleeperNodesChecklist.postValue(new Response_GetChecklists(sleeperNodesList));
                    forsakenChecklist.postValue(new Response_GetChecklists(forsakenList));

                });
        compositeDisposable.add(disposable);
    }

    private void getSleeperNodesInfo(List<String> nodeHashes) {

        Disposable disposable = inventoryItemDefinitionDAO.getItemsListByKey(nodeHashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(nodeInfoList -> {

                    for(ListIterator iterator = nodeInfoList.listIterator(); iterator.hasNext(); iterator.next()) {

                        int position = iterator.nextIndex();
                        Log.e(nodeInfoList.get(position).getId(), "pos: " + position);

                        InventoryItemData data = gson.fromJson(nodeInfoList.get(position).getValue(), InventoryItemData.class);

                        //iterate over all sleeperNode data from checklist API result and append description
                        for(int i = 0; i < sleeperNodesList.size(); i++) {

                            if(sleeperNodesList.get(i).getItemHash().equals(data.getHash())) {
                                sleeperNodesList.get(i)
                                        .setDescription(data.getDisplayProperties().getDescription());
                            }
                        }
                    }
                    sleeperNodesChecklist.postValue(new Response_GetChecklists(sleeperNodesList));
                }, throwable -> sleeperNodesChecklist.postValue(new Response_GetChecklists(throwable.getLocalizedMessage())));

    }

    public void setupBottomNav() {

        String mType = sharedPreferences.getString("selectedPlatform", "");
        String mId = sharedPreferences.getString("membershipId"+mType, "");

    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public MutableLiveData<Response_GetChecklists> getLatentMemoriesChecklist() {
        return latentMemoriesChecklist;
    }

    public MutableLiveData<Response_GetChecklists> getGhostLoreChecklist() {
        return ghostLoreChecklist;
    }

    public MutableLiveData<Response_GetChecklists> getJournalsChecklist() {
        return journalsChecklist;
    }

    public MutableLiveData<Response_GetChecklists> getSleeperNodesChecklist() {
        return sleeperNodesChecklist;
    }

    public MutableLiveData<Response_GetChecklists> getRaidLairsChecklist() {
        return raidLairsChecklist;
    }

    public MutableLiveData<Response_GetChecklists> getForsakenChecklist() {
        return forsakenChecklist;
    }
}
