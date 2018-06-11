package com.jastley.warmindfordestiny2.Inventory.interfaces;

import android.view.View;
import com.jastley.warmindfordestiny2.Inventory.holders.EquipItemViewHolder;

public interface EquipSelectListener {
    void onEquipSelect(View view, int position, EquipItemViewHolder holder);
}
