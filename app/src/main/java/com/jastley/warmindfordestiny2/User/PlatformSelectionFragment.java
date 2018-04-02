package com.jastley.warmindfordestiny2.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jastley.warmindfordestiny2.Interfaces.PlatformSelectionListener;
import com.jastley.warmindfordestiny2.LFG.LFGPostViewHolder;
import com.jastley.warmindfordestiny2.LFG.RecyclerViewClickListener;
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

    public interface PlatformSelectionListener2 {
        void onClick(View view, int position, PlatformRVHolder holder);
    }

    PlatformSelectionListener2 mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        final String[] list = args.getStringArray("platforms");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.fragment_platform_select, null);

        //RecyclerView
        recyclerView = rootView.findViewById(R.id.platform_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //Adapter
        adapter = new PlatformSelectionAdapter(this.getActivity(), list, mListener);//{
//            @Override
//            public void onClick(View view, int position, PlatformRVHolder holder) {
//                System.out.println("selected: " + position);
//
//            }
//        });


        recyclerView.setAdapter(adapter);

        builder.setView(rootView);
        builder.setTitle(R.string.select_platform);

        return builder.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mListener = (PlatformSelectionListener2) activity;
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

