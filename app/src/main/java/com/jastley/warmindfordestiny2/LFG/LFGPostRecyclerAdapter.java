package com.jastley.warmindfordestiny2.LFG;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.jastley.warmindfordestiny2.R;

/**
 * Created by jamie on 18/3/18.
 */

public class LFGPostRecyclerAdapter extends FirebaseRecyclerAdapter<LFGPost, LFGPostViewHolder> {

    private Context context;
    ProgressBar lfgProgressBar;

    public LFGPostRecyclerAdapter(@NonNull Context context, FirebaseRecyclerOptions<LFGPost> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LFGPostViewHolder holder, int position, @NonNull LFGPost model) {
        holder.setActivityTitle(model.getActivityTitle());
        holder.setActivityCheckpoint(model.getActivityCheckpoint());
        holder.setPlatformIcon(model.getMembershipType(), context);
        holder.setClassType(model.getClassType());
        holder.setDisplayName(model.getDisplayName());
        holder.setLightLevel(model.getLightLevel());
        holder.setMicIcon(model.isHasMic(), context);
        holder.setDateTime(model.getDateTime());
    }

    @Override
    public LFGPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lfg_list_item, parent, false);

        lfgProgressBar = ((Activity) context).findViewById(R.id.lfg_progress_bar);
        lfgProgressBar.setVisibility(View.INVISIBLE);

        return new LFGPostViewHolder(view);
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);

        Toast.makeText(context, "A database error occurred, check your connection.", Toast.LENGTH_SHORT).show();
    }




}
