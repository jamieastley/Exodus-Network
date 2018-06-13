package com.jastley.warmindfordestiny2.Inventory.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.R;
import com.squareup.picasso.Picasso;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class EquipItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.equip_emblem_icon) ImageView emblemIcon;

    private String characterId;
    private String characterLevel;
    private String imageURL;
    private String classType;
    private View mView;

    public EquipItemViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public ImageView getEmblemIcon() {
        return emblemIcon;
    }

    public void setEmblemIcon(String iconUrl, Context context) {

        this.imageURL = iconUrl;

        Picasso.get()
                .load(baseURL + iconUrl)
                .into(this.emblemIcon);
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(String characterLevel) {
        this.characterLevel = characterLevel;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String type) {

        switch (type) {
            case "0":
                classType = "Titan";
                break;
            case "1":
                classType = "Hunter";
                break;
            case "2":
                classType = "Warlock";
                break;
            default:
                classType = "Unknown";
                break;
        }

    }
    public void setDisabled() {
        mView.setEnabled(false);
        mView.setAlpha(0.3f);
    }
}
