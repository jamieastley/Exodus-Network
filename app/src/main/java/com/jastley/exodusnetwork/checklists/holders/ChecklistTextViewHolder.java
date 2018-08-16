package com.jastley.exodusnetwork.checklists.holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;

public class ChecklistTextViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.checklist_text_layout) FrameLayout layoutContainer;
    @BindView(R.id.checklist_item_name) TextView checklistName;
    private boolean isCompleted;
    private Context mContext;

    public ChecklistTextViewHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
    }

    public String getChecklistName() {
        return checklistName.getText().toString();
    }

    public void setChecklistName(String title) {
        this.checklistName.setText(title);
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
        if(isCompleted) {
            layoutContainer.setForeground(ContextCompat.getDrawable(mContext, R.drawable.checklist_completion));
        }
    }
}
