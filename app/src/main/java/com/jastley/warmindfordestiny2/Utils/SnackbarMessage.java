package com.jastley.warmindfordestiny2.Utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarMessage {

    public static Snackbar getSnackbar(View view, String message, int duration) {
        return Snackbar.make(view, message, duration);
    }
}
