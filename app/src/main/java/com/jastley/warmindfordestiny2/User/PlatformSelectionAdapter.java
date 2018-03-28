package com.jastley.warmindfordestiny2.User;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jastley.warmindfordestiny2.R;


/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformSelectionAdapter extends RecyclerView.Adapter<PlatformRVHolder> implements DialogInterface{

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
    public void onBindViewHolder(final PlatformRVHolder holder, int position) {

        //Xbox
        if(platforms[position].equals("1")){
            holder.setPlatformIcon(platforms[position], context);
            holder.setPlatformName("Xbox");
            holder.setPlatformType("1");
        }
        //PlayStation
        else if(platforms[position].equals("2")){
            holder.setPlatformIcon(platforms[position], context);
            holder.setPlatformName("PlayStation");
            holder.setPlatformType("2");
        }
        //PC/Battle.Net
        else {
            holder.setPlatformIcon(platforms[position], context);
            holder.setPlatformName("Battle.Net");
            holder.setPlatformType("4");
        }

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, holder.platformType.getText() + " selected", Toast.LENGTH_SHORT).show();
//                dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return platforms.length;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {
        Toast.makeText(context, "dismiss called", Toast.LENGTH_SHORT).show();
    }
}
