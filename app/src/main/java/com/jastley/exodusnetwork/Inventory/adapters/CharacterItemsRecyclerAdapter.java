package com.jastley.exodusnetwork.Inventory.adapters;

import androidx.recyclerview.widget.RecyclerView;

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

    private List<InventoryItemModel> itemList = new ArrayList<>();

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
        holder.setQuantity(itemList.get(position).getQuantity());
        holder.setAmmoType(itemList.get(position).getAmmoType());

        holder.setMasterworkIcon(itemList.get(position).isMasterwork());
        holder.setLockStatus(itemList.get(position).getIsLocked());

        holder.setClickedItem(itemList.get(position));
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

    public List<InventoryItemModel> getItemList() {
        return itemList;
    }

    public void clearItemList() {
        itemList.clear();
        notifyDataSetChanged();
    }

}
