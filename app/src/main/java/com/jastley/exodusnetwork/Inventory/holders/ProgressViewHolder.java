package com.jastley.exodusnetwork.Inventory.holders;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.objective_status_icon) ImageView statusIcon;
    @BindView(R.id.objective_progress_bar) ProgressBar progressBar;
    @BindView(R.id.objective_data_name) TextView objDataName;
    @BindView(R.id.objective_progress_value) TextView progressValue;


    public ProgressViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setStatusIcon(boolean completed, Context context) {
        if(completed) {
            statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.objective_completed_border));
        }
    }

    public void setProgressBar(int progress, int max) {
        this.progressBar.setMax(max);
        this.progressBar.setProgress(progress);
    }

    public void setObjDataName(String name) {
        this.objDataName.setText(name);
    }

    public void setProgressValue(int progress, int max) {
        String concat = String.valueOf(progress) + "/" + String.valueOf(max);
        this.progressValue.setText(concat);
    }
}
