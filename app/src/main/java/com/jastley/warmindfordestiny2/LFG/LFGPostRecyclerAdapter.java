package com.jastley.warmindfordestiny2.LFG;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.R;

/**
 * Created by jamie1192 on 18/3/18.
 */

public class LFGPostRecyclerAdapter extends FirebaseRecyclerAdapter<LFGPost, LFGPostViewHolder> {

    private Context context;
    private ProgressBar lfgProgressBar;

    RecyclerViewClickListener listener;

    public LFGPostRecyclerAdapter(@NonNull Context context, FirebaseRecyclerOptions<LFGPost> options, RecyclerViewClickListener listener) {
        super(options);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public LFGPostViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lfg_list_item, parent, false);

        lfgProgressBar = ((Activity) context).findViewById(R.id.lfg_progress_bar);
        lfgProgressBar.setVisibility(View.INVISIBLE);

        final LFGPostViewHolder holder = new LFGPostViewHolder(mView);

        holder.itemView.setOnClickListener(view ->
                listener.onClick(view, holder.getAdapterPosition(), holder));

        return holder;
    }

    @Override
    protected void onBindViewHolder(@NonNull final LFGPostViewHolder holder, final int position, @NonNull final LFGPost model) {
        holder.setActivityTitle(model.getActivityTitle());
        holder.setActivityCheckpoint(model.getActivityCheckpoint());
        holder.setPlatformIcon(model.getMembershipType(), context);
        holder.setClassType(model.getClassType());
        holder.setDisplayName(model.getDisplayName());
        holder.setLightLevel(model.getLightLevel());
        holder.setMicIcon(model.getHasMic(), context);
        holder.setDateTime(model.getDateTime());

        //won't be displayed, for retrieval when clicked
        holder.setEmblemIcon(model.getEmblemIcon());
        holder.setEmblemBackground(model.getEmblemBackground());
        holder.setCharacterId(model.getCharacterId());
        holder.setMembershipId(model.getMembershipId());
        holder.setDescription(model.getDescription());
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);

        Toast.makeText(context, "A database error occurred, check your connection.", Toast.LENGTH_SHORT).show();
    }




}
