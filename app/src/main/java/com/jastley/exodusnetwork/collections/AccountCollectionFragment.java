package com.jastley.exodusnetwork.collections;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Utils.ColumnCalculator;
import com.jastley.exodusnetwork.Vendors.fragments.ItemInspectFragment;
import com.jastley.exodusnetwork.collections.adapters.CollectionImageRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AccountCollectionFragment extends Fragment {

    @BindView(R.id.collection_recycler_view) RecyclerView collectionRecyclerView;
    @BindView(R.id.collectible_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    CollectionImageRecyclerAdapter recyclerAdapter;

    private AccountCollectionViewModel mViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static AccountCollectionFragment newInstance() {
        return new AccountCollectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.account_collection_fragment, container, false);

        ButterKnife.bind(this, mView);

        setHasOptionsMenu(true);
        initialiseRecyclerView();
        handleCollectibleClick();
        mSwipeRefresh.setRefreshing(true);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AccountCollectionViewModel.class);

        mViewModel.getProfileCollectibles().observe(this, collectibles -> {
            if(collectibles.getThrowable() != null) {
                mSwipeRefresh.setRefreshing(false);
            }
            else if(collectibles.getMessage() != null) {
                mSwipeRefresh.setRefreshing(false);
            }
            else if(collectibles.getItemList() != null) {
                recyclerAdapter.setChecklistData(collectibles.getItemList());
                mSwipeRefresh.setRefreshing(false);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.collectibles));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        compositeDisposable.dispose();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initialiseRecyclerView() {
        recyclerAdapter = new CollectionImageRecyclerAdapter();
        collectionRecyclerView.setAdapter(recyclerAdapter);
        int columnCount = ColumnCalculator.calculateNoOfColumns(getContext());
        collectionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
    }

    private void handleCollectibleClick() {
        Disposable disposable = recyclerAdapter.getOnClickSubject().subscribe(s -> {
            Toast.makeText(getContext(), "TODO: "+s, Toast.LENGTH_SHORT).show();

            Fragment inspectFragment = ItemInspectFragment.newInstance(s);

//                    FragmentManager fragmentManager = getFragmentManager();
            if(getActivity() != null){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down)
                        .replace(R.id.parent_fragment_frame, inspectFragment, "ITEM_INSPECT")
                        .addToBackStack("ITEM_INSPECTION")
                        .commit();
            }

        });
        compositeDisposable.add(disposable);
    }

}
