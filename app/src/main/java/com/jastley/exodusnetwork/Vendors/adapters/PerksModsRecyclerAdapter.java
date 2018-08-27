package com.jastley.exodusnetwork.Vendors.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Vendors.holders.PerksModsViewHolder;
import com.jastley.exodusnetwork.Vendors.models.SocketModel;

import java.util.ArrayList;
import java.util.List;

public class PerksModsRecyclerAdapter extends RecyclerView.Adapter<PerksModsViewHolder> {

    private List<SocketModel> socketModelList = new ArrayList<>();

    @NonNull
    @Override
    public PerksModsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.perk_mod_item_row, parent, false);

        return new PerksModsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PerksModsViewHolder holder, int position) {
        holder.setSocketIcon(socketModelList.get(position).getSocketIcon());
        holder.setSocketName(socketModelList.get(position).getSocketName());
        holder.setSocketDesc(socketModelList.get(position).getSocketDescription());
    }

    @Override
    public int getItemCount() {
        return socketModelList.size();
    }

    public void setListData(List<SocketModel> list) {
        socketModelList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
