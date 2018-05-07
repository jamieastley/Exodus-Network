package com.jastley.warmindfordestiny2.Characters.interfaces;

import android.view.View;
import com.jastley.warmindfordestiny2.Characters.holders.TransferItemViewHolder;

public interface TransferSelectListener {
    void onTransferSelect(View view, int position, TransferItemViewHolder holder);
}
