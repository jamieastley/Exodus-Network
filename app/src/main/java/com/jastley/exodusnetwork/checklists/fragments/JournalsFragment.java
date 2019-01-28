package com.jastley.exodusnetwork.checklists.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.checklists.ChecklistsViewModel;
import com.jastley.exodusnetwork.checklists.adapters.ChecklistTextRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.cayde_journals_recycler) RecyclerView journalsRecyclerView;
    @BindView(R.id.journals_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    private ChecklistTextRecyclerAdapter mRecyclerAdapter;
    ChecklistsViewModel mViewModel;

    public JournalsFragment() {
        // Required empty public constructor
    }

    public static JournalsFragment newInstance(String param1, String param2) {
        JournalsFragment fragment = new JournalsFragment();
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
        View mView = inflater.inflate(R.layout.fragment_journals, container, false);

        ButterKnife.bind(this, mView);
        mSwipeRefresh.setRefreshing(true);
        setSwipeRefreshListener();
        initialiseRecyclerViews();

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ChecklistsViewModel.class);

        getJournals();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void initialiseRecyclerViews() {
        mRecyclerAdapter = new ChecklistTextRecyclerAdapter();
        journalsRecyclerView.setAdapter(mRecyclerAdapter);
        journalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getJournals() {
        mViewModel.getJournals().observe(this, journals -> {
            if(!journals.getChecklistModelList().isEmpty()) {
                mRecyclerAdapter.setChecklistData(journals.getChecklistModelList());
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
