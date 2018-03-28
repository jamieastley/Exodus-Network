package com.jastley.warmindfordestiny2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jastley.warmindfordestiny2.R;

/**
 * Created by jamie on 26/3/18.
 */

public class LoadingDialogFragment extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_loading, null);

        builder.setView(rootView);
        builder.setTitle("Loading...");

        return builder.create();
    }
}
