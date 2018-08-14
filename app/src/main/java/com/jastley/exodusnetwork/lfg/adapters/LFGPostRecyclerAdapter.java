package com.jastley.exodusnetwork.lfg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.api.models.Response_GetPublicFireteams;
import com.jastley.exodusnetwork.lfg.holders.LFGPostViewHolder;
import com.jastley.exodusnetwork.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jamie1192 on 18/3/18.
 */

public class LFGPostRecyclerAdapter extends RecyclerView.Adapter<LFGPostViewHolder> {

    private List<Response_GetPublicFireteams.Response.Results> posts = new ArrayList<>();
    private Context mContext;

    public PublishSubject<Response_GetPublicFireteams.Response.Results> onClickSubject = PublishSubject.create();

    public LFGPostRecyclerAdapter() {

    }

    @Override
    public LFGPostViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        this.mContext = parent.getContext();

        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lfg_list_item, parent, false);

        return new LFGPostViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(LFGPostViewHolder holder, int position) {
        holder.setActivityTitle(posts.get(position).getTitle());
        holder.setActivityType(posts.get(position).getActivityType(), mContext);

        holder.setPlayerSlotCount(posts.get(position).getPlayerSlotCount());
        holder.setAvailableSlots(posts.get(position).getAvailablePlayerSlotCount());
        holder.setSlotIcons(posts.get(position).getPlayerSlotCount(),
                            posts.get(position).getAvailablePlayerSlotCount(),
                            mContext);
        holder.setPlatformIcon(String.valueOf(posts.get(position).getPlatform()), mContext);
        holder.setDateTime(posts.get(position).getDateCreated(), posts.get(position).getDatePlayerModified());

        final Response_GetPublicFireteams.Response.Results lfgPost = posts.get(position);

        holder.itemView.setOnClickListener(view -> onClickSubject.onNext(lfgPost));
    }

    public Observable<Response_GetPublicFireteams.Response.Results> getClickedItem() {
        return onClickSubject;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItems(List<Response_GetPublicFireteams.Response.Results> fireteamPosts) {
        this.posts = fireteamPosts;
        notifyDataSetChanged();
    }

}
