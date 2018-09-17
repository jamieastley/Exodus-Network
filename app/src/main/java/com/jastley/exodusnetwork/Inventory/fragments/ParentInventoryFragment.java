package com.jastley.exodusnetwork.Inventory.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.exodusnetwork.Dialogs.LoadingDialogFragment;
import com.jastley.exodusnetwork.Inventory.InventoryViewModel;
import com.jastley.exodusnetwork.Inventory.models.CharacterDatabaseModel;
import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.database.dao.AccountDAO;
import com.jastley.exodusnetwork.database.AppDatabase;
import com.jastley.exodusnetwork.database.models.Account;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * {@link ParentInventoryFragment} calls viewModel method to load account for active platform.
 * - charactersList slot used as reference for which tab index it lives in
 *
 * - characterList holds a list of {@link com.jastley.exodusnetwork.api.models.Response_GetAllCharacters.CharacterData}
 *      so all data is readily accessible via jsonModel
 *
 * - viewModel is shared between {@link ParentInventoryFragment} and {@link CharacterInventoryFragment}
 *      so child fragment {@link CharacterInventoryFragment} can access the accountData, and write
 *      each characters inventory item list to it
 *
 */
public class ParentInventoryFragment extends Fragment {

    @BindView(R.id.parent_inventory_viewpager) ViewPager mViewPager;
    InventoryViewModel mViewModel;
    TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private OnFragmentInteractionListener mListener;
    private List<Response_GetAllCharacters.CharacterData> charactersList = new ArrayList<>();

    private int tabIndexCount;

    public ParentInventoryFragment() {
        // Required empty public constructor
    }

    public static ParentInventoryFragment newInstance() {
        return new ParentInventoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(InventoryViewModel.class);

        mViewModel.getAccountLiveData().observe(this, accounts -> {
            if(accounts.getThrowable()  != null) {
                Snackbar.make(getView(), accounts.getThrowable().getLocalizedMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", v -> mViewModel.getAccountLiveData())
                        .show();
            }
            else if(accounts.getCharacterDataList() != null) {
                this.charactersList = accounts.getCharacterDataList();
                mViewModel.setAccountList(accounts.getCharacterDataList());
                setupSectionsPagerAdapter(accounts.getCharacterDataList().size());
            }
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
                    + " must implement OnDetailsFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
//        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity)getActivity();
        if(activity != null) {
            activity.setActionBarTitle(getString(R.string.item_transfer));
        }
        initialiseTransferObserver();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //hide LFGPosts toolbar buttons
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initialiseTransferObserver() {
        mViewModel.getTransferEquipStatus().observe(this, status -> {
            if(status.getThrowable() != null) {
                dismissLoadingFragment();
                showSnackbarMessage(status.getThrowable().getLocalizedMessage());
            }
            else if(status.getMessage() != null) {
                dismissLoadingFragment();
                showSnackbarMessage(status.getMessage());
            }
        });
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .show();
    }

    private void dismissLoadingFragment() {
        LoadingDialogFragment loadingFragment = (LoadingDialogFragment)getActivity().getFragmentManager().findFragmentByTag("loadingDialog");
        if (loadingFragment != null){
            loadingFragment.dismiss();
        }
    }

    private void setupSectionsPagerAdapter(int count) {
        mTabLayout = getActivity().findViewById(R.id.sliding_tabs);

        mTabLayout.setVisibility(View.VISIBLE);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), count);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("tabSelectedListener: tab " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        int accountSize;

        public SectionsPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.accountSize = count;
        }

        @Override
        public Fragment getItem(int position) {
            //Returns a fragment for each character + 1, the last being for the characters' vault
            return CharacterInventoryFragment.newInstance(position, accountSize, position == (accountSize - 1));
        }

        @Override
        public int getCount() {
            return charactersList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);

            tabIndexCount = position;

            switch(charactersList.get(position).getClassType()){
                case "0":
                    return "Titan";
                case "1":
                    return "Hunter";
                case "2":
                    return "Warlock";
                case "vault":
                    return "Vault";
            }
            return super.getPageTitle(position);
        }
    }

}
