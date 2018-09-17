package com.jastley.exodusnetwork.settings;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.settings_title) TextView title;
    @BindView(R.id.settings_details) TextView details;

    public SettingsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void setDetails(String text) {
        if(text != null){
            this.details.setText(text);
        }
        else {
            this.details.setVisibility(View.GONE);
        }
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public String getDetails() {
        return details.getText().toString();
    }
}
