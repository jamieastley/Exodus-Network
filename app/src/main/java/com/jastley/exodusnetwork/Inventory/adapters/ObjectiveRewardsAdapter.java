package com.jastley.exodusnetwork.Inventory.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.Inventory.holders.RewardsViewHolder;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveRewardsAdapter extends RecyclerView.Adapter<RewardsViewHolder> {

    private List<InventoryItemJsonData> itemList = new ArrayList<>();

    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RewardsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsViewHolder holder, int position) {
        holder.setRewardIcon(itemList.get(position).getDisplayProperties().getIcon());
        holder.setRewardName(itemList.get(position).getDisplayProperties().getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItemList(List<InventoryItemJsonData> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }
}
