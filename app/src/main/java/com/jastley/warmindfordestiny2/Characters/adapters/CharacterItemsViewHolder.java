package com.jastley.warmindfordestiny2.Characters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

/**
 * Created by jamie1192 on 24/4/18.
 */

public class CharacterItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.inventory_item_name) TextView itemName;
    @BindView(R.id.inventory_item_type) TextView itemType;
    @BindView(R.id.inventory_item_image) ImageView itemImage;
    @BindView(R.id.primary_stat_value) TextView statValue;

    //Hidden values to retain for retrieval later
    private String itemHash;
    private String imageUrl;
    private String primaryStatValue;

    protected View mRootView;
    Context context;

    public CharacterItemsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mRootView = itemView;
    }

    public TextView getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.setText(itemName);
    }

    public TextView getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType.setText(itemType);
    }

    public ImageView getItemImage() {
        return itemImage;
    }

    public void setItemImage(String url, Context context) {
//        this.itemImage = itemImage;
        this.imageUrl = url;
        Picasso.with(context)
            .load(baseURL+url)
            .placeholder(R.drawable.missing_icon_d2)
            .into(this.itemImage);
    }

    public TextView getStatValue() {
        return statValue;
    }

    public void setStatValue(String value) {
//        if(value != null){
            statValue.setText(value);
//        }
//        else {
//            statValue.setVisibility(View.GONE);
//        }
    }
}
