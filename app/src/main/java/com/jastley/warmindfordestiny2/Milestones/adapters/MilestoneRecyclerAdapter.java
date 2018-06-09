package com.jastley.warmindfordestiny2.Milestones.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.warmindfordestiny2.Milestones.holders.MilestoneViewHolder;
import com.jastley.warmindfordestiny2.Milestones.models.MilestoneModel;
import com.jastley.warmindfordestiny2.R;

import java.util.List;

public class MilestoneRecyclerAdapter extends RecyclerView.Adapter<MilestoneViewHolder> {

    List<MilestoneModel> milestonesList;

    public MilestoneRecyclerAdapter(List<MilestoneModel> milestonesList) {
        this.milestonesList = milestonesList;
    }

    @NonNull
    @Override
    public MilestoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.milestone_row_item, parent, false);

        return new MilestoneViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MilestoneViewHolder holder, int position) {
        holder.setMilestoneName(milestonesList.get(position).getMilestoneName());
        holder.setMilestoneDescription(milestonesList.get(position).getMilestoneDescription());
        holder.setMilestoneImage(milestonesList.get(position).getMilestoneImageURL());
        holder.setMilestoneRewardName(milestonesList.get(position).getMilestoneRewardName());
        holder.setMilestoneRewardImage(milestonesList.get(position).getMilestoneRewardImageURL());
    }

    @Override
    public int getItemCount() {
        return milestonesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
