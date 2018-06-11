package com.jastley.warmindfordestiny2.Inventory.interfaces;

import android.view.View;
import com.jastley.warmindfordestiny2.Inventory.holders.TransferItemViewHolder;

public interface TransferSelectListener {
    void onTransferSelect(View view, int position, TransferItemViewHolder holder);
}
