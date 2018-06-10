package com.jastley.warmindfordestiny2.Milestones.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.warmindfordestiny2.Milestones.ViewModels.MilestonesViewModel;
import com.jastley.warmindfordestiny2.Milestones.models.MilestoneModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.Utils.UnsignedHashConverter;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.MilestoneDAO;
import com.jastley.warmindfordestiny2.database.models.MilestoneData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class MilestonesFragment extends Fragment {

    @BindView(R.id.milestones_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.milestones_recycler_view) RecyclerView mMilestonesRecyclerView;

    private OnMilestoneFragmentInteractionListener mListener;
    private MilestonesViewModel mViewModel;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BungieAPI mBungieAPI;

    public static MilestonesFragment newInstance() {
        return new MilestonesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBungieAPI = RetrofitHelper.getAuthBungieAPI(getContext(), baseURL);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getMilestones();

        return inflater.inflate(R.layout.fragment_milestones, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MilestonesViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMilestoneFragmentInteractionListener) {
            mListener = (OnMilestoneFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailsFragmentInteraction");
        }
    }

    public interface OnMilestoneFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnMilestoneFragmentInteractionListener(Uri uri);
    }

    public void getMilestones() {

        Disposable disposable = mBungieAPI.getMilestones()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response_getMilestones -> {

                    if(!response_getMilestones.getErrorCode().equals("1")) {
                        String errorMessage = response_getMilestones.getMessage();
                        Snackbar.make(getView(),  errorMessage, Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }
                    else {

                        List<MilestoneModel> milestoneModelList = new ArrayList<>();
                        ArrayList<String> hashList = new ArrayList<>();

                        for (String currentKey : response_getMilestones.getMilestoneHashes().keySet()) {

                            MilestoneModel milestoneData = new MilestoneModel();

                            String primaryKey = UnsignedHashConverter.getPrimaryKey(currentKey);

                            milestoneData.setMilestoneHash(response_getMilestones.getMilestoneHashes().get(currentKey).getMilestoneHash());
                            milestoneData.setPrimaryKey(primaryKey);
                            milestoneModelList.add(milestoneData);

                            //get hash value as represented in the database
                            hashList.add(primaryKey);
                        }

                        Collections.sort(hashList, (s, t1) -> Long.valueOf(s).compareTo(Long.valueOf(t1)));
                        Collections.sort(milestoneModelList, (m1, m2) -> Long.valueOf(m1.getPrimaryKey()).compareTo(Long.valueOf((m2.getPrimaryKey()))));

                        getMilestoneData(milestoneModelList, hashList);
                    }

                }, throwable -> {
                    if(throwable instanceof NoNetworkException) {
                        Snackbar.make(getView(), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", v -> getMilestones())
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

    public void getMilestoneData(List<MilestoneModel> milestoneModels, List<String> hashes) {

        MilestoneDAO mMilestonesDAO = AppDatabase.getAppDatabase(getContext()).getMilestonesDAO();

        Disposable disposable = mMilestonesDAO.getMilestoneListByKey(hashes)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(milestones -> {

//                    List<MilestoneData> milestoneDataList = new ArrayList<>();
                    for (int i = 0; i < milestones.size(); i++) {

                        //Map the milestone data to a model class so we don't have to manually parse JSON
                        Gson gson = new GsonBuilder().create();
                        MilestoneData data = gson.fromJson(milestones.get(i).getValue(), MilestoneData.class);

                        milestoneModels.get(i).setMilestoneName(data.getDisplayProperties().getName());

                        if(data.getDisplayProperties().getDescription() != null) {
                            milestoneModels.get(i).setMilestoneDescription(data.getDisplayProperties().getDescription());
                        }
                        if(data.getDisplayProperties().getIcon() != null) {
                            milestoneModels.get(i).setMilestoneImageURL(data.getDisplayProperties().getIcon());
                        }

                        for(String key : data.getQuestsData().keySet()){
                            if(data.getQuestsData().get(key).getQuestRewards().getRewardItemsList().get(0).getItemHash() != null){
                                milestoneModels.get(i).setMilestoneRewardHash(data.getQuestsData().get(key).getQuestRewards().getRewardItemsList().get(0).getItemHash());
                            }
                        }

                    }


                }, throwable -> {
                    Snackbar.make(getView(), throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                            .show();
                    //TODO hide loading
                });
        compositeDisposable.add(disposable);
    }

}
