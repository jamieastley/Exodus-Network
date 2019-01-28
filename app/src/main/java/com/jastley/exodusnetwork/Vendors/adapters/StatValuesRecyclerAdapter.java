package com.jastley.exodusnetwork.Vendors.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Vendors.holders.StatViewHolder;
import com.jastley.exodusnetwork.Vendors.models.SocketModel;

import java.util.ArrayList;
import java.util.List;

public class StatValuesRecyclerAdapter extends RecyclerView.Adapter<StatViewHolder> {

    private List<SocketModel.StatValues> statData = new ArrayList<>();

    @NonNull
    @Override
    public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stat_row, parent, false);

        return new StatViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatViewHolder holder, int position) {

        if(!statData.get(position).getStatName().equals("") &&
                statData.get(position).getValue() != 0) {

            holder.setStatName(statData.get(position).getStatName());
            holder.setStatValue(String.valueOf(statData.get(position).getValue()));
            holder.setStatProgressBar(statData.get(position).getValue(), statData.get(position).getMaximum());
        }
    }

    @Override
    public int getItemCount() {
        return statData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setStatData(List<SocketModel.StatValues> statList) {
        this.statData = statList;
        notifyDataSetChanged();
    }
}
