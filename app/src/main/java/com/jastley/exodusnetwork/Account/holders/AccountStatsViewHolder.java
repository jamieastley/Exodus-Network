package com.jastley.exodusnetwork.Account.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountStatsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.stat_name) TextView statName;
    @BindView(R.id.stat_value) TextView statValue;

    public AccountStatsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public String getStatName() {
        return statName.getText().toString();
    }

    public void setStatName(String statName) {
        //TODO: create util class to clean up stat name and return it
        this.statName.setText(statName);
    }

    public String getStatValue() {
        return statValue.getText().toString();
    }

    public void setStatValue(String statValue) {
        this.statValue.setText(statValue);
    }
}
