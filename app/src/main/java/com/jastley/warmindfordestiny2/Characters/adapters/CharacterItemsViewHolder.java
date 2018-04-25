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

    public void setItemImage(String imageUrl, Context context) {
//        this.itemImage = itemImage;
        Picasso.with(context)
            .load(baseURL+imageUrl)
            .into(this.itemImage);
    }
}
