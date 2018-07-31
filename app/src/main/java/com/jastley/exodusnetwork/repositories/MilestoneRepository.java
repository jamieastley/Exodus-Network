package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.jastley.exodusnetwork.Milestones.models.InventoryDataModel;
import com.jastley.exodusnetwork.Milestones.models.MilestoneModel;
import com.jastley.exodusnetwork.Utils.NoNetworkException;
import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.api.BungieAPI;

import com.jastley.exodusnetwork.api.models.Response_GetMilestones;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.dao.InventoryItemDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.MilestoneDAO;
import com.jastley.exodusnetwork.database.models.MilestoneData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class MilestoneRepository {

    private List<MilestoneModel> milestoneModelList = new ArrayList<>();
//    private MutableLiveData<List<MilestoneModel>> milestoneManifestList = new MutableLiveData<>();

    private MutableLiveData<Response_GetMilestones> milestoneManifestList;
    @Inject
    MilestoneDAO mMilestoneDao;

    @Inject
    InventoryItemDefinitionDAO mInventoryItemDao;

    @Inject
    @Named("bungieAuthRetrofit")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    public MilestoneRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    private LiveData<Response_GetMilestones> getMilestoneProgress() {

        milestoneManifestList = new MutableLiveData<>();

        Disposable disposable = retrofit.create(BungieAPI.class).getMilestones()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response_getMilestones -> {
                    if (!response_getMilestones.getErrorCode().equals("1")) {
                        String errorMessage = response_getMilestones.getMessage();
                        milestoneManifestList.postValue(new Response_GetMilestones(errorMessage));
                    } else {

                        List<MilestoneModel> milestoneModelList = new ArrayList<>();
                        ArrayList<String> hashList = new ArrayList<>();

                        for (String currentKey : response_getMilestones.getMilestoneHashes().keySet()) {

                            MilestoneModel milestoneData = new MilestoneModel();

                            //Get signedInt32 value
                            String primaryKey = UnsignedHashConverter.getPrimaryKey(currentKey);

                            milestoneData.setMilestoneHash(response_getMilestones.getMilestoneHashes().get(currentKey).getMilestoneHash());
                            milestoneData.setPrimaryKey(primaryKey);
                            milestoneModelList.add(milestoneData);

                            //get hash value as represented in the database
                            hashList.add(primaryKey);

                            //flashpoint questItemHash
                            try {
                                milestoneData.setQuestItemHash(response_getMilestones.getMilestoneHashes().get(currentKey).getAvailableQuests().get(0).getQuestItemHash());
                            } catch (Exception e) {
                                Log.d("MILESTONE_AVLBL_QUESTS", e.getLocalizedMessage());
                            }
                        }

                        //sort lists so they're in same order as the List Room returns
                        Collections.sort(hashList, (s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1)));
                        Collections.sort(milestoneModelList, (m1, m2) -> Long.valueOf(m1.getPrimaryKey()).compareTo(Long.valueOf((m2.getPrimaryKey()))));

                        getMilestoneModels(milestoneModelList, hashList);
                    }
                }, throwable -> {
                    if(throwable instanceof NoNetworkException) {
                        milestoneManifestList.postValue(new Response_GetMilestones(throwable.getLocalizedMessage()));
                    }
                    else {
                        milestoneManifestList.postValue(new Response_GetMilestones(throwable.getLocalizedMessage()));
                    }
                }, () -> {

                });

        return milestoneManifestList;
    }

    public LiveData<Response_GetMilestones> masterMethod() {

        getMilestoneProgress();

        return milestoneManifestList;
    }

    public void refreshMilestones() {

        milestoneManifestList = null;
        getMilestoneProgress();
    }

    public void getMilestoneModels(List<MilestoneModel> milestoneModels, List<String> hashes) {
        Disposable disposable = mMilestoneDao.getMilestoneListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(milestones -> {
                    ArrayList<String> rewards = new ArrayList<>();
                    for (int i = 0; i < milestones.size(); i++) {

                        //Map the milestone data to a model class so we don't have to manually parse JSON
                        MilestoneData data = gson.fromJson(milestones.get(i).getValue(), MilestoneData.class);

                        //Only get the milestones that have names/are valid
                        if(data.getQuestsData() != null) {

                            try {
                                milestoneModels.get(i).setMilestoneName(data.getQuestsData().get(milestoneModels.get(i).getQuestItemHash()).getDisplayProperties().getName());
                                milestoneModels.get(i).setMilestoneDescription(data.getQuestsData().get(milestoneModels.get(i).getQuestItemHash()).getDisplayProperties().getDescription());
                                milestoneModels.get(i).setMilestoneImageURL(data.getQuestsData().get(milestoneModels.get(i).getQuestItemHash()).getDisplayProperties().getIcon());

                                //quest rewards
                                try {
                                    rewards.add(UnsignedHashConverter.getPrimaryKey(data.getQuestsData().get(milestoneModels.get(i).getQuestItemHash()).getQuestRewards().getRewardItemsList().get(0).getItemHash()));
                                    milestoneModels.get(i).setMilestoneRewardHash(data.getQuestsData().get(milestoneModels.get(i).getQuestItemHash()).getQuestRewards().getRewardItemsList().get(0).getItemHash());
                                }
                                catch(Exception e) {
                                    Log.d("MILESTONE_REWARDS", "No rewards listed");
                                }
                            }
                            catch(Exception e) {

                                try { //not found under quests node, try displayProperties
                                    milestoneModels.get(i).setMilestoneName(data.getDisplayProperties().getName());
                                    milestoneModels.get(i).setMilestoneDescription(data.getDisplayProperties().getDescription());
                                    milestoneModels.get(i).setMilestoneImageURL(data.getDisplayProperties().getIcon());
                                }
                                catch(Exception err) {
                                    Log.d("MILESTONE_DATA", "No displayProperties found anywhere.");
                                }
                            }

                            for (String key : data.getQuestsData().keySet()) {
                                if (data.getQuestsData().get(key).getQuestRewards() != null) {
                                    milestoneModels.get(i).setMilestoneRewardHash(data.getQuestsData().get(key).getQuestRewards().getRewardItemsList().get(0).getItemHash());
                                }
                            }
                        }

                    }

                    Collections.sort(rewards, (s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1)));

                    getMilestoneRewards(milestoneModels, rewards);
                }, throwable -> {

                }, () -> {

                });
    }

    public LiveData<Response_GetMilestones> getMilestoneRewards(List<MilestoneModel> milestoneModels, List<String> rewardHashes) {

        Disposable disposable = mInventoryItemDao.getItemsListByKey(rewardHashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(rewards -> {

                    for (int i = 0; i < rewards.size(); i++) {

                        InventoryDataModel item = gson.fromJson(rewards.get(i).getValue(), InventoryDataModel.class);

                        //check rewards for each milestone, append if match
                        for (int j = 0; j < milestoneModels.size(); j++) {

                            try {
                                if (item.getHash().equals(milestoneModels.get(j).getMilestoneRewardHash())) {

                                    milestoneModels.get(j).setMilestoneRewardImageURL(item.getDisplayProperties().getIcon());
                                    milestoneModels.get(j).setMilestoneRewardName(item.getDisplayProperties().getName());
                                }
                                else {
                                    if(milestoneModels.get(j).getMilestoneName() == null){
                                        milestoneModels.remove(j);
                                    }
                                }
                            }
                            catch(Exception e) {
                                Log.d("MILESTONE_REWARD_LOOP", e.getLocalizedMessage());
                            }
                        }

                        milestoneManifestList.postValue(new Response_GetMilestones(milestoneModels));
//                        milestoneManifestList.postValue(milestoneModels);
                    }

                }, throwable -> {
//                    Snackbar.make(getView(), throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG)
//                            .show();
//                    mProgressBar.setVisibility(View.GONE);
                    milestoneManifestList.postValue(new Response_GetMilestones(throwable.getLocalizedMessage()));
                });

        return milestoneManifestList;
    }
}
