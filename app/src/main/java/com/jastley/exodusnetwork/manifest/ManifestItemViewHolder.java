package com.jastley.exodusnetwork.manifest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

public class ManifestItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.manifest_item_name) TextView id;
    @BindView(R.id.manifest_item_details) TextView itemType;
    @BindView(R.id.manifest_item_icon) ImageView icon;
    @BindView(R.id.manifest_item_hash) TextView itemHash;
//    @BindView(R.id.manifest_progress_bar) ProgressBar progressBar;

    public ManifestItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public String getId() {
        return id.getText().toString();
    }

    public void setId(String text) {
        this.id.setText(text);
    }

    public String getItemType() {
        return itemType.getText().toString();
    }

    public void setItemType(String text) {
        this.itemType.setText(text);
    }

    public void setIcon(String url) {
        Picasso.get()
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(this.icon);
    }

    public void clear() {
        this.id.setText("Loading");
        this.itemType.setText("Please wait...");
//        this.progressBar.setVisibility(View.VISIBLE);
    }

    public String getItemHash() {
        return itemHash.getText().toString();
    }

    public void setItemHash(String hash) {
        this.itemHash.setText(hash);
    }
}
