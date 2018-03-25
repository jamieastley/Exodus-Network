package com.jastley.warmindfordestiny2.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jastley.warmindfordestiny2.R;


/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformSelectionAdapter extends RecyclerView.Adapter<PlatformRVHolder>{

    Context context;
    String[] platforms;

    public PlatformSelectionAdapter(Context context, String[] platforms) {
        this.context = context;
        this.platforms = platforms;
    }

    @Override
    public PlatformRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platform_row_layout, parent, false);

        return new PlatformRVHolder(view);
    }

    @Override
    public void onBindViewHolder(PlatformRVHolder holder, int position) {

        //Xbox
        if(platforms[position].equals("1")){
            holder.platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_xbl));
            holder.platformName.setText(R.string.xbox);
        }
        //PlayStation
        else if(platforms[position].equals("2")){
            holder.platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_psn));
            holder.platformName.setText(R.string.psn);
        }
        //PC/Battle.Net
        else {
            holder.platformIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_blizzard));
            holder.platformName.setText(R.string.battlenet);
        }

    }

    @Override
    public int getItemCount() {
        return platforms.length;
    }
}
