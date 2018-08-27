package com.jastley.exodusnetwork.Vendors.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Vendors.holders.XurItemsViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class XurItemsRecyclerAdapter extends RecyclerView.Adapter<XurItemsViewHolder> {

    private Context mContext;
    private List<InventoryItemModel> xurItems = new ArrayList<>();

    public final PublishSubject<InventoryItemModel> onClickSubject = PublishSubject.create();

    public XurItemsRecyclerAdapter(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public XurItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xur_inventory_row, parent, false);

        return new XurItemsViewHolder(mView, mContext);
    }

    @Override
    public void onBindViewHolder(XurItemsViewHolder holder, int position) {
        holder.setItemName(xurItems.get(position).getItemName());
        holder.setItemIcon(xurItems.get(position).getItemIcon());
        holder.setItemType(xurItems.get(position).getItemTypeDisplayName());
        holder.setItemType(xurItems.get(position).getItemTypeDisplayName());
        holder.setItemCostImage(xurItems.get(position).getCostItemIcon());
        holder.setItemCostText(xurItems.get(position).getCostsQuantity());
        holder.setSalesCount(xurItems.get(position).getSaleHistoryCount());
        holder.setItemHash(xurItems.get(position).getItemHash());

        final InventoryItemModel item = xurItems.get(position);

        holder.itemView.setOnClickListener(view -> onClickSubject.onNext(item));
    }

    public Observable<InventoryItemModel> getClickedItem() {
        return onClickSubject;
    }

    @Override
    public int getItemCount() {
        return xurItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setXurItems(List<InventoryItemModel> items) {
        xurItems = items;
        notifyDataSetChanged();
    }
}
