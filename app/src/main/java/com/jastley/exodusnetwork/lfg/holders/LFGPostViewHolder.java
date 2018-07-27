package com.jastley.exodusnetwork.lfg.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jastl on 7/03/2018.
 */

public class LFGPostViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.platform_Icon) ImageView platformIcon;
    @BindView(R.id.lfg_activity_title) TextView activityTitle;
    @BindView(R.id.lfg_activity_checkpoint) TextView activityCheckpoint;
    @BindView(R.id.lfg_time) TextView dateTime;
    @BindView(R.id.light_Level) TextView lightLevel;
    @BindView(R.id.class_Type) TextView classType;
    @BindView(R.id.micIcon) ImageView micIcon;
    @BindView(R.id.player_Username) TextView displayName;


    public String emblemIcon;
    public String emblemBackground;
    public String membershipId;
    public String characterId;
    public String membershipType;
    public String description;
    public boolean hasMic;


    View mView;

    public LFGPostViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;

        ButterKnife.bind(this, itemView);

    }


    public void setActivityTitle(String title) {
        activityTitle.setText(title);
    }

    public void setActivityCheckpoint(String checkpoint) {
        activityCheckpoint.setText(checkpoint);
    }

    public void setPlatformIcon(String icon, Context context) {

        this.membershipType = icon;

//        TODO: alpha-out white backgrounds from platform icons
        if (icon.equals("2")) {
            //Use setImageResource runs on UI thread and can cause hiccups, use setImageDrawable
            platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_psn));
        }
        else if (icon.equals("1")) {
            platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_xbl));
        }
        else if (icon.equals("4")) {
            platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_blizzard));
        }
    }

    public void setLightLevel(String light) {
        String lightlevel = mView.getResources().getString(R.string.lightIcon, light);
        lightLevel.setText(lightlevel);
    }

    public void setDisplayName(String name) {

        //Battle.Net tags
        if(name.contains("%23")){
            name = name.replace("%23", "#");
        }
        displayName.setText(name);
    }

    public void setClassType(String type) {

        if (type.equals("0")) {
            classType.setText("Titan");
        }
        else if (type.equals("1")) {
            classType.setText("Hunter");
        }
        else if (type.equals("2")) {
            classType.setText("Warlock");
        }
        else {
            classType.setText("Unknown");
        }
    }

    public void setMicIcon(boolean hasMic, Context context) {

        if (hasMic) {
            this.hasMic = true;
            micIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_mic_on_white));
        }
        else {
            this.hasMic = false;
            micIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_mic_off_white));
        }
    }

    public void setDateTime(Long time) {

        Long currentTime = System.currentTimeMillis();

        //output as n seconds/mins/hours/days ago
        String postAge = (String) DateUtils.getRelativeTimeSpanString(time, currentTime, 0);

        dateTime.setText(postAge);
    }

    public ImageView getPlatformIcon() {
        return platformIcon;
    }

    public TextView getActivityTitle() {
        return activityTitle;
    }

    public TextView getActivityCheckpoint() {
        return activityCheckpoint;
    }

    public TextView getDateTime() {
        return dateTime;
    }

    public TextView getLightLevel() {
        return lightLevel;
    }

    public TextView getClassType() {
        return classType;
    }

    public ImageView getMicIcon() {
        return micIcon;
    }

    public TextView getDisplayName() {
        return displayName;
    }

    public String getEmblemIcon() {
        return emblemIcon;
    }

    public void setEmblemIcon(String emblemIcon) {
        this.emblemIcon = emblemIcon;
    }

    public String getEmblemBackground() {
        return emblemBackground;
    }

    public void setEmblemBackground(String emblemBackground) {
        this.emblemBackground = emblemBackground;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public boolean getHasMic() {
        return hasMic;
    }

    public void setHasMic(boolean hasMic) {
        this.hasMic = hasMic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public View getmView() {
        return mView;
    }
}
