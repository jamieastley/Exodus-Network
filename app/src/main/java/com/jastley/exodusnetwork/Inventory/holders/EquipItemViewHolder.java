package com.jastley.exodusnetwork.Inventory.holders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.exodusnetwork.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

public class EquipItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.equip_emblem_icon) ImageView emblemIcon;
    @BindView(R.id.equip_class_name) TextView className;

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
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(context.getResources(), bitmap);
                        roundedBitmapDrawable.setCircular(true);
                        emblemIcon.setImageDrawable(roundedBitmapDrawable);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
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

    public void setClassType(String type, Context context) {

        switch (type) {
            case "0":
                className.setText(context.getString(R.string.titan));
                break;
            case "1":
                className.setText(context.getString(R.string.hunter));
                break;
            case "2":
                className.setText(context.getString(R.string.warlock));
                break;
            default:
                className.setText(context.getString(R.string.unknown));
                break;
        }

    }
    public void setDisabled() {
        mView.setEnabled(false);
        mView.setAlpha(0.3f);
    }
}
