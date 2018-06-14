package com.jastley.warmindfordestiny2.Account.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.warmindfordestiny2.Account.models.AccountStatsModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class AccountStatsFragment extends Fragment {

    private OnAccountStatsInteractionListener mListener;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BungieAPI mBungieAPI;
    private Context mContext;

    //Mode-specific stat models
    private List<AccountStatsModel> pvpStatsList = new ArrayList<>();
    private List<AccountStatsModel> patrolStatsList = new ArrayList<>();
    private List<AccountStatsModel> raidStatsList = new ArrayList<>();
    private List<AccountStatsModel> storyStatsList = new ArrayList<>();
    private List<AccountStatsModel> allStrikesStatsList = new ArrayList<>();

    public AccountStatsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AccountStatsFragment newInstance() {
        return new AccountStatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mBungieAPI = RetrofitHelper.getAuthBungieAPI(mContext, baseURL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account_stats, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAccountStats();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAccountStatsInteractionListener) {
            mListener = (OnAccountStatsInteractionListener) context;
            mContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountStatsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mContext = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }


    public interface OnAccountStatsInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getAccountStats() {

        SharedPreferences savedPrefs;
        savedPrefs = mContext.getSharedPreferences("saved_manifest", Context.MODE_PRIVATE);

        String selectedPlatform = savedPrefs.getString("selectedPlatform", "");

        if(selectedPlatform != "") {
            String membershipId = savedPrefs.getString("membershipId" + selectedPlatform, "");

            Disposable disposable = mBungieAPI.getAllModesAccountStats(selectedPlatform, membershipId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                        if(!response.getErrorCode().equals("1")) {
                            String errorMessage = response.getMessage();
                            Snackbar.make(getView(),  errorMessage, Snackbar.LENGTH_LONG)
                                    .setAction("Retry", v -> getAccountStats())
                                    .show();
                        }
                        else {

                            //PVP stats
                            if(response.getResponse().getAllPvP() != null) {

                                pvpStatsList.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getAllPvP().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Activities Won",
                                        response.getResponse().getAllPvP().getAllTime().getActivitiesWon().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Assists",
                                        response.getResponse().getAllPvP().getAllTime().getAssists().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Kills",
                                        response.getResponse().getAllPvP().getAllTime().getKills().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getAllPvP().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getAllPvP().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getAllPvP().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getAllPvP().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getAllPvP().getAllTime().getScore().getPga().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getAllPvP().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getAllPvP().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getAllPvP().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getAllPvP().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getAllPvP().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("W/L Ratio",
                                        response.getResponse().getAllPvP().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getAllPvP().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                pvpStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getAllPvP().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));
                            }

                            //Raid stats
                            if(response.getResponse().getRaid() != null) {

                                raidStatsList.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getRaid().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getRaid().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Assists",
                                        response.getResponse().getRaid().getAllTime().getAssists().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Kills",
                                        response.getResponse().getRaid().getAllTime().getKills().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getRaid().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getRaid().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getRaid().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getRaid().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getRaid().getAllTime().getScore().getPga().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getRaid().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getRaid().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getRaid().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getRaid().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getRaid().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("W/L Ratio",
                                        response.getResponse().getRaid().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getRaid().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getRaid().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));
                            }

                            //Strikes stats
                            if(response.getResponse().getAllStrikes() != null) {

                                allStrikesStatsList.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getAllStrikes().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getAllStrikes().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Assists",
                                        response.getResponse().getAllStrikes().getAllTime().getAssists().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Kills",
                                        response.getResponse().getAllStrikes().getAllTime().getKills().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getAllStrikes().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getAllStrikes().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getAllStrikes().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getAllStrikes().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getAllStrikes().getAllTime().getScore().getPga().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getAllStrikes().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getAllStrikes().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getAllStrikes().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getAllStrikes().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getAllStrikes().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("W/L Ratio",
                                        response.getResponse().getAllStrikes().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getAllStrikes().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getAllStrikes().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));
                            }

                            //Story stats
                            if(response.getResponse().getStory() != null) {

                                storyStatsList.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getStory().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getStory().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Assists",
                                        response.getResponse().getStory().getAllTime().getAssists().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Kills",
                                        response.getResponse().getStory().getAllTime().getKills().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getStory().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getStory().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getStory().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getStory().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getStory().getAllTime().getScore().getPga().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getStory().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getStory().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getStory().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getStory().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getStory().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("W/L Ratio",
                                        response.getResponse().getStory().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getStory().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getStory().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));
                            }

                            //Patrol
                            if(response.getResponse().getPatrol() != null) {

                                patrolStatsList.add(new AccountStatsModel("Activites Entered",
                                        response.getResponse().getPatrol().getAllTime().getActivitiesEntered().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Activities Cleared",
                                        response.getResponse().getPatrol().getAllTime().getActivitiesCleared().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Assists",
                                        response.getResponse().getPatrol().getAllTime().getAssists().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Kills",
                                        response.getResponse().getPatrol().getAllTime().getKills().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Deaths",
                                        response.getResponse().getPatrol().getAllTime().getDeaths().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Suicides",
                                        response.getResponse().getPatrol().getAllTime().getSuicides().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Time Played",
                                        response.getResponse().getPatrol().getAllTime().getSecondsPlayed().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Avg. Lifespan",
                                        response.getResponse().getPatrol().getAllTime().getAverageLifespan().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Average Score",
                                        response.getResponse().getPatrol().getAllTime().getScore().getPga().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Most Kills (Match)",
                                        response.getResponse().getPatrol().getAllTime().getBestSingleGameKills().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Efficiency",
                                        response.getResponse().getPatrol().getAllTime().getEfficiency().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("KD/Ratio",
                                        response.getResponse().getPatrol().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Objectives Completed",
                                        response.getResponse().getPatrol().getAllTime().getObjectivesCompleted().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Precision Kills",
                                        response.getResponse().getPatrol().getAllTime().getPrecisionKills().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("W/L Ratio",
                                        response.getResponse().getPatrol().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getPatrol().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getPatrol().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));
                            }


                        }
                    }, throwable -> {
                        if(throwable instanceof NoNetworkException) {
                            Snackbar.make(getView(), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", v -> getAccountStats())
                                    .show();
                        }
                        else {
                            Snackbar.make(getView(), "Couldn't get account stats.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                        }
                    });
                compositeDisposable.add(disposable);
        }
        else {
            //TODO error snackbar
        }



    }
}
