package com.jastley.warmindfordestiny2.Milestones.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class MilestoneViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.milestone_name) TextView milestoneName;
    @BindView(R.id.milestone_description) TextView milestoneDescription;
    @BindView(R.id.milestone_image) ImageView milestoneImage;
    @BindView(R.id.milestone_reward_name) TextView milestoneRewardName;
    @BindView(R.id.milestone_reward_image) ImageView milestoneRewardImage;

    public MilestoneViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public String getMilestoneName() {
        return milestoneName.getText().toString();
    }

    public void setMilestoneName(String name) {
        this.milestoneName.setText(name);
    }

    public TextView getMilestoneDescription() {
        return milestoneDescription;
    }

    public void setMilestoneDescription(String description) {
        this.milestoneDescription.setText(description);
    }

    public ImageView getMilestoneImage() {
        return milestoneImage;
    }

    public void setMilestoneImage(String url) {

        Picasso.get()
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(milestoneImage);
    }

    public String getMilestoneRewardName() {
        return milestoneRewardName.getText().toString();
    }

    public void setMilestoneRewardName(String rewardName) {
        this.milestoneRewardName.setText(rewardName);
    }

    public ImageView getMilestoneRewardImage() {
        return milestoneRewardImage;
    }

    public void setMilestoneRewardImage(String url) {

        Picasso.get()
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(milestoneRewardImage);
    }
}
