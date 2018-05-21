package com.jastley.warmindfordestiny2.LFG.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.LFG.adapters.LFGFactionsRecyclerAdapter;
import com.jastley.warmindfordestiny2.LFG.holders.LFGFactionsViewHolder;
import com.jastley.warmindfordestiny2.LFG.models.FactionProgressModel;
import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.FactionsDAO;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LFGDetailsFragment.OnFragmentInteractionListener} interface
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

    @BindView(R.id.faction_progress_recyclerview) RecyclerView mFactionProgressRecyclerView;
    LFGFactionsRecyclerAdapter mFactionProgressRecyclerAdapter;

//    @BindView(R.id.lfg_details_emblem_icon) ImageView emblemIcon;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
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

            mBungieAPI = new RetrofitHelper().getBungieAPI(baseURL);

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

//        if(savedInstanceState != null){
//            LFGPost savedInstanceModel = savedInstanceState.getParcelable("savedPlayerClick");
//            ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();

////            actionBar.setTitle(savedInstanceModel.getDisplayName());
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//
//        else{
//            ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
//            actionBar.setTitle(receivedPlayerClick.getDisplayName());
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

//        ((MainActivity)getActivity()).showUpButton();
//        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lfgdetails, container, false);

        mFab = ((MainActivity) getActivity()).findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        toolbar = getActivity().findViewById(R.id.toolbar);

        appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

//        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
//        toggle.setDrawerIndicatorEnabled(false);

//        ((MainActivity) getActivity()).setDrawerHomeIcon();

        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_padded);
//
//        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);



//        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("Test");

//        SelectedPlayerModel clicked = mListener.onFragmentInteraction();

//        Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });

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

            Picasso.with(getContext())
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

            Picasso.with(getContext())
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
//        menu.clear();
//        // Inflate the menu; this adds items to the action bar if it is present.
//        inflater.inflate(R.menu.app_bar_new_lfg, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
                appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(false);
                appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                appCompatActivity.getSupportActionBar().set
                getActivity().onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.postDetails));
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        TabLayout mTabLayout = getActivity().findViewById(R.id.inventory_sliding_tabs);

        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




//    @Override
//    public void onFragmentInteraction(SelectedPlayerModel playerModel) {
//
//
//        receivedPlayerClick = playerModel;
//    }

//    @Override
//    public void onFragmentInteraction(SelectedPlayerModel playerModel) {
//        Toast.makeText(getActivity(), playerModel.getEmblemBackground(), Toast.LENGTH_SHORT).show();
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name


    }

    public void getGroupDetails(String membershipType, String membershipId) {

        mBungieAPI.getClanData(membershipType, membershipId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //Something went wrong
                    String errorCode = response.getErrorCode();
                    if(!errorCode.equals("1")) {
                        String errorMessage = response.getMessage();
                        Snackbar.make(getView(),  "Error "+ errorCode +": "+ errorMessage, Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }
                     else {
                        groupName.setText(response.getResponse().getResults().get(0).getGroup().getName());

                    }
                    groupNameProgress.setVisibility(View.GONE);
                },
                error -> groupName.setText(""));
    }

    public void getHistoricalStatsAccount(String membershipType, String membershipId) {

        mBungieAPI.getHistoricalStatsAccount(membershipType, membershipId)
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
                    Snackbar.make(getView(), "Couldn't get clan data.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                });
    }


    public void getFactionStats(String membershipType, String membershipId, String characterId){

        mBungieAPI.getFactionProgress(membershipType, membershipId, characterId)
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_factionProgression -> {

                    if(!response_factionProgression.getErrorCode().equals("1")){
                        Snackbar.make(getView(), response_factionProgression.getMessage(), Snackbar.LENGTH_LONG)
                                .show();
                    }
                    else{
                        List<Long> factionHashList = new ArrayList<>();

                        //get stats from API response, keySet required for iteration instead of forLoop .get(i)
                        for(Iterator iterator = response_factionProgression.getResponse().getProgressions().getData().getFactions().keySet().iterator(); iterator.hasNext();){
                            FactionProgressModel factionProgressModel = new FactionProgressModel();
                            String currentKey = (String)iterator.next();

                            Long factionHashTest = response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getFactionHash();

                            factionHashList.add(factionHashTest);



                            System.out.println("forLoop: " + factionHashTest);
//                            factionProgressModel.setFactionHash(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getFactionHash());
                            factionProgressModel.setCurrentProgress(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getCurrentProgress());
                            factionProgressModel.setProgressToNextLevel(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getProgressToNextLevel());
                            factionProgressModel.setLevel(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getLevel());
                            factionProgressModel.setNextLevelAt(response_factionProgression.getResponse().getProgressions().getData().getFactions().get(currentKey).getNextLevelAt());

                            factionProgressionsList.add(factionProgressModel);
                        }
                        getFactionData(factionHashList);
                    }
                },throwable -> Snackbar.make(getView(), throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                        .show());
    }



    public void getFactionData(List<Long> hashes) {

        FactionsDAO mFactionDAO = AppDatabase.getAppDatabase(getContext()).getFactionsDAO();

//        Long suros = 2856683562L;
        mFactionDAO.getFactionsListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(factions -> {

                    for (int i = 0; i < factions.size(); i++) {

                        JsonParser parser = new JsonParser();
                        JsonObject factionObj = (JsonObject) parser.parse(factions.get(i).getValue());

                        System.out.println("inside forLoop: " + factionProgressionsList.get(i).getFactionHash());
                        System.out.println("Room: " + factionObj.get("displayProperties").getAsJsonObject().get("name").getAsString());

                        //Append manifest data to faction progressions for character
                        factionProgressionsList.get(i).setFactionName(factionObj.get("displayProperties").getAsJsonObject().get("name").getAsString());
                        factionProgressionsList.get(i).setFactionIconUrl(factionObj.get("displayProperties").getAsJsonObject().get("icon").getAsString());
                    }

                    mFactionProgressRecyclerAdapter = new LFGFactionsRecyclerAdapter(getContext(), factionProgressionsList);
                    int spanCount = 2;
                    GridLayoutManager gridManager = new GridLayoutManager(getContext(), spanCount);
                    mFactionProgressRecyclerView.setLayoutManager(gridManager);
                    mFactionProgressRecyclerView.setNestedScrollingEnabled(false);
                    mFactionProgressRecyclerView.setAdapter(mFactionProgressRecyclerAdapter);
                    factionLoadingBar.setVisibility(View.GONE);
                });
    }
}

















