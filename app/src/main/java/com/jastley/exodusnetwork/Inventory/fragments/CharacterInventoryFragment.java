package com.jastley.exodusnetwork.Inventory.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;


import android.widget.Toast;

import com.jastley.exodusnetwork.Definitions;
import com.jastley.exodusnetwork.Inventory.InventoryViewModel;
import com.jastley.exodusnetwork.Inventory.adapters.CharacterItemsRecyclerAdapter;
import com.jastley.exodusnetwork.Inventory.holders.TransferItemViewHolder;
import com.jastley.exodusnetwork.Inventory.interfaces.SuccessListener;
import com.jastley.exodusnetwork.Inventory.interfaces.TransferSelectListener;
import com.jastley.exodusnetwork.Inventory.models.CharacterDatabaseModel;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Dialogs.LoadingDialogFragment;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Vendors.HeaderItemDecoration;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

import static com.jastley.exodusnetwork.Definitions.pursuits;

public class CharacterInventoryFragment extends Fragment
        implements TransferSelectListener,
                    SuccessListener,
                    SearchView.OnQueryTextListener {

    private int mTabNumber;
    private int mTabCount;
    private boolean isVault;
    private List<InventoryItemModel> itemList = new ArrayList<>();

    @BindView(R.id.inventory_items_recyclerview) RecyclerView mItemsRecyclerView;
//    @BindView(R.id.inventory_items_progress) ProgressBar loadingProgress;
    @BindView(R.id.inventory_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    private CharacterItemsRecyclerAdapter mItemsRecyclerAdapter;
    private OnFragmentInteractionListener mListener;

    InventoryViewModel mViewModel;
    HeaderItemDecoration headerItemDecoration;


    public CharacterInventoryFragment() {
        // Required empty public constructor
    }

    public static CharacterInventoryFragment newInstance(int tabNumber, int tabCount, boolean isVault) {
        CharacterInventoryFragment fragment = new CharacterInventoryFragment();
        Bundle args = new Bundle();
        Log.e("FRAGMENT_CREATE", String.valueOf(tabNumber));
        args.putInt("ARG_TAB_NUMBER", tabNumber);
        args.putInt("ARG_TAB_COUNT", tabCount);
        args.putBoolean("ARG_IS_VAULT", isVault);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabNumber = getArguments().getInt("ARG_TAB_NUMBER");
            mTabCount = getArguments().getInt("ARG_TAB_COUNT");
            isVault = getArguments().getBoolean("ARG_IS_VAULT");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_character_inventory, container, false);

        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initialiseRecyclerViews();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mItemsRecyclerView.scheduleLayoutAnimation();
            mItemsRecyclerAdapter.clearItemList();
            mSwipeRefreshLayout.setRefreshing(true);
            getCharacterInventory(mTabNumber);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(InventoryViewModel.class);

//        getCharacterInventory(mTabNumber);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            System.out.println("FragmentInteraction triggered");
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailsFragmentInteraction");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
//        compositeDisposable.dispose();

        //Hide character tab bar
//        TabLayout mTabLayout = getActivity().findViewById(R.id.sliding_tabs);
//
//        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inventory_toolbar, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.inventory_search).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.inventory_refresh:
//                refreshInventory();
                mSwipeRefreshLayout.setRefreshing(true);
                getCharacterInventory(mTabNumber);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        getCharacterInventory(mTabNumber);
//        initialiseTransferObserver();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onTransferSelect(View view, int position, TransferItemViewHolder holder) {
        Toast.makeText(getContext(), " selected!!!?", Toast.LENGTH_SHORT).show();
        System.out.println("onTransferSelect triggered");
    }



    private void initialiseRecyclerViews() {
        mItemsRecyclerAdapter = new CharacterItemsRecyclerAdapter((view, position, holder) -> {
//            Toast.makeText(getContext(), holder.getItemName().getText().toString(), Toast.LENGTH_SHORT)
//                    .show();
                mViewModel.setClickedItem(holder.getClickedItem());
                handleItemClick(holder.getClickedItem().getBucketHash());
        });
        mSwipeRefreshLayout.setRefreshing(true);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
        mItemsRecyclerView.setAdapter(mItemsRecyclerAdapter);
        mItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mItemsRecyclerView.setNestedScrollingEnabled(false);
        mItemsRecyclerView.setLayoutAnimation(animationController);

    }


    private void getCharacterInventory(int slot) {

        if(isVault) {
            mViewModel.startInventoryRetrieval(slot, true);
        }
        else {
            mViewModel.startInventoryRetrieval(slot, false);
        }

        switch (slot){
            case 0:
                mViewModel.getFirstSlotInventory().observe(this, itemList -> {
                    if(itemList.getThrowable() != null) {
                        showSnackbarMessage(itemList.getThrowable().getLocalizedMessage());
                    }
                    else if(itemList.getMessage() != null) {
                        showSnackbarMessage(itemList.getMessage());
                    }
                    else if(itemList.getItemModelList() != null) {
                        handleLiveDataResponse(itemList.getItemModelList());
                    }
                });
                break;
            case 1:
                mViewModel.getSecondSlotInventory().observe(this, itemList -> {
                    if(itemList.getThrowable() != null) {
                        showSnackbarMessage(itemList.getThrowable().getLocalizedMessage());
                    }
                    else if(itemList.getMessage() != null) {
                        showSnackbarMessage(itemList.getMessage());
                    }
                    else if(itemList.getItemModelList() != null) {
                        handleLiveDataResponse(itemList.getItemModelList());
                    }
                });
                break;
            case 2:
                mViewModel.getThirdSlotInventory().observe(this, itemList -> {
                    if(itemList.getThrowable() != null) {
                        showSnackbarMessage(itemList.getThrowable().getLocalizedMessage());
                    }
                    else if(itemList.getMessage() != null) {
                        showSnackbarMessage(itemList.getMessage());
                    }
                    else if(itemList.getItemModelList() != null) {
                        handleLiveDataResponse(itemList.getItemModelList());
                    }
                });
                break;
            case 3:
                mViewModel.getFourthSlotInventory().observe(this, itemList -> {
                    if(itemList.getThrowable() != null) {
                        showSnackbarMessage(itemList.getThrowable().getLocalizedMessage());
                    }
                    else if(itemList.getMessage() != null) {
                        showSnackbarMessage(itemList.getMessage());
                    }
                    else if(itemList.getItemModelList() != null) {
                        handleLiveDataResponse(itemList.getItemModelList());
                    }
                });
                break;
        }
    }

    private void handleLiveDataResponse(List<InventoryItemModel> items) {

        this.itemList = items;
        resetItemDecoration(items);
        mItemsRecyclerAdapter.setItemList(items);
        hideLoading();
    }

    private void handleItemClick(String bucketHash){

        if(bucketHash.equals(pursuits)) {
            ObjectiveDetailsModal objModal = ObjectiveDetailsModal.newInstance(null, null);
            objModal.show(getChildFragmentManager(), "objectiveModalDialog");
        } else {

            ItemTransferDialogFragment transferDialogFragment = ItemTransferDialogFragment.newInstance(mTabNumber, mTabCount, this);
            transferDialogFragment.show(getChildFragmentManager(), "transferModalDialog");
        }
    }

    @Override
    public void inProgress(String title) {
        showLoadingDialog(title, "Please wait");
    }


//    private void initialiseTransferObserver() {
//        mViewModel.getTransferEquipStatus().observe(this, status -> {
//            if(status.getThrowable() != null) {
//                dismissLoadingFragment();
//                showSnackbarMessage(status.getThrowable().getLocalizedMessage());
//            }
//            else if(status.getMessage() != null) {
//                dismissLoadingFragment();
//                showSnackbarMessage(status.getMessage());
//            }
//        });
//    }

    private void hideLoading() {
//        loadingProgress.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .show();
    }



    private void showLoadingDialog(String title, String message) {
        LoadingDialogFragment fragment = LoadingDialogFragment.newInstance(title, message);
        fragment.setCancelable(false);
        fragment.show(getActivity().getFragmentManager(), "loadingDialog");
    }

    private void dismissLoadingFragment() {
        LoadingDialogFragment loadingFragment = (LoadingDialogFragment)getActivity().getFragmentManager().findFragmentByTag("loadingDialog");
        if (loadingFragment != null){
            loadingFragment.dismiss();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        if(itemList != null){
            final List<InventoryItemModel> filteredItems = filter(itemList, query);

            resetItemDecoration(filteredItems);
            mItemsRecyclerAdapter.setItemList(filteredItems);
            mItemsRecyclerView.scrollToPosition(0);
        }
        return true;
    }

    private static List<InventoryItemModel> filter(List<InventoryItemModel> itemList, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<InventoryItemModel> filteredList = new ArrayList<>();
        for (InventoryItemModel item : itemList) {
            final String text = item.getItemName().toLowerCase();
            if(text.contains(lowerCaseQuery)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }


    public void resetItemDecoration(List<InventoryItemModel> newList) {

        mItemsRecyclerView.removeItemDecoration(headerItemDecoration);

        headerItemDecoration = new HeaderItemDecoration(120, true, new HeaderItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0 || newList.get(position).getSlot() != newList.get(position -1).getSlot();
            }

            @Override
            public CharSequence getSectionHeader(int position) {
                return Definitions.getBucketName(newList.get(position).getSlot());
            }

            @Override
            public CharSequence getItemCount(int position) {

                int count = 0;
                int section = newList.get(position).getSlot();

                for(InventoryItemModel item : newList) {
                    if(item.getSlot() == section) {
                        count++;
                    }
                }
                String itemCount = String.valueOf(count);

                if(section > 0 && section < 10) { //item/armor/equippable item (can only hold 10)
                    itemCount = itemCount+"/10";
                }

                return itemCount;
            }
        });
        mItemsRecyclerView.addItemDecoration(headerItemDecoration);
    }
}
