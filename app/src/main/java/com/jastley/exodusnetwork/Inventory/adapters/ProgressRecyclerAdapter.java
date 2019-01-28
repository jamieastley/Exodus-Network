package com.jastley.exodusnetwork.Inventory.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.Inventory.holders.ProgressViewHolder;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.api.models.Response_GetCharacterInventory;

import java.util.ArrayList;
import java.util.List;

public class ProgressRecyclerAdapter extends RecyclerView.Adapter<ProgressViewHolder> {

    private List<Response_GetCharacterInventory.Objectives.ObjectiveData.ItemObjectives> itemList = new ArrayList<>();

    private Context mContext;

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.objective_progress_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        holder.setStatusIcon(itemList.get(position).isComplete(), mContext);
        holder.setProgressBar(itemList.get(position).getProgress(), itemList.get(position).getCompletionValue());
        holder.setObjDataName(itemList.get(position).getObjectiveDataName());
        holder.setProgressValue(itemList.get(position).getProgress(), itemList.get(position).getCompletionValue());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItemsList(List<Response_GetCharacterInventory.Objectives.ObjectiveData.ItemObjectives> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }
}
