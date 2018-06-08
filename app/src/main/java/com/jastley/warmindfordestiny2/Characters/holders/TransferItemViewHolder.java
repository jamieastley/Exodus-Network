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
    @BindView(R.id.transfer_light_level) TextView mLightLevel;
    @BindView(R.id.milestone_description) TextView mBaseLevel;
    @BindView(R.id.transfer_class_type) TextView mClassType;

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

    public String getClassType() {
        return mClassType.getText().toString();
    }

    public void setClassType(String type) {

        this.classTypeValue = type;

        if (type.equals("0")) {
            String titan = mView.getResources().getString(R.string.titan);
            mClassType.setText(titan);
        }
        else if (type.equals("1")) {
            String hunter = mView.getResources().getString(R.string.hunter);
            mClassType.setText(hunter);
        }
        else if (type.equals("2")) {
            String warlock = mView.getResources().getString(R.string.warlock);
            mClassType.setText(warlock);
        }
        else if (type.equals("vault")) {
            mClassType.setText("Vault");
        }
        else {
            String unknown = mView.getResources().getString(R.string.unknown);
            mClassType.setText(unknown);
        }
    }

    public ImageView getEmblemBackground() {
        return emblemBackground;
    }

    public void setEmblemBackground(String url, Context context) {

        this.imageURL = url;

        Picasso.get()
                .load(baseURL + url)
                .into(this.emblemBackground);

    }

//    public TextView getLightLevel() {
//        return lightLevel;
//    }

    public void setLightLevel(String light) {
        String lightLevel = mView.getResources().getString(R.string.lightIcon, light);
        this.mLightLevel.setText(lightLevel);
    }

    public String getCharacterId() {
        return characterId;
    }


    public String getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(String characterLevel) {
        this.characterLevel = characterLevel;
    }

    public TextView getmBaseLevel() {
        return mBaseLevel;
    }

    public void setmBaseLevel(String level) {
        String baseLevel = mView.getResources().getString(R.string.level, level);
        this.mBaseLevel.setText(baseLevel);
    }

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
}
