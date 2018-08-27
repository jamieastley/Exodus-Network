package com.jastley.exodusnetwork.checklists.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SleeperNodesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SleeperNodesFragment extends Fragment {

    @BindView(R.id.sleeper_nodes_recycler) RecyclerView mRecyclerView;
    ChecklistTextRecyclerAdapter mRecyclerAdapter;
    ChecklistsViewModel mViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SleeperNodesFragment() {
        // Required empty public constructor
    }

    public static SleeperNodesFragment newInstance(String param1, String param2) {
        SleeperNodesFragment fragment = new SleeperNodesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_sleeper_nodes, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ChecklistsViewModel.class);

        getSleeperNodes();
    }

    private void getSleeperNodes() {
        mViewModel.getSleeperNodes().observe(this, nodes -> {
            if(nodes.getErrorMessage() != null) {
                //TODO
            }
            else if(!nodes.getChecklistModelList().isEmpty()) {
                mRecyclerAdapter.setChecklistData(nodes.getChecklistModelList());
            }
        });
    }

    private void initialiseViews() {
        mRecyclerAdapter = new ChecklistTextRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
    }
}
