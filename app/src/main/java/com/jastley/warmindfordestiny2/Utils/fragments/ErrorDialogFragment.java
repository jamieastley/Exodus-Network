package com.jastley.warmindfordestiny2.Utils.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.jastley.warmindfordestiny2.R;

public class ErrorDialogFragment extends DialogFragment {

    private static DialogInterface.OnClickListener mListener;

    public static ErrorDialogFragment newInstance(String title, String message, DialogInterface.OnClickListener listener) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);
        mListener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");


        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, mListener)
                .create();

    }
}
