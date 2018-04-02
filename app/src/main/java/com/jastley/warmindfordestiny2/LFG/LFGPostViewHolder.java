package com.jastley.warmindfordestiny2.LFG;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.R;

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

private LFGPostViewHolder.ClickListener mClickListener;

    View mView;

    public LFGPostViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mClickListener.OnItemClick(view, getAdapterPosition());
            }
        });
    }

    public interface ClickListener{
        public void OnItemClick(View view, int position);
    }

    public void setOnClickListener(LFGPostViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
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
        String lightlevel = mView.getResources().getString(R.string.lightIcon, light);
        lightLevel.setText(lightlevel);
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

        Long currentTime = System.currentTimeMillis();

        //output as n seconds/mins/hours/days ago
        String postAge = (String) DateUtils.getRelativeTimeSpanString(time, currentTime, 0);

        dateTime.setText(postAge);
    }
}
