package com.jastley.warmindfordestiny2.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformRVHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.platform_row_icon) ImageView platformIcon;
    @BindView(R.id.platform_name_row) TextView platformName;

    public PlatformRVHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPlatformIcon(String icon, Context context) {

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

    public void setPlatformName(String platform) {
        platformName.setText(platform);
    }
}
