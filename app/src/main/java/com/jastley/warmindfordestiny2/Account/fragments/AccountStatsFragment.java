package com.jastley.warmindfordestiny2.Account.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.jastley.warmindfordestiny2.Account.viewmodels.AccountStatsViewModel;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class AccountStatsFragment extends Fragment {

    private OnAccountStatsInteractionListener mListener;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BungieAPI mBungieAPI;
    private Context mContext;
    private AccountStatsViewModel mViewModel;

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

    //RecyclerAdapters
    private AccountStatsRecyclerAdapter pvpStatsAdapter;
    private AccountStatsRecyclerAdapter raidStatsAdapter;
    private AccountStatsRecyclerAdapter strikesStatsAdapter;
    private AccountStatsRecyclerAdapter storyStatsAdapter;
    private AccountStatsRecyclerAdapter patrolStatsAdapter;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialiseRecyclerViews();

        mViewModel = ViewModelProviders.of(this).get(AccountStatsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setRefreshing(true);

        getAccountStats();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
//            pvpStatsList.clear();
//            raidStatsList.clear();
//            allStrikesStatsList.clear();
//            storyStatsList.clear();
//            patrolStatsList.clear();

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
//                pvpStatsList.clear();
//                raidStatsList.clear();
//                allStrikesStatsList.clear();
//                storyStatsList.clear();
//                patrolStatsList.clear();
                getAccountStats();
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnAccountStatsInteractionListener {
        // TODO: Update argument type and name
        void onAccountStatsInteraction(Uri uri);
    }

    private void initialiseRecyclerViews() {

        //PVP stats
        pvpStatsAdapter = new AccountStatsRecyclerAdapter();
        mPVPRecyclerView.setLayoutAnimation(getLayoutAnimation());
        mPVPRecyclerView.setLayoutManager(getLayoutManager());
        mPVPRecyclerView.setAdapter(pvpStatsAdapter);
        mPVPRecyclerView.setNestedScrollingEnabled(false);

        //Raid
        raidStatsAdapter = new AccountStatsRecyclerAdapter();
        mRaidRecyclerView.setLayoutAnimation(getLayoutAnimation());
        mRaidRecyclerView.setLayoutManager(getLayoutManager());
        mRaidRecyclerView.setAdapter(raidStatsAdapter);
        mRaidRecyclerView.setNestedScrollingEnabled(false);

        //Strikes
        strikesStatsAdapter = new AccountStatsRecyclerAdapter();
        mStrikesRecyclerView.setLayoutAnimation(getLayoutAnimation());
        mStrikesRecyclerView.setLayoutManager(getLayoutManager());
        mStrikesRecyclerView.setAdapter(strikesStatsAdapter);
        mStrikesRecyclerView.setNestedScrollingEnabled(false);

        //Story
        storyStatsAdapter = new AccountStatsRecyclerAdapter();
        mStoryRecyclerView.setLayoutAnimation(getLayoutAnimation());
        mStoryRecyclerView.setLayoutManager(getLayoutManager());
        mStoryRecyclerView.setAdapter(storyStatsAdapter);
        mStoryRecyclerView.setNestedScrollingEnabled(false);

        //Patrol
        patrolStatsAdapter = new AccountStatsRecyclerAdapter();
        mPatrolRecyclerView.setLayoutAnimation(getLayoutAnimation());
        mPatrolRecyclerView.setLayoutManager(getLayoutManager());
        mPatrolRecyclerView.setAdapter(patrolStatsAdapter);
        mPatrolRecyclerView.setNestedScrollingEnabled(false);
    }

    private void getAccountStats() {

        mViewModel.getPvpStatsList().observe(this, pvpStats -> {

        });

        mViewModel.getRaidStatsList().observe(this, raidStats -> {

        });

        mViewModel.getAllStrikesStatsList().observe(this, strikeStats -> {

        });

        mViewModel.getStoryStatsList().observe(this, storyStats -> {

        });

        mViewModel.getPatrolStatsList().observe(this, patrolStats -> {

        });
    }


    private LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    private LayoutAnimationController getLayoutAnimation() {
        return AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
    }
}
