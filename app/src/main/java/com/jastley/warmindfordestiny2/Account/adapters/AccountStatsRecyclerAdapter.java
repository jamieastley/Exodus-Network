package com.jastley.warmindfordestiny2.Account.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jastley.warmindfordestiny2.Account.holders.AccountStatsViewHolder;
import com.jastley.warmindfordestiny2.Account.models.AccountStatsModel;
import com.jastley.warmindfordestiny2.R;

import java.util.List;

public class AccountStatsRecyclerAdapter extends RecyclerView.Adapter<AccountStatsViewHolder> {

    private List<AccountStatsModel> statsList;

    public AccountStatsRecyclerAdapter(List<AccountStatsModel> stats) {
        this.statsList = stats;
    }

    @NonNull
    @Override
    public AccountStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountStatsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.account_stats_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountStatsViewHolder holder, int position) {
        holder.setStatName(statsList.get(position).getStatName());
        holder.setStatValue(statsList.get(position).getStatValue());
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
