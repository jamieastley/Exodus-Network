package com.jastley.warmindfordestiny2.LFG.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.R;
import com.squareup.picasso.Picasso;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class LFGFactionsViewHolder extends RecyclerView.ViewHolder {

    View mView;

    @BindView(R.id.faction_icon) ImageView factionIcon;
    @BindView(R.id.faction_name) TextView factionName;
    @BindView(R.id.faction_progression_bar) ProgressBar factionProgressBar;
    @BindView(R.id.faction_progress_text) TextView factionProgressText;
    @BindView(R.id.faction_level) TextView factionLevelText;

//    private String progressToNextLevel;
//    private String nextLevelAt;

    public LFGFactionsViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;

        ButterKnife.bind(this, itemView);
    }

    public ImageView getFactionIcon() {
        return factionIcon;
    }

    public void setFactionIcon(String url, Context context) {
//        this.factionIcon = factionIcon;

        Picasso.with(context)
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(this.factionIcon);
    }

    public TextView getFactionName() {
        return factionName;
    }

    public void setFactionName(String name) {
        this.factionName.setText(name);
    }

    public ProgressBar getFactionProgressBar() {
        return factionProgressBar;
    }

    public void setFactionProgressBar(String progress, String nextLevelAt) {

        //setMax must be called first
        this.factionProgressBar.setMax(Integer.parseInt(nextLevelAt));
        this.factionProgressBar.setProgress(Integer.parseInt(progress));

    }

    public TextView getFactionProgressText() {
        return factionProgressText;
    }

    public void setFactionProgressText(String progress, String nextLevel) {

        String concatProgress = mView.getResources().getString(R.string.concatFactionProgress, progress, nextLevel);

        this.factionProgressText.setText(concatProgress);
    }
//
//    public String getProgressToNextLevel() {
//        return progressToNextLevel;
//    }
//
//    public void setProgressToNextLevel(String progressToNextLevel) {
//        this.progressToNextLevel = progressToNextLevel;
//    }
//
//    public String getNextLevelAt() {
//        return nextLevelAt;
//    }
//
//    public void setNextLevelAt(String nextLevelAt) {
//        this.nextLevelAt = nextLevelAt;
//    }


    public TextView getFactionLevelText() {
        return factionLevelText;
    }

    public void setFactionLevelText(String level) {

        String concatLevel = mView.getResources().getString(R.string.concatFactionLevel, level);
        this.factionLevelText.setText(concatLevel);
    }
}
