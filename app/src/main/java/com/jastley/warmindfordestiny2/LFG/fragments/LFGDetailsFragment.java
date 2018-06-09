package com.jastley.warmindfordestiny2.LFG.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.LFG.adapters.LFGFactionsRecyclerAdapter;
import com.jastley.warmindfordestiny2.LFG.models.FactionProgressModel;
import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.Utils.UnsignedHashConverter;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.FactionsDAO;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDetailsFragmentInteraction} interface
 * to handle interaction events.
 * Use the {@link LFGDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LFGDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LFGPost receivedPlayerClick;
    @BindView(R.id.lfg_details_display_name) TextView displayName;
    @BindView(R.id.lfg_details_emblem_background) ImageView emblemBackground;
    @BindView(R.id.lfg_details_light) TextView lightLevel;
    @BindView(R.id.lfg_details_class_type) TextView classType;
    @BindView(R.id.lfg_details_description) TextView description;
    @BindView(R.id.lfg_details_group_name) TextView groupName;
    @BindView(R.id.lfg_details_microphone_icon) ImageView micIcon;
    @BindView(R.id.lfg_details_platform_icon) ImageView platformIcon;
    @BindView(R.id.lfg_details_play_time) TextView playTime;
    @BindView(R.id.lfg_details_lifespan) TextView lifeSpan;
    @BindView(R.id.lfg_details_kdr) TextView kdRatio;
    @BindView(R.id.lfg_details_activity_title) TextView activityTitle;
    @BindView(R.id.lfg_details_activity_checkpoint) TextView activityCheckpoint;

    @BindView(R.id.stats_values_progress_bar) ProgressBar statsValuesProgress;
    @BindView(R.id.lfg_details_group_progress) ProgressBar groupNameProgress;
    @BindView(R.id.faction_loading_progress_bar) ProgressBar factionLoadingBar;
    @BindView(R.id.faction_card_view) CardView factionCardView;

    @BindView(R.id.faction_progress_recyclerview) RecyclerView mFactionProgressRecyclerView;
    LFGFactionsRecyclerAdapter mFactionProgressRecyclerAdapter;

//    @BindView(R.id.lfg_details_emblem_icon) ImageView emblemIcon;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnDetailsFragmentInteraction mListener;
    private BungieAPI mBungieAPI;
    List<FactionProgressModel> factionProgressionsList = new ArrayList<>();

    Toolbar toolbar;
    AppCompatActivity appCompatActivity;
    FloatingActionButton mFab;

    public LFGDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LFGDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LFGDetailsFragment newInstance(String param1, String param2) {
        LFGDetailsFragment fragment = new LFGDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            receivedPlayerClick = bundle.getParcelable("clickedPlayer");

            mBungieAPI = RetrofitHelper.getBungieAPI(baseURL, getContext());

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lfgdetails, container, false);

//        mFab = ((MainActivity) getActivity()).findViewById(R.id.fab);
//        mFab.setVisibility(View.INVISIBLE);

        toolbar = getActivity().findViewById(R.id.toolbar);

//        appCompatActivity = (AppCompatActivity)getActivity();
//        appCompatActivity.setSupportActionBar(toolbar);
//
//        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);


//        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_padded);


        ButterKnife.bind(this, view);

        if(savedInstanceState != null){
            LFGPost savedInstanceModel = savedInstanceState.getParcelable("savedPlayerClick");
            displayName.setText(savedInstanceModel.getDisplayName());
            lightLevel.setText(savedInstanceModel.getLightLevel());
            classType.setText(savedInstanceModel.getClassType());
            activityTitle.setText(savedInstanceModel.getActivityTitle());
            activityCheckpoint.setText(savedInstanceModel.getActivityCheckpoint());
            description.setText(savedInstanceModel.getDescription());

            if(savedInstanceModel.getHasMic()){
                micIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_mic_on_white));
            }
            else {
                micIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_mic_off_white));
            }

            switch (savedInstanceModel.getMembershipType()) {

                case "1":
                    platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_xbl));
                    break;
                case "2":
                    platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_psn));
                    break;
                case "4":
                    platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_blizzard));
                    break;
            }

            String membershipId = savedInstanceModel.getMembershipId();
            String membershipType = savedInstanceModel.getMembershipType();

            Picasso.get()
                    .load(baseURL+savedInstanceModel.getEmblemBackground())
                    .into(emblemBackground);

            getHistoricalStatsAccount(membershipType, membershipId);
            getGroupDetails(membershipType, membershipId);

        }

        else {
            displayName.setText(receivedPlayerClick.getDisplayName());
            lightLevel.setText(receivedPlayerClick.getLightLevel());
            classType.setText(receivedPlayerClick.getClassType());
            activityTitle.setText(receivedPlayerClick.getActivityTitle());
            activityCheckpoint.setText(receivedPlayerClick.getActivityCheckpoint());
            description.setText(receivedPlayerClick.getDescription());

            if(receivedPlayerClick.getHasMic()){
                micIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_mic_on_white));
            }
            else {
                micIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_mic_off_white));
            }

            switch (receivedPlayerClick.getMembershipType()) {

                case "1":
                    platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_xbl));
                    break;
                case "2":
                    platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_psn));
                    break;
                case "4":
                    platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_blizzard));
                    break;
            }

            String membershipId = receivedPlayerClick.getMembershipId();
            String membershipType = receivedPlayerClick.getMembershipType();
            String characterId = receivedPlayerClick.getCharacterId();

            Picasso.get()
                    .load(baseURL+receivedPlayerClick.getEmblemBackground())
                    .into(emblemBackground);

            getHistoricalStatsAccount(membershipType, membershipId);
            getGroupDetails(membershipType, membershipId);
            getFactionStats(membershipType,membershipId, characterId);
        }


        return view;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("savedPlayerClick", receivedPlayerClick);
    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//
//        inflater.inflate(R.menu.app_bar_new_lfg, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //clear actionBar buttons from previous fragment (filter/refresh)
        menu.clear();
//        // Inflate the menu; this adds items to the action bar if it is present.
//        inflater.inflate(R.menu.app_bar_new_lfg, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
//                appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(false);
//                appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                appCompatActivity.getSupportActionBar().set
                getActivity().onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity)getActivity();
        if(activity != null) {
            activity.setActionBarTitle(getString(R.string.postDetails));

    //        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        }
        TabLayout mTabLayout = getActivity().findViewById(R.id.inventory_sliding_tabs);

        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsFragmentInteraction) {
            mListener = (OnDetailsFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailsFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnDetailsFragmentInteraction {
        // TODO: Update argument type and name
        void LFGDetailsListener();

    }

    public void getGroupDetails(String membershipType, String membershipId) {

        Disposable disposable = mBungieAPI.getClanData(membershipType, membershipId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //Something went wrong
                    if(!response.getErrorCode().equals("1")) {
                        String errorMessage = response.getMessage();
                        Snackbar.make(getView(),  errorMessage, Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }
                     else {
                        groupName.setText(response.getResponse().getResults().get(0).getGroup().getName());

                    }
                    groupNameProgress.setVisibility(View.GONE);
                },
                error -> {
                    groupName.setText("");
                    groupNameProgress.setVisibility(View.GONE);

                    Snackbar.make(getView(), "Couldn't get clan data.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                });

        compositeDisposable.add(disposable);

    }

    public void getHistoricalStatsAccount(String membershipType, String membershipId) {

        Disposable disposable = mBungieAPI.getHistoricalStatsAccount(membershipType, membershipId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //Something went wrong
                    if(!response.getErrorCode().equals("1")){
                        String errorMessage = response.getMessage();
                        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();

                        playTime.setText("-");
                        lifeSpan.setText("-");
                        kdRatio.setText("-");
                    }

                    else {
                        try {
                            playTime.setText(response.getResponse().getMergedAllCharacters().getMerged().getAccountAllTime().getSecondsPlayed().getBasic().getDisplayValue());
                        } catch (Exception e) {
                            playTime.setText("-");
                        }

                        try {
                            lifeSpan.setText(response.getResponse().getMergedAllCharacters().getResults().getAllPvP().getAllTime().getAverageLifespan().getBasic().getDisplayValue());
                        } catch (Exception e) {
                            lifeSpan.setText("-");
                        }

                        try {
                            kdRatio.setText(response.getResponse().getMergedAllCharacters().getResults().getAllPvP().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue());
                        } catch (Exception e) {
                            kdRatio.setText("-");
                        }
                    }
                    statsValuesProgress.setVisibility(View.GONE);
                }, error -> {
                    playTime.setText("-");
                    lifeSpan.setText("-");
                    kdRatio.setText("-");
                    statsValuesProgress.setVisibility(View.GONE);

                    Snackbar.make(getView(), "Couldn't get account stats.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                });
        compositeDisposable.add(disposable);
    }


    public void getFactionStats(String membershipType, String membershipId, String characterId){

        Disposable disposable = mBungieAPI.getFactionProgress(membershipType, membershipId, characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response_factionProgression -> {

                    if(!response_factionProgression.getErrorCode().equals("1")){
                        Snackbar.make(getView(), response_factionProgression.getMessage(), Snackbar.LENGTH_LONG)
                                .show();
                    }
                    else{
                        ArrayList<String> factionHashList = new ArrayList<>();

                        //get stats from API response, keySet required for iteration instead of forLoop .get(i)
                        for(Iterator iterator = response_factionProgression.getResponse().getProgressions().getData().getFactions().keySet().iterator(); iterator.hasNext();){

                            FactionProgressModel factionProgressModel = new FactionProgressModel();

                            String currentKey = (String)iterator.next();
                            String primaryKey = UnsignedHashConverter.getPrimaryKey(currentKey);

                            factionHashList.add(primaryKey);


                            Log.d("FACTION_API_RESPONSE", primaryKey);
                            factionProgressModel.setPrimaryKey(primaryKey);
                            factionProgressModel.setFactionHash(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getFactionHash());
                            factionProgressModel.setCurrentProgress(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getCurrentProgress());
                            factionProgressModel.setProgressToNextLevel(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getProgressToNextLevel());
                            factionProgressModel.setLevel(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getLevel());
                            factionProgressModel.setNextLevelAt(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getNextLevelAt());

                            factionProgressionsList.add(factionProgressModel);
                        }

                        Collections.sort(factionHashList, (s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1)));

                        Collections.sort(factionProgressionsList, (f1, f2) -> Long.valueOf(f1.getPrimaryKey()).compareTo(Long.valueOf((f2.getPrimaryKey()))));

                        getFactionData(factionHashList);
                    }
                },throwable -> {

                    factionLoadingBar.setVisibility(View.GONE);
                    factionCardView.setVisibility(View.GONE);

                    if(throwable instanceof NoNetworkException) {
                        Snackbar.make(getView(), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", v -> retryLoadData())
                                .show();
                    }
                    else {
                        Snackbar.make(getView(), "Couldn't get faction stats.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }
        });
        compositeDisposable.add(disposable);
    }



    public void getFactionData(List<String> hashes) {

        FactionsDAO mFactionDAO = AppDatabase.getAppDatabase(getContext()).getFactionsDAO();

        Disposable disposable = mFactionDAO.getFactionsListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(factions -> {

                    for (int i = 0; i < factions.size(); i++) {

                        JsonParser parser = new JsonParser();
                        JsonObject factionObj = (JsonObject) parser.parse(factions.get(i).getValue());

                        if(factions.get(i).getId().equals(factionProgressionsList.get(i).getPrimaryKey())) {

//                            factionProgressionsList.get(i).getFactionHash().toString().equals(factionObj.get("hash").getAsString());

                            System.out.println("inside forLoop: " + factionProgressionsList.get(i).getFactionHash());
                            System.out.println("Room: " + factionObj.get("displayProperties").getAsJsonObject().get("name").getAsString());

                            //Append manifest data to faction progressions for character
                            factionProgressionsList.get(i).setFactionName(factionObj.get("displayProperties").getAsJsonObject().get("name").getAsString());
                            factionProgressionsList.get(i).setFactionIconUrl(factionObj.get("displayProperties").getAsJsonObject().get("icon").getAsString());
                        }
                    }

                    //get back onto UI thread
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    Runnable mRunnable = () -> {
                        mFactionProgressRecyclerAdapter = new LFGFactionsRecyclerAdapter(getContext(), factionProgressionsList);
                        int spanCount = 2;
                        GridLayoutManager gridManager = new GridLayoutManager(getContext(), spanCount);
                        mFactionProgressRecyclerView.setLayoutManager(gridManager);
                        mFactionProgressRecyclerView.setNestedScrollingEnabled(false);
                        mFactionProgressRecyclerView.setAdapter(mFactionProgressRecyclerAdapter);
                        factionLoadingBar.setVisibility(View.GONE);
                    };
                    mainHandler.post(mRunnable);

                }, throwable -> {
                    Snackbar.make(getView(), throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                        .show();
                    factionLoadingBar.setVisibility(View.GONE);
                    factionCardView.setVisibility(View.GONE);
                });

        compositeDisposable.add(disposable);
    }

    private void retryLoadData() {

//        show loading spinners again
        groupNameProgress.setVisibility(View.VISIBLE);
        statsValuesProgress.setVisibility(View.VISIBLE);
        factionLoadingBar.setVisibility(View.VISIBLE);
        factionCardView.setVisibility(View.VISIBLE);

        getHistoricalStatsAccount(receivedPlayerClick.getMembershipType(), receivedPlayerClick.getMembershipId());
        getGroupDetails(receivedPlayerClick.getMembershipType(), receivedPlayerClick.getMembershipId());
        getFactionStats(receivedPlayerClick.getMembershipType(), receivedPlayerClick.getMembershipId(), receivedPlayerClick.getCharacterId());
    }
}

















