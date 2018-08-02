package com.jastley.exodusnetwork.Vendors.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Vendors.viewmodels.XurViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;


public class ItemInspectFragment extends Fragment {

    @BindView(R.id.item_screenshot) ImageView itemScreenshot;
    @BindView(R.id.stat_row_recycler_view) RecyclerView itemStatsRecyclerView;
    @BindView(R.id.item_inspect_mods_recycler) RecyclerView itemModsRecyclerView;
    @BindView(R.id.item_inspect_perks_recycler) RecyclerView itemPerksRecyclerView;
    @BindView(R.id.item_inspect_itemname) TextView itemName;

    private XurViewModel mViewModel;

    private OnFragmentInteractionListener mListener;

    public ItemInspectFragment() {
        // Required empty public constructor
    }


    public static ItemInspectFragment newInstance() {
        return new ItemInspectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_item_inspect, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(XurViewModel.class);

        getItemDetails();
    }

    private void getItemDetails() {

        itemName.setText(mViewModel.getItemDetailsModel().getItemName());

        mViewModel.getInventoryItemData().observe(this, data -> {
            Picasso.get()
                    .load(baseURL + data.getScreenshot())
                    .into(itemScreenshot);
        });

        mViewModel.getPerkSockets().observe(this, perks -> {

        });

        mViewModel.getModSockets().observe(this, mods -> {

        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initialiseRecyclerViews() {

    }
}
