package com.jastley.warmindfordestiny2.LFG.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.jastley.warmindfordestiny2.LFG.holders.LFGFactionsViewHolder;
import com.jastley.warmindfordestiny2.LFG.models.FactionProgressModel;
import com.jastley.warmindfordestiny2.R;

import java.util.List;

public class LFGFactionsRecyclerAdapter extends RecyclerView.Adapter<LFGFactionsViewHolder> {

    private Context mContext;
    private List<FactionProgressModel> factionProgressModelList;

    public LFGFactionsRecyclerAdapter(Context mContext, List<FactionProgressModel> factionProgressModelList) {
        this.mContext = mContext;
        this.factionProgressModelList = factionProgressModelList;
    }

    @Override
    public LFGFactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LFGFactionsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.faction_progress_row, parent, false));
    }

    @Override
    public void onBindViewHolder(LFGFactionsViewHolder holder, int position) {
//        holder.setFactionName(factionProgressModelList.get(position).getFactionName());
        holder.setFactionIcon(factionProgressModelList.get(position).getFactionIconUrl(), mContext);
        holder.setFactionLevelText(factionProgressModelList.get(position).getLevel());
        holder.setFactionProgressBar(factionProgressModelList.get(position).getProgressToNextLevel(),factionProgressModelList.get(position).getNextLevelAt());
        holder.setFactionProgressText(factionProgressModelList.get(position).getProgressToNextLevel(),factionProgressModelList.get(position).getNextLevelAt());
    }

    @Override
    public int getItemCount() {
        return factionProgressModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
