package com.jastley.warmindfordestiny2.Characters.interfaces;

import android.view.View;
import com.jastley.warmindfordestiny2.Characters.holders.EquipItemViewHolder;

public interface EquipSelectListener {
    void onEquipSelect(View view, int position, EquipItemViewHolder holder);
}
