package com.jastley.exodusnetwork.lfg.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import org.threeten.bp.Instant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jastl on 7/03/2018.
 */

public class LFGPostViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.platform_Icon) ImageView platformIcon;
    @BindView(R.id.lfg_activity_type) ImageView activityType;
    @BindView(R.id.lfg_activity_title) TextView activityTitle;
    @BindView(R.id.lfg_time) TextView dateTime;
    @BindView(R.id.lfg_edit_time) TextView editTime;
    @BindView(R.id.lfg_player_count_container) LinearLayout playerCountContainer;

    private int playerSlotCount;
    private int availableSlots;

    public String membershipType;


    public LFGPostViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }


    public void setActivityTitle(String title) {
        activityTitle.setText(title);
    }

    public void setPlatformIcon(String platform, Context context) {

        this.membershipType = platform;

        //Fireteam platforms use different platformId's than rest of API because what is consistency ¯\_(ツ)_/¯

        switch (platform) {
            case "1":
                //Use setImageResource runs on UI thread and can cause hiccups, use setImageDrawable
                platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_psn));
                break;
            case "2":
                platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_xbl));
                break;
            case "3":
                platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_blizzard));
                break;
        }
    }

    public void setDateTime(String time, String edited) {

        //Using ThreeTen Backport to utilise Java 8+ time features
        Long post = Instant.parse(time).toEpochMilli();
        Long edit = Instant.parse(edited).toEpochMilli();
        Long now = Instant.now().toEpochMilli();

        CharSequence timeDiff = DateUtils.getRelativeTimeSpanString(post, now, 0);
        String editDiff = "(edited " + DateUtils.getRelativeTimeSpanString(edit, now, 0) + ")";

        this.dateTime.setText(timeDiff);
        this.editTime.setText(editDiff);
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public void setActivityType(int activityType, Context context) {

        switch(activityType) {
            case 0 :
               this.activityType.setImageDrawable(context.getResources().getDrawable(R.drawable.fireteam_anything));
               break;
            case 1:
                this.activityType.setImageDrawable(context.getResources().getDrawable(R.drawable.fireteam_raid));
                break;
            case 2:
                this.activityType.setImageDrawable(context.getResources().getDrawable(R.drawable.fireteam_crucible));
                break;
            case 3:
                this.activityType.setImageDrawable(context.getResources().getDrawable(R.drawable.fireteam_trials));
                break;
            case 4:
                this.activityType.setImageDrawable(context.getResources().getDrawable(R.drawable.fireteam_strike));
                break;
            case 5:
                this.activityType.setImageDrawable(context.getResources().getDrawable(R.drawable.fireteam_anything));
                break;
        }
    }

    public void setPlayerSlotCount(int playerSlotCount) {
        this.playerSlotCount = playerSlotCount;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public void setSlotIcons(int players, int groupSize, Context context) {

        this.playerCountContainer.removeAllViews();

        int totalSlots = players + groupSize;

        for (int i = 1; i <= totalSlots; i++) {

            ImageView iv = new ImageView(context);

            //filled spots
            if(i <= players) {
                iv.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_person_filled));
            }
            //empty spots
            else {
                iv.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_person_empty));
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv.setLayoutParams(lp);

            this.playerCountContainer.addView(iv);
        }
    }
}
