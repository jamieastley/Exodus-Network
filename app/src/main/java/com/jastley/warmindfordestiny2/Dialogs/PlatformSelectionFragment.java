package com.jastley.warmindfordestiny2.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.Interfaces.PlatformSelectionListener;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Dialogs.adapters.PlatformSelectionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformSelectionFragment extends DialogFragment {

    @BindView(R.id.dialog_header_text)
    TextView headerText;

    RecyclerView recyclerView;
    PlatformSelectionAdapter adapter;
    PlatformSelectionListener mListener;

    public PlatformSelectionFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        final String[] list = args.getStringArray("platforms");

        View titleView = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);

        ButterKnife.bind(this, titleView);

        headerText.setText(R.string.select_platform);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setCustomTitle(titleView);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.fragment_platform_select, null);

        //RecyclerView
        recyclerView = rootView.findViewById(R.id.platform_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //Adapter
        adapter = new PlatformSelectionAdapter(this.getActivity(), list, mListener);

        recyclerView.setAdapter(adapter);

        builder.setView(rootView);
        builder.setTitle(R.string.select_platform);

        return builder.create();

    }


    @Override //needed to attach listener to dialogFragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mListener = (PlatformSelectionListener) activity;
        }
        catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);


    }
}

