package com.jastley.warmindfordestiny2.LFG;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.R;

/**
 * Created by jastl on 7/03/2018.
 */

public class LFGPostViewHolder extends RecyclerView.ViewHolder{

    private final ImageView platformIcon;
    private final TextView activityTitle;
    private final TextView activityCheckpoint;
    private final TextView dateTime;
    private final TextView lightLevel;
    private final TextView classType;
    private final ImageView micIcon;
    private final TextView displayName;



    public LFGPostViewHolder(View itemView) {
        super(itemView);
        platformIcon = itemView.findViewById(R.id.platform_Icon);
        activityTitle = itemView.findViewById(R.id.lfg_activity_title);
        activityCheckpoint = itemView.findViewById(R.id.lfg_activity_checkpoint);
        dateTime = itemView.findViewById(R.id.lfg_time);
        lightLevel = itemView.findViewById(R.id.light_Level);
        classType = itemView.findViewById(R.id.class_Type);
        micIcon = itemView.findViewById(R.id.micIcon);
        displayName = itemView.findViewById(R.id.player_Username);

    }

    public void setActivityTitle(String title) {
        activityTitle.setText(title);
    }

    public void setActivityCheckpoint(String checkpoint) {
        activityCheckpoint.setText(checkpoint);
    }

//    public int getIconResource(Context context, String membershipType) {
//        int resId = context.getResources().getI
//    }

    public void setPlatformIcon(String icon, Context context) {

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
        lightLevel.setText(light);
    }

    public void setDisplayName(String name) {
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
            micIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_mic_on_24dp));
        }
        else {
            micIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_mic_off_24dp));
        }
    }

    public void setDateTime(Long time) {
        dateTime.setText(time.toString());
    }
}