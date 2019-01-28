package com.jastley.exodusnetwork.checklists.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.checklists.holders.ChecklistImageViewHolder;
import com.jastley.exodusnetwork.checklists.models.ChecklistModel;

import java.util.ArrayList;
import java.util.List;

public class ChecklistImageRecyclerAdapter extends RecyclerView.Adapter<ChecklistImageViewHolder> {

    private List<ChecklistModel> checklistData = new ArrayList<>();

    @NonNull
    @Override
    public ChecklistImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_image_item, parent, false);

        return new ChecklistImageViewHolder(mView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistImageViewHolder holder, int position) {
        holder.setItemImage(checklistData.get(position).getItemImage());
        holder.setCompleted(checklistData.get(position).isCompleted());
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
