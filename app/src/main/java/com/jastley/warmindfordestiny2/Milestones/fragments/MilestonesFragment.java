package com.jastley.warmindfordestiny2.Milestones.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jastley.warmindfordestiny2.Milestones.ViewModels.MilestonesViewModel;
import com.jastley.warmindfordestiny2.R;

import butterknife.BindView;

public class MilestonesFragment extends Fragment {

    @BindView(R.id.milestones_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.milestones_recycler_view) RecyclerView mMilestonesRecyclerView;

    private OnMilestoneFragmentInteractionListener mListener;
    private MilestonesViewModel mViewModel;

    public static MilestonesFragment newInstance() {
        return new MilestonesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_milestones, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MilestonesViewModel.class);
        // TODO: Use the ViewModel
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
}
