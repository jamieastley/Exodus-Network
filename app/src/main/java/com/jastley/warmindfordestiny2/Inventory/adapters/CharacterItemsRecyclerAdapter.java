package com.jastley.warmindfordestiny2.Inventory.adapters;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.warmindfordestiny2.Inventory.holders.CharacterItemsViewHolder;
import com.jastley.warmindfordestiny2.Inventory.interfaces.ItemSelectionListener;
import com.jastley.warmindfordestiny2.Inventory.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.R;

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

        final CharacterItemsViewHolder mCharacterItemViewHolder = new CharacterItemsViewHolder(mView, context);

        mCharacterItemViewHolder.itemView.setOnClickListener(view -> {
            mListener.onItemClick(view, mCharacterItemViewHolder.getAdapterPosition(), mCharacterItemViewHolder);
        });

        return mCharacterItemViewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterItemsViewHolder holder, int position) {

        holder.setItemName(itemList.get(position).getItemName());
        holder.setItemHash(itemList.get(position).getItemHash());
        holder.setItemImage(itemList.get(position).getItemIcon());
        holder.setIsEquipped(itemList.get(position).getIsEquipped());
        holder.setPrimaryStatValue(itemList.get(position).getPrimaryStatValue());
        holder.setItemInstanceId(itemList.get(position).getItemInstanceId());
        holder.setBucketHash(itemList.get(position).getBucketHash());
        holder.setCanEquip(itemList.get(position).getCanEquip());
        holder.setItemTypeDisplayName(itemList.get(position).getItemTypeDisplayName());
        holder.setModifierIcon(itemList.get(position).getDamageType());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateList(List<InventoryItemModel> list) {
        itemList = list;
        notifyDataSetChanged();
    }

//    private final SortedList.Callback<InventoryItemModel> modelCallback = new SortedList.Callback<InventoryItemModel>() {
//        @Override
//        public int compare(InventoryItemModel o1, InventoryItemModel o2) {
//            return mComparator.compare(o1, o2);
//        }
//
//        @Override
//        public void onChanged(int position, int count) {
//            mItemsRecyclerAdapter.notifyItemRangeChanged(position, count);
//        }
//
//        @Override
//        public boolean areContentsTheSame(InventoryItemModel oldItem, InventoryItemModel newItem) {
//            return oldItem.equals(newItem);
//        }
//
//        @Override
//        public boolean areItemsTheSame(InventoryItemModel item1, InventoryItemModel item2) {
//            return item1.getItemName().equals(item2.getItemName());
//        }
//
//        @Override
//        public void onInserted(int position, int count) {
//            mItemsRecyclerAdapter.notifyItemRangeChanged(position, count);
//        }
//
//        @Override
//        public void onRemoved(int position, int count) {
//            mItemsRecyclerAdapter.notifyItemRangeChanged(position, count);
//        }
//
//        @Override
//        public void onMoved(int fromPosition, int toPosition) {
//            mItemsRecyclerAdapter.notifyItemRangeChanged(fromPosition, toPosition);
//        }
//    };
}
