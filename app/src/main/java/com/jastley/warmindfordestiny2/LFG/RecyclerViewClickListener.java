package com.jastley.warmindfordestiny2.LFG;

import android.view.View;

/**
 * Created by jamie on 2/4/18.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
