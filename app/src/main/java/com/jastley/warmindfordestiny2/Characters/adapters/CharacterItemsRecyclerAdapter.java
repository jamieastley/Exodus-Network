package com.jastley.warmindfordestiny2.Characters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.warmindfordestiny2.Characters.interfaces.ItemSelectionListener;
import com.jastley.warmindfordestiny2.Characters.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamie1192 on 24/4/18.
 */

public class CharacterItemsRecyclerAdapter extends RecyclerView.Adapter<CharacterItemsViewHolder>{

    private Context context;

    ItemSelectionListener mListener;

    List<InventoryItemModel> itemList;

    public CharacterItemsRecyclerAdapter(Context context, List<InventoryItemModel> itemList, ItemSelectionListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.mListener = listener;
    }

    @Override
    public CharacterItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item_row_layout, parent, false);

        final CharacterItemsViewHolder mCharacterItemViewHolder = new CharacterItemsViewHolder(mView);

        mCharacterItemViewHolder.itemView.setOnClickListener(view -> {
            mListener.onItemClick(view, mCharacterItemViewHolder.getAdapterPosition(), mCharacterItemViewHolder);
        });

        return mCharacterItemViewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterItemsViewHolder holder, int position) {

        //TODO: determine item group and sort RV's by category (Kinetic/Energy/Heavy)
//        holder.setItemName(itemList.get(position).);
        holder.setItemName(itemList.get(position).getItemName());
        holder.setItemImage(itemList.get(position).getItemIcon(), context);
        holder.setStatValue(itemList.get(position).getPrimaryStatValue());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
