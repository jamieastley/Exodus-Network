package com.jastley.warmindfordestiny2.Account.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.Account.adapters.AccountStatsRecyclerAdapter;
import com.jastley.warmindfordestiny2.Account.models.AccountStatsModel;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

    @BindView(R.id.account_stats_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    //RecyclerViews
    @BindView(R.id.account_stats_pvp_recycler_view) RecyclerView mPVPRecyclerView;
    @BindView(R.id.account_stats_raid_recycler_view) RecyclerView mRaidRecyclerView;
    @BindView(R.id.account_stats_strikes_recycler_view) RecyclerView mStrikesRecyclerView;
    @BindView(R.id.account_stats_story_recycler_view) RecyclerView mStoryRecyclerView;
    @BindView(R.id.account_stats_patrol_recycler_view) RecyclerView mPatrolRecyclerView;

    //Error messages
    @BindView(R.id.account_stats_pvp_error) TextView mPVPErrorMessage;
    @BindView(R.id.account_stats_raid_error) TextView mRaidErrorMessage;
    @BindView(R.id.account_stats_strikes_error) TextView mStrikesErrorMessage;
    @BindView(R.id.account_stats_story_error) TextView mStoryErrorMessage;
    @BindView(R.id.account_stats_patrol_error) TextView mPatrolErrorMessage;

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

        mBungieAPI = RetrofitHelper.getAuthBungieAPI(mContext, baseURL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account_stats, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setRefreshing(true);

        getAccountStats();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            pvpStatsList.clear();
            raidStatsList.clear();
            allStrikesStatsList.clear();
            storyStatsList.clear();
            patrolStatsList.clear();

            getAccountStats();

        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAccountStatsInteraction(uri);
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
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity)getActivity();
        if(activity != null) {
            activity.setActionBarTitle(getString(R.string.account_stats));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.refresh_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.refresh_button:
                mSwipeRefreshLayout.setRefreshing(true);
                pvpStatsList.clear();
                raidStatsList.clear();
                allStrikesStatsList.clear();
                storyStatsList.clear();
                patrolStatsList.clear();
                getAccountStats();
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnAccountStatsInteractionListener {
        // TODO: Update argument type and name
        void onAccountStatsInteraction(Uri uri);
    }

    private void getAccountStats() {

        SharedPreferences savedPrefs;
        savedPrefs = mContext.getSharedPreferences("saved_prefs", Context.MODE_PRIVATE);

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
                            if(response.getResponse().getAllPvP().getAllTime() != null) {

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

                                AccountStatsRecyclerAdapter pvpStatsAdapter = new AccountStatsRecyclerAdapter(pvpStatsList);
                                mPVPRecyclerView.setLayoutAnimation(getLayoutAnimation());
                                mPVPRecyclerView.setLayoutManager(getLayoutManager());
                                mPVPRecyclerView.setAdapter(pvpStatsAdapter);
                                mPVPRecyclerView.setNestedScrollingEnabled(false);
                            }
                            else {
                                mPVPErrorMessage.setVisibility(View.VISIBLE);
                            }

                            //Raid stats
                            if(response.getResponse().getRaid().getAllTime() != null) {

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

//                                raidStatsList.add(new AccountStatsModel("W/L Ratio",
//                                        response.getResponse().getRaid().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getRaid().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                raidStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getRaid().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));

                                AccountStatsRecyclerAdapter raidStatsAdapter = new AccountStatsRecyclerAdapter(raidStatsList);
                                mRaidRecyclerView.setLayoutAnimation(getLayoutAnimation());
                                mRaidRecyclerView.setLayoutManager(getLayoutManager());
                                mRaidRecyclerView.setAdapter(raidStatsAdapter);
                                mRaidRecyclerView.setNestedScrollingEnabled(false);
                            }
                            else {
                                mRaidErrorMessage.setVisibility(View.VISIBLE);
                            }

                            //Strikes stats
                            if(response.getResponse().getAllStrikes().getAllTime() != null) {

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

//                                allStrikesStatsList.add(new AccountStatsModel("W/L Ratio",
//                                        response.getResponse().getAllStrikes().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getAllStrikes().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                allStrikesStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getAllStrikes().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));

                                AccountStatsRecyclerAdapter strikesStatsAdapter = new AccountStatsRecyclerAdapter(allStrikesStatsList);
//                                mPVPRecyclerView.setLayoutManager(getGridLayoutManager());
//                                mPVPRecyclerView.setAdapter(strikesStatsAdapter);
                                mStrikesRecyclerView.setLayoutAnimation(getLayoutAnimation());
                                mStrikesRecyclerView.setLayoutManager(getLayoutManager());
                                mStrikesRecyclerView.setAdapter(strikesStatsAdapter);
                                mStrikesRecyclerView.setNestedScrollingEnabled(false);
                            }
                            else {
                                mStrikesErrorMessage.setVisibility(View.VISIBLE);
                            }

                            //Story stats
                            if(response.getResponse().getStory().getAllTime() != null) {

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

//                                storyStatsList.add(new AccountStatsModel("Average Score",
//                                        response.getResponse().getStory().getAllTime().getScore().getPga().getDisplayValue()));

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

//                                storyStatsList.add(new AccountStatsModel("W/L Ratio",
//                                        response.getResponse().getStory().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getStory().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                storyStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getStory().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));

                                AccountStatsRecyclerAdapter storyStatsAdapter = new AccountStatsRecyclerAdapter(storyStatsList);
                                mStoryRecyclerView.setLayoutAnimation(getLayoutAnimation());
                                mStoryRecyclerView.setLayoutManager(getLayoutManager());
                                mStoryRecyclerView.setAdapter(storyStatsAdapter);
                                mStoryRecyclerView.setNestedScrollingEnabled(false);
                            }
                            else {
                                mStoryErrorMessage.setVisibility(View.VISIBLE);
                            }

                            //Patrol
                            if(response.getResponse().getPatrol().getAllTime() != null) {

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

//                                patrolStatsList.add(new AccountStatsModel("W/L Ratio",
//                                        response.getResponse().getPatrol().getAllTime().getWinLossRatio().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Best Killstreak",
                                        response.getResponse().getPatrol().getAllTime().getLongestKillSpree().getBasic().getDisplayValue()));

                                patrolStatsList.add(new AccountStatsModel("Longest Kill Distance",
                                        response.getResponse().getPatrol().getAllTime().getLongestKillDistance().getBasic().getDisplayValue()));

                                AccountStatsRecyclerAdapter patrolStatsAdapter = new AccountStatsRecyclerAdapter(patrolStatsList);
                                mPatrolRecyclerView.setLayoutAnimation(getLayoutAnimation());
                                mPatrolRecyclerView.setLayoutManager(getLayoutManager());
                                mPatrolRecyclerView.setAdapter(patrolStatsAdapter);
                                mPatrolRecyclerView.setNestedScrollingEnabled(false);
                            }
                            else {
                                mPatrolErrorMessage.setVisibility(View.VISIBLE);
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

                        mPVPErrorMessage.setVisibility(View.VISIBLE);
                        mRaidErrorMessage.setVisibility(View.VISIBLE);
                        mStrikesErrorMessage.setVisibility(View.VISIBLE);
                        mStoryErrorMessage.setVisibility(View.VISIBLE);
                        mPatrolErrorMessage.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);

                    }, () -> mSwipeRefreshLayout.setRefreshing(false));

                compositeDisposable.add(disposable);
        }
        else {
            mPVPErrorMessage.setVisibility(View.VISIBLE);
            mRaidErrorMessage.setVisibility(View.VISIBLE);
            mStrikesErrorMessage.setVisibility(View.VISIBLE);
            mStoryErrorMessage.setVisibility(View.VISIBLE);
            mPatrolErrorMessage.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);

            Snackbar.make(getView(), "Couldn't get account stats.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
        }

    }

    private LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    private LayoutAnimationController getLayoutAnimation() {
        return AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
    }
}
