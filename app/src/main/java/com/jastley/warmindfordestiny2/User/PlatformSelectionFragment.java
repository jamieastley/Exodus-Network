package com.jastley.warmindfordestiny2.User;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.jastley.warmindfordestiny2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformSelectionFragment extends DialogFragment{

    public static final List<Integer> memberships = new ArrayList<>();

    public PlatformSelectionFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);

        Bundle args = getArguments();
        List<Integer> characters = args.getIntegerArrayList(String.valueOf(memberships));

        int count = characters.size();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.select_platform)
                .setPositiveButton("First", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }
}
