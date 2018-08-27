package com.jastley.exodusnetwork.Vendors.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Vendors.adapters.PerksModsRecyclerAdapter;
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
    private PerksModsRecyclerAdapter modsAdapter;
    private PerksModsRecyclerAdapter perksAdapter;
    private XurViewModel mViewModel;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseRecyclerViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(XurViewModel.class);

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
            if(perks.getThrowable() != null) {
                //TODO
            }
            else if(perks.getSocketModelList() != null) {
                perksAdapter.setListData(perks.getSocketModelList());
            }
        });

        mViewModel.getModSockets().observe(this, mods -> {
            if(mods.getThrowable() != null) {
                //TODO
            }
            else if(mods.getSocketModelList() != null) {
                modsAdapter.setListData(mods.getSocketModelList());
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void initialiseRecyclerViews() {
        perksAdapter = new PerksModsRecyclerAdapter();
        itemPerksRecyclerView.setAdapter(perksAdapter);
        itemPerksRecyclerView.setNestedScrollingEnabled(false);
        itemPerksRecyclerView.setLayoutManager(getLayoutManager());

        modsAdapter = new PerksModsRecyclerAdapter();
        itemModsRecyclerView.setAdapter(modsAdapter);
        itemModsRecyclerView.setNestedScrollingEnabled(false);
        itemModsRecyclerView.setLayoutManager(getLayoutManager());
    }

    private LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
