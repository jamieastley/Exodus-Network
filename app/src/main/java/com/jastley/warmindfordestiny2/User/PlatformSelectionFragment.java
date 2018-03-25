package com.jastley.warmindfordestiny2.User;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.warmindfordestiny2.R;

/**
 * Created by jastl on 23/03/2018.
 */

public class PlatformSelectionFragment extends DialogFragment{

//    public static final List<String> memberships = new ArrayList<>();
//    public static final String[] memberships2 = "PlatformSelectionFragment.characterCount";
//    public static final ArrayList<String> platforms = (ArrayList<String>);

    RecyclerView recyclerView;
    PlatformSelectionAdapter adapter;

    public PlatformSelectionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
//        ArrayList<Parcelable> list = getArguments().getParcelableArrayList("platforms");
        String[] list = args.getStringArray("platforms");

        View rootView = inflater.inflate(R.layout.fragment_platform_select, container);

        //RecyclerView
        recyclerView = rootView.findViewById(R.id.platform_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //Adapter
        adapter = new PlatformSelectionAdapter(this.getActivity(), list);
        recyclerView.setAdapter(adapter);

        this.getDialog().setTitle("Select platform");
        this.getDialog().setCancelable(false);

        return rootView;
    }


}

