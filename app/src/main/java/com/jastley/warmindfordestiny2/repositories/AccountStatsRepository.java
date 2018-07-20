package com.jastley.warmindfordestiny2.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jastley.warmindfordestiny2.Account.models.AccountStatsModel;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.models.Response_GetAllModesAccountStats;
import com.jastley.warmindfordestiny2.app.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class AccountStatsRepository {

    private MutableLiveData<Response_GetAllModesAccountStats> pvpStatsList = new MutableLiveData<>();
    private MutableLiveData<Response_GetAllModesAccountStats> patrolStatsList = new MutableLiveData<>();
    private MutableLiveData<Response_GetAllModesAccountStats> raidStatsList = new MutableLiveData<>();
    private MutableLiveData<Response_GetAllModesAccountStats> storyStatsList = new MutableLiveData<>();
    private MutableLiveData<Response_GetAllModesAccountStats> allStrikesStatsList = new MutableLiveData<>();

    //TODO single mutableLiveData for error handling
    private MutableLiveData<Response_GetAllModesAccountStats> networkThrowable = new MutableLiveData<>();

    @Inject
    @Named("bungieAuthRetrofit")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Context context;

    @Inject
    public AccountStatsRepository() {
        App.getApp().getAppComponent().inject(this);
    }


    public LiveData<Response_GetAllModesAccountStats> getPatrolStatsList() {
        return patrolStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getRaidStatsList() {
        return raidStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getStoryStatsList() {
        return storyStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getAllStrikesStatsList() {
        return allStrikesStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getPvpStatsList() {



        sharedPreferences = context.getSharedPreferences("saved_prefs", Context.MODE_PRIVATE);

        String selectedPlatform = sharedPreferences.getString("selectedPlatform", "");

        if(selectedPlatform != "") {
            String membershipId = sharedPreferences.getString("membershipId" + selectedPlatform, "");

            Disposable disposable = retrofit.create(BungieAPI.class).getAllModesAccountStats(selectedPlatform, membershipId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(response -> {

                        //API error
                        if(!response.getErrorCode().equals("1")) {

                            pvpStatsList.postValue(new Response_GetAllModesAccountStats(response.getMessage()));
                            patrolStatsList.postValue(new Response_GetAllModesAccountStats(response.getMessage()));
                            raidStatsList.postValue(new Response_GetAllModesAccountStats(response.getMessage()));
                            storyStatsList.postValue(new Response_GetAllModesAccountStats(response.getMessage()));
                            allStrikesStatsList.postValue(new Response_GetAllModesAccountStats(response.getMessage()));
                        }
                        else {

                            //PVP stats
                            if(response.getResponse().getAllPvP().getAllTime() != null) {

                                List<AccountStatsModel> pvpStats = new ArrayList<>();

                                pvpStats.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getAllPvP().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Activities Won",
                                        response.getResponse().getAllPvP().getAllTime().getActivitiesWon().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Assists",
                                        response.getResponse().getAllPvP().getAllTime().getAssists().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Kills",
                                        response.getResponse().getAllPvP().getAllTime().getKills().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getAllPvP().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getAllPvP().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getAllPvP().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getAllPvP().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getAllPvP().getAllTime().getScore().getPga().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getAllPvP().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getAllPvP().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getAllPvP().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getAllPvP().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getAllPvP().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("W/L Ratio",
                                        response.getResponse().getAllPvP().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getAllPvP().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                pvpStats.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getAllPvP().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));


                                pvpStatsList.postValue(new Response_GetAllModesAccountStats(pvpStats));
                            }
                            else {
                                pvpStatsList.postValue(new Response_GetAllModesAccountStats("No PvP stats found."));
                            }

                            //Raid stats
                            if(response.getResponse().getRaid().getAllTime() != null) {

                                List<AccountStatsModel> raidStats = new ArrayList<>();

                                raidStats.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getRaid().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getRaid().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Assists",
                                        response.getResponse().getRaid().getAllTime().getAssists().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Kills",
                                        response.getResponse().getRaid().getAllTime().getKills().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getRaid().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getRaid().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getRaid().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getRaid().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getRaid().getAllTime().getScore().getPga().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getRaid().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getRaid().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getRaid().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getRaid().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getRaid().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                // No W/L Ratio for raids

                                raidStats.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getRaid().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                raidStats.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getRaid().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));

                                raidStatsList.postValue(new Response_GetAllModesAccountStats(raidStats));
                            }
                            else {
                                raidStatsList.postValue(new Response_GetAllModesAccountStats("No Raid stats found."));
                            }

                            //Strikes stats
                            if(response.getResponse().getAllStrikes().getAllTime() != null) {

                                List<AccountStatsModel> strikesStats = new ArrayList<>();

                                strikesStats.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getAllStrikes().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getAllStrikes().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Assists",
                                        response.getResponse().getAllStrikes().getAllTime().getAssists().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Kills",
                                        response.getResponse().getAllStrikes().getAllTime().getKills().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getAllStrikes().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getAllStrikes().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getAllStrikes().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getAllStrikes().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getAllStrikes().getAllTime().getScore().getPga().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getAllStrikes().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getAllStrikes().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getAllStrikes().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getAllStrikes().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getAllStrikes().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                // No W/L Ratio for Strikes

                                strikesStats.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getAllStrikes().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                strikesStats.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getAllStrikes().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));


                                allStrikesStatsList.postValue(new Response_GetAllModesAccountStats(strikesStats));

                            }
                            else {
                                allStrikesStatsList.postValue(new Response_GetAllModesAccountStats("No Strikes stats found."));
                            }

                            //Story stats
                            if(response.getResponse().getStory().getAllTime() != null) {

                                List<AccountStatsModel> storyStats = new ArrayList<>();

                                storyStats.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getStory().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getStory().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Assists",
                                        response.getResponse().getStory().getAllTime().getAssists().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Kills",
                                        response.getResponse().getStory().getAllTime().getKills().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getStory().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getStory().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getStory().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getStory().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                //No average score for story stats

                                storyStats.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getStory().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getStory().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getStory().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getStory().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getStory().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                //No W/L Ratio for story stats

                                storyStats.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getStory().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                storyStats.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getStory().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));


                                storyStatsList.postValue(new Response_GetAllModesAccountStats(storyStats));

                            }
                            else {
                                storyStatsList.postValue(new Response_GetAllModesAccountStats("No Story stats found."));
                            }

                            //Patrol
                            if(response.getResponse().getPatrol().getAllTime() != null) {

                                List<AccountStatsModel> patrolStats = new ArrayList<>();

                                patrolStats.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getPatrol().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getPatrol().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Assists",
                                        response.getResponse().getPatrol().getAllTime().getAssists().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Kills",
                                        response.getResponse().getPatrol().getAllTime().getKills().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getPatrol().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getPatrol().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getPatrol().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getPatrol().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getPatrol().getAllTime().getScore().getPga().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getPatrol().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getPatrol().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getPatrol().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getPatrol().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getPatrol().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                //No W/L Ratio stats for patrol

                                patrolStats.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getPatrol().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                patrolStats.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getPatrol().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));

                                patrolStatsList.postValue(new Response_GetAllModesAccountStats(patrolStats));

                            }
                            else {
                                patrolStatsList.postValue(new Response_GetAllModesAccountStats("No Patrol stats found."));
                            }
                        }
                    }, throwable -> {
//                        pvpStatsList.postValue(new Response_GetAllModesAccountStats(throwable));
//                        patrolStatsList.postValue(new Response_GetAllModesAccountStats(throwable));
//                        raidStatsList.postValue(new Response_GetAllModesAccountStats(throwable));
//                        storyStatsList.postValue(new Response_GetAllModesAccountStats(throwable));
//                        allStrikesStatsList.postValue(new Response_GetAllModesAccountStats(throwable));

                        networkThrowable.postValue(new Response_GetAllModesAccountStats(throwable));
                    });
        }

        return pvpStatsList;
    }

    public LiveData<Response_GetAllModesAccountStats> getThrowable() {
        return networkThrowable;
    }
}
