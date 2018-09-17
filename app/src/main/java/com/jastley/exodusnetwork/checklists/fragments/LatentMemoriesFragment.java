package com.jastley.exodusnetwork.checklists.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.checklists.ChecklistsViewModel;
import com.jastley.exodusnetwork.checklists.adapters.ChecklistTextRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LatentMemoriesFragment extends Fragment {

    @BindView(R.id.latent_memories_recyclerview) RecyclerView mRecyclerView;
    private ChecklistTextRecyclerAdapter mRecyclerAdapter;

    private ChecklistsViewModel mViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LatentMemoriesFragment() {
        // Required empty public constructor
    }

    public static LatentMemoriesFragment newInstance(String param1, String param2) {
        LatentMemoriesFragment fragment = new LatentMemoriesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_latent_memories, container, false);
        ButterKnife.bind(this, rootView);

        initialiseViews();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ChecklistsViewModel.class);

        getMemories();
    }

    private void initialiseViews() {
        mRecyclerAdapter = new ChecklistTextRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getMemories() {
        mViewModel.getLatentMemories().observe(this, memories -> {
            if(memories.getErrorMessage() != null) {
                //TODO
            }
            else if(!memories.getChecklistModelList().isEmpty()) {
                mRecyclerAdapter.setChecklistData(memories.getChecklistModelList());
            }
        });
    }
}
