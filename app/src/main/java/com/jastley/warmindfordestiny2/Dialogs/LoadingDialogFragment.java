package com.jastley.warmindfordestiny2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jamie on 26/3/18.
 */

public class LoadingDialogFragment extends DialogFragment {

    @BindView(R.id.dialog_header_text) TextView headerText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);

        final ViewGroup nullParent = null;
        View titleView = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_layout, nullParent, false);

        ButterKnife.bind(this, titleView);

        headerText.setText("Loading...");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setCustomTitle(titleView);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_loading, null);

        builder.setView(rootView);
        builder.setTitle("Loading...");

        return builder.create();
    }
}
