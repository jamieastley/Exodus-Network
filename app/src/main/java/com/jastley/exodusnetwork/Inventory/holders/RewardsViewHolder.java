package com.jastley.exodusnetwork.Inventory.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

public class RewardsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reward_icon) ImageView rewardIcon;
    @BindView(R.id.reward_name) TextView rewardName;

    public RewardsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setRewardIcon(String url) {

        Picasso.get()
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(rewardIcon);
    }

    public void setRewardName(String name) {
        this.rewardName.setText(name);
    }
}
