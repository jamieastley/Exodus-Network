package com.jastley.exodusnetwork.overview.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.Definitions.getGender;
import static com.jastley.exodusnetwork.Definitions.getRace;
import static com.jastley.exodusnetwork.Definitions.hunter;
import static com.jastley.exodusnetwork.Definitions.titan;
import static com.jastley.exodusnetwork.Definitions.warlock;
import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

public class EmblemViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @BindView(R.id.emblem_background) ImageView emblemBackground;
    @BindView(R.id.emblem_class_name) TextView className;
    @BindView(R.id.emblem_race_name) TextView raceName;
    @BindView(R.id.emblem_light) TextView lightLevel;
    @BindView(R.id.emblem_character_level) TextView characterLevel;

    public EmblemViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setEmblemBackground(String url) {
        Picasso.get()
                .load(baseURL + url)
                .placeholder(context.getResources().getDrawable(R.drawable.missing_banner_placeholder))
                .into(this.emblemBackground);
    }

    public void setClassName(String name) {

        switch(name) {
            case titan:
                this.className.setText(context.getString(R.string.titan));
                break;
            case hunter:
                this.className.setText(context.getString(R.string.hunter));
                break;
            case warlock:
                this.className.setText(context.getString(R.string.warlock));
                break;

            default:
                this.className.setText(context.getString(R.string.unknown));
                break;
        }
    }

    public void setRaceGenderName(int race, int gender) {
        String text = getRace(race) + " " + getGender(gender);
        this.raceName.setText(text);
    }

    public void setLightLevel(String light) {
        this.lightLevel.setText(light);
    }

    public void setCharacterLevel(String level) {
        String levelText = "Level " + level;
        this.characterLevel.setText(levelText);
    }
}
