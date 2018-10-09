package com.jastley.exodusnetwork.checklists.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class GhostLoreFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.ghost_lore_recycler) RecyclerView ghostLoreRecycler;
    @BindView(R.id.ghost_lore_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    private ChecklistTextRecyclerAdapter mRecyclerAdapter;
    private ChecklistsViewModel mViewModel;

    private String mParam1;
    private String mParam2;

    public GhostLoreFragment() {
        // Required empty public constructor
    }

    public static GhostLoreFragment newInstance(String param1, String param2) {
        GhostLoreFragment fragment = new GhostLoreFragment();
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
        View mView = inflater.inflate(R.layout.fragment_ghost_lore, container, false);

        ButterKnife.bind(this, mView);
        mSwipeRefresh.setRefreshing(true);
        setSwipeRefreshListener();
        initialiseRecyclerView();

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ChecklistsViewModel.class);

        getGhostLore();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initialiseRecyclerView() {
        mRecyclerAdapter = new ChecklistTextRecyclerAdapter();
        ghostLoreRecycler.setAdapter(mRecyclerAdapter);
        ghostLoreRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getGhostLore() {
        mViewModel.getGhostLore().observe(this, ghostLore -> {
            if(!ghostLore.getChecklistModelList().isEmpty()) {
                mRecyclerAdapter.setChecklistData(ghostLore.getChecklistModelList());
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void setSwipeRefreshListener() {
        mSwipeRefresh.setOnRefreshListener(() -> {
            mSwipeRefresh.setRefreshing(true);
            mViewModel.loadChecklistData();
        });
    }
}
