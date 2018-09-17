package com.jastley.exodusnetwork.Inventory.interfaces;

import android.view.View;
import com.jastley.exodusnetwork.Inventory.holders.EquipItemViewHolder;

public interface EquipSelectListener {
    void onEquipSelect(View view, int position, EquipItemViewHolder holder);
}
