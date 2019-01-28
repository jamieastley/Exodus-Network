package com.jastley.exodusnetwork.checklists.holders;

import android.content.Context;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChecklistTextViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.checklist_text_layout) FrameLayout layoutContainer;
    @BindView(R.id.checklist_item_name) TextView checklistName;
    @BindView(R.id.checklist_item_description) TextView itemDescription;
    private boolean isCompleted;
    private Context mContext;

    public ChecklistTextViewHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        ButterKnife.bind(this, itemView);
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
            checklistName.setPaintFlags(checklistName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    public void setDescription(String description) {
        this.itemDescription.setVisibility(View.VISIBLE);
        this.itemDescription.setText(description);
    }
}
