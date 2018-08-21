package com.jastley.exodusnetwork.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ColumnCalculator {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 80);
    }
}
