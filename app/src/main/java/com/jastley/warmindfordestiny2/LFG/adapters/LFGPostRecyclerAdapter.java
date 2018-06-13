package com.jastley.warmindfordestiny2.LFG.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.jastley.warmindfordestiny2.LFG.holders.LFGPostViewHolder;
import com.jastley.warmindfordestiny2.LFG.RecyclerViewClickListener;
import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.R;

import java.util.List;

/**
 * Created by jamie1192 on 18/3/18.
 */

public class LFGPostRecyclerAdapter extends RecyclerView.Adapter<LFGPostViewHolder> {

    private Context context;
    private int lastPosition = -1;
    List<LFGPost> posts;

    RecyclerViewClickListener listener;

    public LFGPostRecyclerAdapter(Context context, List<LFGPost> lfgList, RecyclerViewClickListener listener) {
        this.context = context;
        this.posts = lfgList;
        this.listener = listener;
    }

    @Override
    public LFGPostViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lfg_list_item, parent, false);

        final LFGPostViewHolder holder = new LFGPostViewHolder(mView);

        holder.itemView.setOnClickListener(view ->
                listener.onClick(view, holder.getAdapterPosition(), holder));

        return holder;
    }

    @Override
    public void onBindViewHolder(LFGPostViewHolder holder, int position) {
        holder.setActivityTitle(posts.get(position).getActivityTitle());
        holder.setActivityCheckpoint(posts.get(position).getActivityCheckpoint());
        holder.setPlatformIcon(posts.get(position).getMembershipType(), context);
        holder.setClassType(posts.get(position).getClassType());
        holder.setDisplayName(posts.get(position).getDisplayName());
        holder.setLightLevel(posts.get(position).getLightLevel());
        holder.setMicIcon(posts.get(position).getHasMic(), context);
        holder.setDateTime(posts.get(position).getDateTime());

        //won't be displayed, for retrieval when clicked
        holder.setEmblemIcon(posts.get(position).getEmblemIcon());
        holder.setEmblemBackground(posts.get(position).getEmblemBackground());
        holder.setCharacterId(posts.get(position).getCharacterId());
        holder.setMembershipId(posts.get(position).getMembershipId());
        holder.setDescription(posts.get(position).getDescription());

        //set animation
//        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition)
//        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    @NonNull
//    @Override
//    public LFGPost getItem(int position) {
//        return super.getItem(position);
//    }

    public void clearItems() {
        notifyDataSetChanged();
    }

}
