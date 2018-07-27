package com.jastley.exodusnetwork.Inventory.interfaces;

import android.view.View;
import com.jastley.exodusnetwork.Inventory.holders.TransferItemViewHolder;

public interface TransferSelectListener {
    void onTransferSelect(View view, int position, TransferItemViewHolder holder);
}
