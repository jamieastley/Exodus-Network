package com.jastley.exodusnetwork.Vendors.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.stat_title) TextView statName;
    @BindView(R.id.stat_value) TextView statValue;
    @BindView(R.id.stat_progress_bar) ProgressBar statProgressBar;


    public StatViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public String getStatName() {
        return statName.getText().toString();
    }

    public void setStatName(String name) {
        this.statName.setText(name);
    }

    public String getStatValue() {
        return statValue.getText().toString();
    }

    public void setStatValue(String value) {
        this.statValue.setText(value);
    }

    public ProgressBar getStatProgressBar() {
        return statProgressBar;
    }

    public void setStatProgressBar(int value) {

        if(value <= 100) {
            this.statProgressBar.setVisibility(View.VISIBLE);
            this.statProgressBar.setMax(100);
            this.statProgressBar.setProgress(value);
        }
        else {
            this.statProgressBar.setVisibility(View.VISIBLE);
            this.statProgressBar.setMax(value);
            this.statProgressBar.setProgress(value);
        }
    }
}
