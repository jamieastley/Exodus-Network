package com.jastley.exodusnetwork.Dialogs.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformRVHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.platform_row_icon) ImageView platformIcon;
    @BindView(R.id.platform_name_row) TextView platformName;
    @BindView(R.id.hidden_platform_type) TextView platformType;
    protected View mRootView;
    Context context;

    public PlatformRVHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mRootView = itemView;
    }



    public void setPlatformIcon(String icon, Context context) {

        if (icon.equals("2")) {
            platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_psn_classic));
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

    //Hidden field to get platformType out of onPlatformSelection
    public void setPlatformType(String type) {
        platformType.setText(type);
    }

    public ImageView getPlatformIcon() {
        return platformIcon;
    }

    public TextView getPlatformName() {
        return platformName;
    }

    public TextView getPlatformType() {
        return platformType;
    }
}
