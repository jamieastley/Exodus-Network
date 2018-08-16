package com.jastley.exodusnetwork.checklists.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.checklists.ChecklistsViewModel;
import com.jastley.exodusnetwork.checklists.models.ChecklistTabModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.Definitions.caydeJournals;
import static com.jastley.exodusnetwork.Definitions.forsakenCollection;
import static com.jastley.exodusnetwork.Definitions.ghostLore;
import static com.jastley.exodusnetwork.Definitions.latentMemories;
import static com.jastley.exodusnetwork.Definitions.raidLairs;
import static com.jastley.exodusnetwork.Definitions.sleeperNodes;

public class ChecklistsParentFragment extends Fragment {

    private ChecklistsViewModel mViewModel;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    @BindView(R.id.parent_checklist_viewpager) ViewPager mViewPager;
    @BindView(R.id.checklist_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    TabLayout mTabLayout;

    private List<ChecklistTabModel> tabFragmentList = new ArrayList<>();

    public static ChecklistsParentFragment newInstance() {
        return new ChecklistsParentFragment();
    }

    public ChecklistsParentFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.parent_checklists_fragment, container, false);

        ButterKnife.bind(this, rootView);
        mSwipeRefreshLayout.setRefreshing(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        populateFragmentList();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTabLayout = getActivity().findViewById(R.id.sliding_tabs);

        mViewModel = ViewModelProviders.of(getActivity()).get(ChecklistsViewModel.class);

        setupViewpager();
        getChecklistData();

    }

    private void populateFragmentList() {
        tabFragmentList.add(new ChecklistTabModel("Latent Memories", latentMemories));
        tabFragmentList.add(new ChecklistTabModel("Ghost Lore", ghostLore));
        tabFragmentList.add(new ChecklistTabModel("Journals", caydeJournals));
        tabFragmentList.add(new ChecklistTabModel("Sleeper Nodes", sleeperNodes));
        tabFragmentList.add(new ChecklistTabModel("Raid Lairs", raidLairs));
        tabFragmentList.add(new ChecklistTabModel("Items", forsakenCollection));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.dispose();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabFragmentList.get(position).getTabTitle();
        }

        @Override
        public int getCount() {
            return tabFragmentList.size();
        }
    }

    private void setupViewpager() {
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getChecklistData() {
        mViewModel.loadChecklistData();
        mViewModel.getLatentMemories().observe(this, response -> {
            mSwipeRefreshLayout.setRefreshing(false);
            if(response.getErrorMessage() != null) {
                showSnackbar(response.getErrorMessage());
            }
            else if(response.getThrowable() != null) {
                showSnackbar(response.getThrowable().getLocalizedMessage());
            }
        });
    }

    private void showSnackbar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", view -> mViewModel.loadChecklistData())
                .show();
    }
}
