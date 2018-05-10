package com.jastley.warmindfordestiny2.Characters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.Definitions;
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
    @BindView(R.id.item_modifier_icon) ImageView modifierIcon;

    //Hidden values to retain for retrieval later
    private String itemHash;
    private String imageUrl;
    private String primaryStatValue;
    private String bucketHash;
    private String itemInstanceId;
    private boolean isEquipped;
    private boolean canEquip;
    private String itemTypeDisplayName;
    private int tabIndex;
    private String damageType;

    protected View mRootView;

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

    public String getPrimaryStatValue() {
        return primaryStatValue;
    }

    public void setPrimaryStatValue(String value) {

        this.primaryStatValue = value;
        statValue.setText(value);
    }

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {
        this.itemHash = itemHash;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getBucketHash() {
        return bucketHash;
    }

    public void setBucketHash(String bucketHash) {
        this.bucketHash = bucketHash;
    }

    public String getItemInstanceId() {
        return itemInstanceId;
    }

    public void setItemInstanceId(String itemInstanceId) {
        this.itemInstanceId = itemInstanceId;
    }

    public boolean getIsEquipped() {
        return isEquipped;
    }

    public void setIsEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public boolean getCanEquip() {
        return canEquip;
    }

    public void setCanEquip(boolean canEquip) {
        this.canEquip = canEquip;
    }

    public String getItemTypeDisplayName() {
        return itemTypeDisplayName;
    }

    public void setItemTypeDisplayName(String itemTypeDisplayName) {
        this.itemTypeDisplayName = itemTypeDisplayName;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public ImageView getModifierIcon() {
        return modifierIcon;
    }

    public void setModifierIcon(String type, Context context) {

        //set damageType incase we need it later
        if(type != null){

            this.damageType = type;

            switch(type){

                //set damageType icon
                case(Definitions.dmgTypeKinetic):
                    //TODO - maybe
                    break;

                case(Definitions.dmgTypeArc):
                    this.modifierIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.modifier_arc));
                    break;

                case(Definitions.dmgTypeThermal):
                    this.modifierIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.modifier_solar));
                    break;

                case(Definitions.dmgTypeVoid):
                    this.modifierIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.modifier_void));
                    break;

                case(Definitions.dmgTypeRaid):
                    //TODO - maybe
                    break;

            }
        }
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }
}
