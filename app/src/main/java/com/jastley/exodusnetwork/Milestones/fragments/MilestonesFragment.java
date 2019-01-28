package com.jastley.exodusnetwork.Milestones.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.Milestones.adapters.MilestoneRecyclerAdapter;
import com.jastley.exodusnetwork.Milestones.viewmodels.MilestoneViewModel;
import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MilestonesFragment extends Fragment {

    @BindView(R.id.milestones_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.milestones_recycler_view) RecyclerView mMilestonesRecyclerView;
    @BindView(R.id.milestone_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;

    private OnMilestoneFragmentInteractionListener mListener;
    private MilestoneViewModel mViewModel;
    private MilestoneRecyclerAdapter mMilestonesAdapter;


    public static MilestonesFragment newInstance() {
        return new MilestonesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_milestones, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMilestonesAdapter = new MilestoneRecyclerAdapter();
        mMilestonesRecyclerView.setAdapter(mMilestonesAdapter);

        mMilestonesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
        mMilestonesRecyclerView.setLayoutAnimation(controller);
        mMilestonesRecyclerView.setNestedScrollingEnabled(false);

        mViewModel = ViewModelProviders.of(this).get(MilestoneViewModel.class);
//        mViewModel.getMilestones().observe(this, milestoneModels -> {
//            mMilestonesAdapter.setMilestones(milestoneModels);
//            mProgressBar.setVisibility(View.GONE);
//        });
        getMilestones();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        getMilestones();

        mSwipeRefresh.setOnRefreshListener(() -> {
            mSwipeRefresh.setRefreshing(true);
//            getMilestones();
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.milestones));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //Hide LFG toolbar buttons
        menu.clear();

        inflater.inflate(R.menu.refresh_toolbar, menu);

//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.refresh_button:
                mSwipeRefresh.setRefreshing(true);
                refreshMilestones();
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnMilestoneFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnMilestoneFragmentInteractionListener(Uri uri);
    }

    public void getMilestones() {
        mViewModel.getMilestones().observe(this, milestoneModels -> {
            if(milestoneModels == null) {
                showSnackbar("An error occurred while retrieving milestone data.");
            }
            else if(milestoneModels.getMessage() != null) {
                showSnackbar(milestoneModels.getMessage());
            }
            else if(milestoneModels.getError() != null) {
                showSnackbar(milestoneModels.getMessage());
            }
            else {
                mMilestonesAdapter.setMilestones(milestoneModels.getMilestoneList());
            }
            mProgressBar.setVisibility(View.GONE);
        });
    }

    public void refreshMilestones() {
        mProgressBar.setVisibility(View.VISIBLE);
        mViewModel.refreshMilestones();
    }

    public void showSnackbar(String message) {
        Snackbar.make(getView(),  message, Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry", v -> getMilestones())
                .show();
    }
}
