package com.jastley.exodusnetwork.Interfaces;

import android.view.View;

import com.jastley.exodusnetwork.Dialogs.holders.PlatformRVHolder;

/**
 * Created by jamie on 2/4/18.
 */

public interface PlatformSelectionListener {
    void onPlatformSelection(View view, int position, PlatformRVHolder holder);
}
