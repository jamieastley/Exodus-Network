package com.jastley.warmindfordestiny2.Inventory.interfaces;

import android.view.View;

import com.jastley.warmindfordestiny2.Inventory.holders.CharacterItemsViewHolder;

/**
 * Created by jamie1192 on 25/4/18.
 */

public interface ItemSelectionListener {
    void onItemClick(View view, int position, CharacterItemsViewHolder holder);
}
