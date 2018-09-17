package com.jastley.exodusnetwork.checklists.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Utils.ColumnCalculator;
import com.jastley.exodusnetwork.checklists.ChecklistsViewModel;
import com.jastley.exodusnetwork.checklists.adapters.ChecklistImageRecyclerAdapter;
import com.jastley.exodusnetwork.checklists.adapters.ChecklistTextRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.Definitions.caydeJournals;
import static com.jastley.exodusnetwork.Definitions.forsakenCollection;
import static com.jastley.exodusnetwork.Definitions.ghostLore;
import static com.jastley.exodusnetwork.Definitions.latentMemories;
import static com.jastley.exodusnetwork.Definitions.raidLairs;
import static com.jastley.exodusnetwork.Definitions.sleeperNodes;


public class ChecklistsContentFragment extends Fragment {

    @BindView(R.id.checklist_recyclerview) RecyclerView mRecyclerView;
    private ChecklistsViewModel mViewModel;
    private static String mTabTitle;
    private static String mTabHash;
    private ChecklistTextRecyclerAdapter mTextAdapter;
    private ChecklistImageRecyclerAdapter mImageAdapter;

    public ChecklistsContentFragment() {
        // Required empty public constructor
    }

    public static ChecklistsContentFragment newInstance(String title, String hash) {
        ChecklistsContentFragment fragment = new ChecklistsContentFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("hash", hash);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabTitle = getArguments().getString("title");
            mTabHash = getArguments().getString("hash");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_checklists_content, container, false);

        ButterKnife.bind(this, rootView);
        initialiseViews();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mViewModel = ViewModelProviders.of(getActivity()).get(ChecklistsViewModel.class);



    }

    @Override
    public void onResume() {
        super.onResume();

        getChecklistData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initialiseViews() {

        switch(mTabHash) {
            case forsakenCollection:
                mImageAdapter = new ChecklistImageRecyclerAdapter();
                mRecyclerView.setAdapter(mImageAdapter);
                int columnCount = ColumnCalculator.calculateNoOfColumns(getActivity().getApplicationContext());
                mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));

                break;
            default:
                mTextAdapter = new ChecklistTextRecyclerAdapter();
                mRecyclerView.setAdapter(mTextAdapter);
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setLayoutAnimation(controller);
                break;
        }
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void getChecklistData() {

        switch (mTabHash) {
            case latentMemories:
                mViewModel.getLatentMemories().observe(this, memories -> {
                    if(memories.getErrorMessage() != null) {
                        //TODO
                    }
                    else if(!memories.getChecklistModelList().isEmpty()) {
                        mTextAdapter.setChecklistData(memories.getChecklistModelList());
                    }
                });
                break;
            case ghostLore:
                mViewModel.getGhostLore().observe(this, lore -> {
                    if(lore.getErrorMessage() != null) {
                        //TODO
                    }
                    else if(!lore.getChecklistModelList().isEmpty()) {
                        mTextAdapter.setChecklistData(lore.getChecklistModelList());
                    }
                });
                break;
            case caydeJournals:
                mViewModel.getJournals().observe(this, journals -> {
                    if(journals.getErrorMessage() != null) {
                        //TODO
                    }
                    else if(!journals.getChecklistModelList().isEmpty()) {
                        mTextAdapter.setChecklistData(journals.getChecklistModelList());
                    }
                });
                break;
            case sleeperNodes:
                mViewModel.getSleeperNodes().observe(this, nodes -> {
                    if(nodes.getErrorMessage() != null) {
                        //TODO
                    }
                    else if(!nodes.getChecklistModelList().isEmpty()) {
                        mTextAdapter.setChecklistData(nodes.getChecklistModelList());
                    }
                });
                break;
            case raidLairs:
                mViewModel.getRaidLairs().observe(this, lairs -> {
                    if(lairs.getErrorMessage() != null) {
                        //TODO
                    }
                    else if(!lairs.getChecklistModelList().isEmpty()) {
                        mTextAdapter.setChecklistData(lairs.getChecklistModelList());
                    }
                });
                break;
            case forsakenCollection:
                mViewModel.getForsakenItems().observe(this, items -> {
                    if(items.getErrorMessage() != null) {
                        //TODO
                    }
                    else if(!items.getChecklistModelList().isEmpty()) {
                        mImageAdapter.setChecklistData(items.getChecklistModelList());
                    }
                });
                break;
        }

    }
}
