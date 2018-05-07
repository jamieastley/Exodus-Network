package com.jastley.warmindfordestiny2.Characters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.R;
import com.squareup.picasso.Picasso;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class TransferItemViewHolder extends RecyclerView.ViewHolder {

//    @BindView(R.id.transfer_class_name) TextView classType;
    @BindView(R.id.transfer_emblem_background) ImageView emblemBackground;
//    @BindView(R.id.transfer_light_level) TextView lightLevel;
    private String characterId;
    private String characterLevel;
    private String imageURL;

    public TransferItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

//    public TextView getClassType() {
//        return classType;
//    }

    public void setClassType(String classType) {
//        classType.setText(classType);
    }

    public ImageView getEmblemBackground() {
        return emblemBackground;
    }

    public void setEmblemBackground(String url, Context context) {

        this.imageURL = url;

        Picasso.with(context)
                .load(baseURL+ url)
                .into(this.emblemBackground);
    }

//    public TextView getLightLevel() {
//        return lightLevel;
//    }
//
//    public void setLightLevel(TextView lightLevel) {
//        this.lightLevel = lightLevel;
//    }

    public String getCharacterId() {
        return characterId;
    }


    public String getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(String characterLevel) {
        this.characterLevel = characterLevel;
    }
}
