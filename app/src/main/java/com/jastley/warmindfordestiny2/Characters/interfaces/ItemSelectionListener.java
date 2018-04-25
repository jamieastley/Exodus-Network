package com.jastley.warmindfordestiny2.Characters.interfaces;

import android.view.View;

import com.jastley.warmindfordestiny2.Characters.adapters.CharacterItemsViewHolder;

/**
 * Created by jamie1192 on 25/4/18.
 */

public interface ItemSelectionListener {
    void onItemClick(View view, int position, CharacterItemsViewHolder holder);
}
