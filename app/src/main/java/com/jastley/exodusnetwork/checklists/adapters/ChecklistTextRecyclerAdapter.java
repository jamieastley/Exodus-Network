package com.jastley.exodusnetwork.checklists.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.checklists.holders.ChecklistTextViewHolder;
import com.jastley.exodusnetwork.checklists.models.ChecklistModel;

import java.util.ArrayList;
import java.util.List;

public class ChecklistTextRecyclerAdapter extends RecyclerView.Adapter<ChecklistTextViewHolder> {

    private List<ChecklistModel> checklistData = new ArrayList<>();

    public ChecklistTextRecyclerAdapter() {
    }

    @NonNull
    @Override
    public ChecklistTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_text_item, parent, false);

        return new ChecklistTextViewHolder(mView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistTextViewHolder holder, int position) {
        holder.setChecklistName(checklistData.get(position).getChecklistItemName());
        holder.setCompleted(checklistData.get(position).isCompleted());

        //Sleeper Nodes
        if(checklistData.get(position).getDescription() != null) {
            holder.setDescription(checklistData.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return checklistData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setChecklistData(List<ChecklistModel> list) {
        this.checklistData = list;
        notifyDataSetChanged();
    }
}
