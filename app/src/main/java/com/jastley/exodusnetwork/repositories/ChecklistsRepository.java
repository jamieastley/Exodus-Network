package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetChecklists;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.checklists.models.ChecklistModel;
import com.jastley.exodusnetwork.database.dao.ChecklistDefinitionDAO;
import com.jastley.exodusnetwork.database.jsonModels.ChecklistData;

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

    private List<String> checklistCategories = new ArrayList<>();
    private List<ChecklistModel> latentMemoriesList = new ArrayList<>();
    private List<ChecklistModel> ghostLoreList = new ArrayList<>();
    private List<ChecklistModel> journalsList = new ArrayList<>();
    private List<ChecklistModel> sleeperNodesList = new ArrayList<>();
    private List<ChecklistModel> raidLairsList = new ArrayList<>();
    private List<ChecklistModel> forsakenList = new ArrayList<>();

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
    public ChecklistsRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public void getChecklistProgression() {

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

                                String checklistObjective = (String) iterator.next();

                                ChecklistModel objective = new ChecklistModel(checklistObjective,
                                        currentChecklist.get(checklistObjective).getAsBoolean(),
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

                    for(ListIterator iterator = definitions.listIterator(); iterator.hasNext(); ) {

                        int position = iterator.nextIndex();

                        ChecklistData data = gson.fromJson(definitions.get(position).getValue(), ChecklistData.class);

                        //assign data to objectives
                        switch(data.getHash()) {

                            case latentMemories:
                                for(ListIterator memoryIterator = latentMemoriesList.listIterator(); memoryIterator.hasNext(); ) {
                                    int listPosition = iterator.nextIndex();
                                    latentMemoriesList.get(listPosition).setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                }
                                break;
                            case ghostLore:
                                for(ListIterator memoryIterator = ghostLoreList.listIterator(); memoryIterator.hasNext(); ) {
                                    int listPosition = iterator.nextIndex();
                                    ghostLoreList.get(listPosition)
                                                 .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                }
                                break;
                            case caydeJournals:
                                for(ListIterator memoryIterator = journalsList.listIterator(); memoryIterator.hasNext(); ) {
                                    int listPosition = iterator.nextIndex();
                                    journalsList.get(listPosition)
                                                .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                }
                                break;
                            case sleeperNodes:
                                for(ListIterator memoryIterator = sleeperNodesList.listIterator(); memoryIterator.hasNext(); ) {
                                    int listPosition = iterator.nextIndex();
                                    sleeperNodesList.get(listPosition)
                                                    .setChecklistItemName(data.getEntriesList().get(listPosition).getChecklistDisplayProperties().getName());
                                }
                                break;
                            case forsakenCollection:
                                for(ListIterator memoryIterator = forsakenList.listIterator(); memoryIterator.hasNext(); ) {
                                    int listPosition = iterator.nextIndex();
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
                    sleeperNodesChecklist.postValue(new Response_GetChecklists(sleeperNodesList));
                    forsakenChecklist.postValue(new Response_GetChecklists(forsakenList));

                });
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        compositeDisposable.dispose();
    }
}
