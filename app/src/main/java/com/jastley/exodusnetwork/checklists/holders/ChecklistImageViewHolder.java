package com.jastley.exodusnetwork.checklists.holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jastley.exodusnetwork.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

public class ChecklistImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.checklist_image_layout) FrameLayout layoutContainer;
    @BindView(R.id.checklist_image) ImageView itemImage;
    private boolean isCompleted;
    private Context mContext;

    public ChecklistImageViewHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void setItemImage(String link) {
        Picasso.get()
                .load(baseURL + link)
                .placeholder(R.drawable.missing_icon_d2)
                .into(itemImage);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
        if(isCompleted) {
            layoutContainer.setForeground(ContextCompat.getDrawable(mContext, R.drawable.checklist_completion));
        }
    }
}
