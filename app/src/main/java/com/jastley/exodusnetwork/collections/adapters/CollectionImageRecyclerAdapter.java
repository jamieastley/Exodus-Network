package com.jastley.exodusnetwork.collections.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.collections.holders.CollectionImageViewHolder;
import com.jastley.exodusnetwork.database.models.DestinyCollectibleDefinition;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CollectionImageRecyclerAdapter extends RecyclerView.Adapter<CollectionImageViewHolder> {

    private List<DestinyCollectibleDefinition> checklistData = new ArrayList<>();
    public final PublishSubject<String> onClickSubject = PublishSubject.create();


    @NonNull
    @Override
    public CollectionImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_image_item, parent, false);

        final CollectionImageViewHolder collectionImageViewHolder = new CollectionImageViewHolder(mView, parent.getContext());

        collectionImageViewHolder.itemView.setOnClickListener(view -> {
            onClickSubject.onNext(collectionImageViewHolder.getCollectibleItemHash());
        });

        //DON'T return new viewHolder, return the finalised one above!
        return collectionImageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionImageViewHolder holder, int position) {
        holder.setItemImage(checklistData.get(position).getValue().getDisplayProperties().getIcon());
        holder.setCompleted(checklistData.get(position).getValue().isAcquired());
        holder.setCollectibleItemHash(checklistData.get(position).getValue().getItemHash());
    }


    @Override
    public int getItemCount() {
        return checklistData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setChecklistData(List<?> list) {
        List<DestinyCollectibleDefinition> collectibleList = (List<DestinyCollectibleDefinition>) list;
        this.checklistData = collectibleList;
        notifyDataSetChanged();
    }

    public Observable<String> getOnClickSubject() {
        return onClickSubject;
    }
}
