package com.jastley.exodusnetwork.Inventory.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jastley.exodusnetwork.Inventory.InventoryViewModel;
import com.jastley.exodusnetwork.Inventory.adapters.ObjectiveRewardsAdapter;
import com.jastley.exodusnetwork.Inventory.adapters.ProgressRecyclerAdapter;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ObjectiveDetailsModal extends BottomSheetDialogFragment {

    //Included header layout
    @BindView(R.id.objective_name) TextView objectiveName;
    @BindView(R.id.objective_item_type) TextView objItemType;
    @BindView(R.id.objective_description) TextView objDescription;
    @BindView(R.id.rewards_title) TextView rewardsHeader;
    @BindView(R.id.objective_display_source) TextView objDisplaySource;
    @BindView(R.id.objective_progress_recycler_view) RecyclerView progressRecyclerView;

    //Modal layout file TODO - reward info
    @BindView(R.id.rewards_recycler_view) RecyclerView rewardsRecyclerView;

    ObjectiveRewardsAdapter objectiveRewardsAdapter;
    ProgressRecyclerAdapter progressRecyclerAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InventoryViewModel mViewModel;
    private InventoryItemModel selectedItem;


    private OnFragmentInteractionListener mListener;

    public ObjectiveDetailsModal() {
        // Required empty public constructor
    }

    public static ObjectiveDetailsModal newInstance(String param1, String param2) {
        ObjectiveDetailsModal fragment = new ObjectiveDetailsModal();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_objective_details_modal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

//        mViewModel = ViewModelProviders.of(getActivity()).get(InventoryViewModel.class);
//        selectedItem = mViewModel.getClickedItem();
//
//        initialiseRecyclerView();
//        setObjectiveUI();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(InventoryViewModel.class);
        selectedItem = mViewModel.getClickedItem();

        initialiseRecyclerView();
        setObjectiveUI();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initialiseRecyclerView() {
        objectiveRewardsAdapter = new ObjectiveRewardsAdapter();
        rewardsRecyclerView.setAdapter(objectiveRewardsAdapter);
        rewardsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rewardsRecyclerView.setNestedScrollingEnabled(false);
        //TODO RV animations?

        progressRecyclerAdapter = new ProgressRecyclerAdapter();
        progressRecyclerView.setAdapter(progressRecyclerAdapter);
        progressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressRecyclerView.setNestedScrollingEnabled(false);
    }

    private void setObjectiveUI() {

        //Objective-specific details
        mViewModel.getSingleItemData(selectedItem.getItemHash()).observe(this, objective -> {
            if(objective.getThrowable() != null) {

            }
            else if(objective.getMessage() != null) {

            }
            else if(objective.getSingleItemData() != null) {
//                objDescription.setText(objective.getSingleItemData().getDisplayProperties().getDescription());

            }
        });
        if(!selectedItem.getDescription().equals("")) {
            objDescription.setText(selectedItem.getDescription());
        }
        if(!selectedItem.getItemName().equals("")) {
            objectiveName.setText(selectedItem.getItemName());
        }
        if(!selectedItem.getItemTypeDisplayName().equals("")) {
            objItemType.setText(selectedItem.getItemTypeDisplayName());
        }

        //Objective rewards
        mViewModel.getRewardsList().observe(this, rewards -> {
            if(rewards.getItemDataList() != null) {
                rewardsHeader.setVisibility(View.VISIBLE);
                objectiveRewardsAdapter.setItemList(rewards.getItemDataList());
            }
        });


//        if(selectedItem.getObjectivesList().get(0).isVisible()){
        if(selectedItem.getObjectivesList() != null) {
            mViewModel.getObjectiveData(selectedItem.getObjectiveHashList(), selectedItem.getObjectivesList()).observe(this, objectives -> {
                if(objectives != null) {
                    progressRecyclerAdapter.setItemsList(objectives);
                }
            });

            objItemType.setText(selectedItem.getItemTypeDisplayName());
            objectiveName.setText(selectedItem.getItemName());
            if(!selectedItem.getDisplaySource().equals("")) {
                objDisplaySource.setVisibility(View.VISIBLE);
                objDisplaySource.setText(selectedItem.getDisplaySource());
            }

        }
    }
}
