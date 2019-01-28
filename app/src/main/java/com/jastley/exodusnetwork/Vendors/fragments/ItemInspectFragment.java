package com.jastley.exodusnetwork.Vendors.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Vendors.adapters.PerksModsRecyclerAdapter;
import com.jastley.exodusnetwork.Vendors.adapters.StatValuesRecyclerAdapter;
import com.jastley.exodusnetwork.Vendors.viewmodels.XurViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;


public class ItemInspectFragment extends Fragment {

    @BindView(R.id.item_screenshot) ImageView itemScreenshot;
    @BindView(R.id.item_icon) ImageView itemIcon;
    @BindView(R.id.stat_row_recycler_view) RecyclerView itemStatsRecyclerView;
    @BindView(R.id.item_inspect_mods_recycler) RecyclerView itemModsRecyclerView;
    @BindView(R.id.item_inspect_perks_recycler) RecyclerView itemPerksRecyclerView;
    @BindView(R.id.item_inspect_description) TextView itemDescription;
    @BindView(R.id.item_type) TextView itemType;
    @BindView(R.id.item_name) TextView itemName;
    private StatValuesRecyclerAdapter statAdapter;
    private PerksModsRecyclerAdapter modsAdapter;
    private PerksModsRecyclerAdapter perksAdapter;
    private XurViewModel mViewModel;

    private static final String ARG_ITEM_HASH = "itemHash";
    private String itemHash;

    public ItemInspectFragment() {
        // Required empty public constructor
    }


    public static ItemInspectFragment newInstance(String hash) {
        ItemInspectFragment fragment = new ItemInspectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_HASH, hash);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() !=  null) {
            itemHash = getArguments().getString(ARG_ITEM_HASH);
        }
        setHasOptionsMenu(true);
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

//        displayModelDetails();
        getItemDetails();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                Toast.makeText(getContext(), "home", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getItemDetails() {

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.item_details));

        mViewModel.getInventoryItemData(itemHash).observe(this, data -> {
            if(data.getDisplayProperties() != null) {
                itemDescription.setText(data.getDisplayProperties().getDescription());
                itemName.setText(data.getDisplayProperties().getName());
                itemType.setText(data.getItemTypeDisplayName());

                Picasso.get()
                        .load(baseURL + data.getDisplayProperties().getIcon())
                        .placeholder(R.drawable.missing_icon_d2)
                        .into(itemIcon);

                Picasso.get()
                        .load(baseURL + data.getScreenshot())
                        .into(itemScreenshot);
            }
        });

        mViewModel.getStatData().observe(this, stats -> {
            if(stats.getThrowable() != null) {
                //TODO
            }
            else if(stats.getError() != null) {
                //TODO
            }
            else if(stats.getStatList() != null) {
                statAdapter.setStatData(stats.getStatList());
            }
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

    private void displayModelDetails() {
        itemDescription.setText(mViewModel.getItemDetailsModel().getDisplayProperties().getDescription());
        itemName.setText(mViewModel.getItemDetailsModel().getDisplayProperties().getName());
        itemType.setText(mViewModel.getItemDetailsModel().getItemTypeDisplayName());

        Picasso.get()
                .load(baseURL + mViewModel.getItemDetailsModel().getDisplayProperties().getIcon())
                .placeholder(R.drawable.missing_icon_d2)
                .into(itemIcon);
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

        statAdapter = new StatValuesRecyclerAdapter();
        itemStatsRecyclerView.setAdapter(statAdapter);
        itemStatsRecyclerView.setNestedScrollingEnabled(false);
        itemStatsRecyclerView.setLayoutManager(getLayoutManager());
    }

    private LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
