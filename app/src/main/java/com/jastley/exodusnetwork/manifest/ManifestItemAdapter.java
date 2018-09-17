package com.jastley.exodusnetwork.manifest;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;


import io.reactivex.subjects.PublishSubject;

public class ManifestItemAdapter extends PagedListAdapter<DestinyInventoryItemDefinition, ManifestItemViewHolder> {


    protected ManifestItemAdapter() {
        super(DIFF_CALLBACK);
    }

    private PublishSubject<String> onClickSubject = PublishSubject.create();

    @NonNull
    @Override
    public ManifestItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.manifest_item_row, parent, false);

        final ManifestItemViewHolder manifestItemViewHolder = new ManifestItemViewHolder(mView);

        manifestItemViewHolder.itemView.setOnClickListener(view -> onClickSubject.onNext(manifestItemViewHolder.getItemHash()));

        return new ManifestItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManifestItemViewHolder holder, int position) {
        DestinyInventoryItemDefinition item = getItem(position);
        if(item != null) {
            holder.setId(item.getValue().getDisplayProperties().getName());
            holder.setItemType(item.getValue().getItemTypeDisplayName());
            holder.setIcon(item.getValue().getDisplayProperties().getIcon());
            holder.setItemHash(item.getValue().getHash());
//            holder.setValue(item.getValue());

        } else {
            //load blank row
            holder.clear();
        }
    }

    private static DiffUtil.ItemCallback<DestinyInventoryItemDefinition> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DestinyInventoryItemDefinition>() {
                @Override
                public boolean areItemsTheSame(DestinyInventoryItemDefinition oldItem, DestinyInventoryItemDefinition newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(DestinyInventoryItemDefinition oldItem, DestinyInventoryItemDefinition newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public PublishSubject<String> getOnClickSubject() {
        return onClickSubject;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
