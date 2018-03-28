package com.jastley.warmindfordestiny2.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jastley.warmindfordestiny2.R;

/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformSelectionFragment extends DialogFragment implements DialogFragmentListener {

    RecyclerView recyclerView;
    PlatformSelectionAdapter adapter;

    public PlatformSelectionFragment() {

    }

    @Override
    public void onReturnValue(String foo) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        String[] list = args.getStringArray("platforms");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.fragment_platform_select, null);

        //RecyclerView
        recyclerView = rootView.findViewById(R.id.platform_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //Adapter
        adapter = new PlatformSelectionAdapter(this.getActivity(), list);
        recyclerView.setAdapter(adapter);

        builder.setView(rootView);
        builder.setTitle(R.string.select_platform);

        return builder.create();

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);


    }
}

