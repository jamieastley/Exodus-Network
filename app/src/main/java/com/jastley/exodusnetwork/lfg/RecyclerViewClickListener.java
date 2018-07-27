package com.jastley.exodusnetwork.lfg;

import android.view.View;
import com.jastley.exodusnetwork.lfg.holders.LFGPostViewHolder;

/**
 * Created by jamie on 2/4/18.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position, LFGPostViewHolder holder);
    void onLongClick(View view, int position);
}
