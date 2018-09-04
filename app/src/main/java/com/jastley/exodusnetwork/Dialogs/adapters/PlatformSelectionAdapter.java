package com.jastley.exodusnetwork.Dialogs.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jastley.exodusnetwork.Interfaces.PlatformSelectionListener;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Dialogs.holders.PlatformRVHolder;

import java.util.ArrayList;


/**
 * Created by jamie1192 on 23/03/2018.
 */

public class PlatformSelectionAdapter extends RecyclerView.Adapter<PlatformRVHolder> implements DialogInterface{

    Context context;
    ArrayList<String> platforms;

    PlatformSelectionListener listener;

    public PlatformSelectionAdapter(ArrayList<String> platforms, PlatformSelectionListener listener) {
        this.platforms = platforms;
        this.listener = listener;
    }

    @Override
    public PlatformRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.platform_row_layout, parent, false);

        final PlatformRVHolder mPlatformRVHolder = new PlatformRVHolder(mView);

        this.context = parent.getContext();

        mPlatformRVHolder.itemView.setOnClickListener(view ->
                listener.onPlatformSelection(view, mPlatformRVHolder.getAdapterPosition(), mPlatformRVHolder));

        return mPlatformRVHolder;
    }

    @Override
    public void onBindViewHolder(final PlatformRVHolder holder, int position) {

        //Xbox
        if(platforms.get(position).equals("1")){
            holder.setPlatformIcon(platforms.get(position), context);
            holder.setPlatformName("Xbox");
            holder.setPlatformType("1");
        }
        //PlayStation
        else if(platforms.get(position).equals("2")){
            holder.setPlatformIcon(platforms.get(position), context);
            holder.setPlatformName("PlayStation");
            holder.setPlatformType("2");
        }
        //PC/Battle.Net
        else if(platforms.get(position).equals("4")){
            holder.setPlatformIcon(platforms.get(position), context);
            holder.setPlatformName("Battle.Net");
            holder.setPlatformType("4");
        }

    }

    @Override
    public int getItemCount() {
        return platforms.size();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {
        Toast.makeText(context, "dismiss called", Toast.LENGTH_SHORT).show();
    }
}
