package com.jastley.exodusnetwork.overview.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.overview.viewholders.EmblemViewHolder;

import java.util.List;

public class EmblemRecyclerAdapter extends RecyclerView.Adapter<EmblemViewHolder> {



    @NonNull
    @Override
    public EmblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EmblemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setItemsList(List<?> list) {
        //TODO
        notifyDataSetChanged();
    }


}
