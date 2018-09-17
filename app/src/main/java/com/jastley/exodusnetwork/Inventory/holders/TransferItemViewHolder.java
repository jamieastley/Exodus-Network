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

public class TransferItemViewHolder extends RecyclerView.ViewHolder {

//    @BindView(R.id.transfer_class_name) TextView classType;
//    @BindView(R.id.transfer_emblem_background) ImageView emblemBackground;
//    @BindView(R.id.transfer_light_level) TextView mLightLevel;
//    @BindView(R.id.transfer_class_level) TextView mBaseLevel;
//    @BindView(R.id.transfer_class_type) TextView mClassType;

    @BindView(R.id.transfer_emblem_icon) ImageView emblemIcon;
    @BindView(R.id.transfer_class_name) TextView className;

    private String characterId;
    private String characterLevel;
    private String imageURL;
    private String classTypeValue;

    //for vault transfer
    private String vaultCharacterId;

    private View mView;

    public TransferItemViewHolder(View itemView) {

        super(itemView);
        this.mView = itemView;

        ButterKnife.bind(this, itemView);
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

//    public TextView getClassType() {
//        return classType;
//    }

    public String getClassTypeValue() {
        return this.classTypeValue;
    }

//    public String getClassType() {
//        return mClassType.getText().toString();
//    }

    public void setClassType(String type) {

        this.classTypeValue = type;

        if (type.equals("0")) {
            String titan = mView.getResources().getString(R.string.titan);
            className.setText(titan);
        }
        else if (type.equals("1")) {
            String hunter = mView.getResources().getString(R.string.hunter);
            className.setText(hunter);
        }
        else if (type.equals("2")) {
            String warlock = mView.getResources().getString(R.string.warlock);
            className.setText(warlock);
        }
        else if (type.equals("vault")) {
            className.setText("Vault");
        }
        else {
            String unknown = mView.getResources().getString(R.string.unknown);
            className.setText(unknown);
        }
    }

    public ImageView getEmblemIcon() {
        return emblemIcon;
    }

    public void setEmblemIcon(String url, Context context) {

        this.imageURL = url;

        Picasso.get()
                .load(baseURL + url)
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

//    public void setLightLevel(String light) {
//        String lightLevel = mView.getResources().getString(R.string.lightIcon, light);
//        this.mLightLevel.setText(lightLevel);
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

//    public TextView getmBaseLevel() {
//        return mBaseLevel;
//    }

//    public void setmBaseLevel(String level) {
//        String baseLevel = mView.getResources().getString(R.string.level, level);
//        this.mBaseLevel.setText(baseLevel);
//    }

    public void setDisabled() {
        mView.setEnabled(false);
        mView.setAlpha(0.3f);
    }

    public String getVaultCharacterId() {
        return vaultCharacterId;
    }

    public void setVaultCharacterId(String vaultCharacterId) {
        this.vaultCharacterId = vaultCharacterId;
    }

    public void setClassName(String text) {
        //TODO classType switch
        this.className.setText(text);
    }
}
