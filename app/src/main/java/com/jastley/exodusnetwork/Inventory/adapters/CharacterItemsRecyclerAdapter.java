package com.jastley.exodusnetwork.Inventory.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.Inventory.holders.CharacterItemsViewHolder;
import com.jastley.exodusnetwork.Inventory.interfaces.ItemSelectionListener;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamie1192 on 24/4/18.
 */

public class CharacterItemsRecyclerAdapter extends RecyclerView.Adapter<CharacterItemsViewHolder>{


    ItemSelectionListener mListener;

    List<InventoryItemModel> itemList = new ArrayList<>();

    public CharacterItemsRecyclerAdapter(ItemSelectionListener listener) {
        this.mListener = listener;
    }

    @Override
    public CharacterItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item_row_layout, parent, false);

        final CharacterItemsViewHolder mCharacterItemViewHolder = new CharacterItemsViewHolder(mView, parent.getContext());

        mCharacterItemViewHolder.itemView.setOnClickListener(view -> {
            mListener.onItemClick(view, mCharacterItemViewHolder.getAdapterPosition(), mCharacterItemViewHolder);
        });

        return mCharacterItemViewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterItemsViewHolder holder, int position) {

        holder.setIsEquipped(itemList.get(position).getIsEquipped());
        holder.setCanEquip(itemList.get(position).getCanEquip());
        holder.setItemName(itemList.get(position).getItemName());
        holder.setItemHash(itemList.get(position).getItemHash());
        holder.setItemImage(itemList.get(position).getItemIcon());
        holder.setPrimaryStatValue(itemList.get(position).getPrimaryStatValue());
        holder.setItemInstanceId(itemList.get(position).getItemInstanceId());
        holder.setBucketHash(itemList.get(position).getBucketHash());
        holder.setItemTypeDisplayName(itemList.get(position).getItemTypeDisplayName());
        holder.setModifierIcon(itemList.get(position).getDamageType());

//        holder.setItemName(itemList.get(position).getDisplayProperties().getName());
//        holder.setItemHash(itemList.get(position).getItemData().getItemHash());
//        holder.setItemImage(itemList.get(position).getDisplayProperties().getIcon());
//
//        try {
//            holder.setPrimaryStatValue(itemList.get(position).getInstanceData().getPrimaryStat().getValue());
//            holder.setModifierIcon(itemList.get(position).getInstanceData().getDamageType());
//        }
//        catch(Exception e) {
//            Log.e("INVENTORY_VIEWHOLDER", e.getLocalizedMessage());
//        }
//
//        try {
//            holder.setItemInstanceId(itemList.get(position).getItemData().getItemInstanceId());
//            holder.setIsEquipped(itemList.get(position).getInstanceData().getIsEquipped());
//            holder.setCanEquip(itemList.get(position).getInstanceData().getCanEquip());
//        }
//        catch(Exception e){
//            Log.e("INVENTORY_VIEWHOLDER", e.getLocalizedMessage());
//        }
//        holder.setBucketHash(itemList.get(position).getItemData().getBucketHash());
//        holder.setItemTypeDisplayName(itemList.get(position).getInventoryItem().getItemTypeDisplayName());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItemList(List<InventoryItemModel> list) {
        itemList = list;
        notifyDataSetChanged();
    }

    public void clearItemList() {
        itemList.clear();
        notifyDataSetChanged();
    }

}
