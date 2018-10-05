package com.jastley.exodusnetwork.checklists.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.checklists.ChecklistsViewModel;
import com.jastley.exodusnetwork.checklists.models.ChecklistTabModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.Definitions.caydeJournals;
import static com.jastley.exodusnetwork.Definitions.ghostLore;
import static com.jastley.exodusnetwork.Definitions.latentMemories;
import static com.jastley.exodusnetwork.Definitions.sleeperNodes;

public class ChecklistsParentFragment extends Fragment {

    private ChecklistsViewModel mViewModel;
    private ViewPagerAdapter mSectionsPagerAdapter;
//    @BindView(R.id.parent_checklist_viewpager) ViewPager mViewPager;
//    @BindView(R.id.checklist_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.checklist_bottom_nav) BottomNavigationView bottomNav;
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

//        mTabLayout = getActivity().findViewById(R.id.sliding_tabs);
//        mTabLayout.setVisibility(View.VISIBLE);
//        mSwipeRefreshLayout.setRefreshing(true);

        setHasOptionsMenu(true);
        mSectionsPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        populateFragmentList();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ChecklistsViewModel.class);

//        setupViewpager();
        getChecklistData();

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity)getActivity();
        if(activity != null) {
            activity.setActionBarTitle(getString(R.string.checklists));
        }
    }

    private void populateFragmentList() {
        tabFragmentList.add(new ChecklistTabModel("Latent Memories", latentMemories, R.drawable.icon_latent_memories));
        tabFragmentList.add(new ChecklistTabModel("Ghost Lore", ghostLore, R.drawable.icon_ghost));
        tabFragmentList.add(new ChecklistTabModel("Journals", caydeJournals, R.drawable.icon_journals));
        tabFragmentList.add(new ChecklistTabModel("Sleeper Nodes", sleeperNodes, R.drawable.icon_latent_memories));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mViewModel.dispose();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.checklist_toolbar, menu);

        MenuItem item = menu.findItem(R.id.checklist_spinner);
        Spinner spinner = (Spinner) item.getActionView();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.checklist_options, R.layout.spinner_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setPopupBackgroundResource(R.color.bungieBackground);
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

            Fragment fragment;
            FragmentManager fragmentManager = getChildFragmentManager();

            switch(adapterView.getItemAtPosition(i).toString()) {

                case "Latent Memories":
                    fragment = LatentMemoriesFragment.newInstance(null, null);

                    fragmentManager.beginTransaction()
                            .replace(R.id.checklist_fragment_container, fragment, "latentMemoriesFrag")
                            .commit();
                    break;
                case "Sleeper Nodes":
                    fragment = SleeperNodesFragment.newInstance(null, null);

                    fragmentManager.beginTransaction()
                            .replace(R.id.checklist_fragment_container, fragment, "sleeperNodesFrag")
                            .commit();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(getContext(), item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //returns null by default, this will throw "Attempt to invoke virtual method 'java.lang.Class java.lang.Object.getClass()'"
            return ChecklistsContentFragment.newInstance(tabFragmentList.get(position).getTabTitle(),
                                                        tabFragmentList.get(position).getChecklistHash());
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
//            return tabFragmentList.get(position).getTabTitle();
            return null;
        }

        @Override
        public int getCount() {
            return tabFragmentList.size();
        }
    }

//    private void setupViewpager() {
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(getContext(), tab.getText(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
//
    private void getChecklistData() {
        mViewModel.loadChecklistData();
//        mViewModel.getLatentMemories().observe(getActivity(), response -> {
//            mSwipeRefreshLayout.setRefreshing(false);
//            if(response.getErrorMessage() != null) {
//                showSnackbar(response.getErrorMessage());
//            }
//            else if(response.getThrowable() != null) {
//                showSnackbar(response.getThrowable().getLocalizedMessage());
//            }
//        });
    }

    private void showSnackbar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", view -> mViewModel.loadChecklistData())
                .show();
    }

    private void setupBottomNav() {

        Menu menu = bottomNav.getMenu();
        
    }
}
