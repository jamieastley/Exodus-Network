package com.jastley.exodusnetwork.checklists.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.checklists.ChecklistsViewModel;
import com.jastley.exodusnetwork.checklists.models.ChecklistTabModel;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.Definitions.caydeJournals;
import static com.jastley.exodusnetwork.Definitions.ghostLore;
import static com.jastley.exodusnetwork.Definitions.latentMemories;
import static com.jastley.exodusnetwork.Definitions.sleeperNodes;

public class AccountChecklistsParentFragment extends Fragment {

    private ChecklistsViewModel mViewModel;
    private ViewPagerAdapter mSectionsPagerAdapter;
    @BindView(R.id.parent_checklist_viewpager) ViewPager mViewPager;
//    @BindView(R.id.checklist_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.checklist_bottom_nav) BottomNavigationView bottomNav;
    TabLayout mTabLayout;

    private int characterIndex = 0;

    private List<ChecklistTabModel> tabFragmentList = new ArrayList<>();

    public static AccountChecklistsParentFragment newInstance() {
        return new AccountChecklistsParentFragment();
    }

    public AccountChecklistsParentFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.parent_account_checklists_fragment, container, false);

        ButterKnife.bind(this, rootView);

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
        populateFragmentList();
//        setupBottomNav();
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
        tabFragmentList.add(new ChecklistTabModel("Journals", caydeJournals, R.drawable.icon_spades));
        tabFragmentList.add(new ChecklistTabModel("Sleeper Nodes", sleeperNodes, R.drawable.icon_sleeper_nodes));

        setupViewpager();

        for (int i = 0; i < tabFragmentList.size(); i++) {

            switch(i) {
                case 0:
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_latent_memories);
                    break;
                case 1:
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_ghost);
                    break;
                case 2:
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_spades);
                    break;
                case 3:
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_sleeper_nodes);
                    break;
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTabLayout.setVisibility(View.GONE);
//        mViewModel.dispose();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        Toast.makeText(getContext(), item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //returns null by default, this will throw "Attempt to invoke virtual method 'java.lang.Class java.lang.Object.getClass()'"
//            return AccountChecklistsContentFragment.newInstance(tabFragmentList.get(position).getTabTitle(),
//                                                        tabFragmentList.get(position).getChecklistHash());
            switch(position) {
                case 0:
                    return LatentMemoriesFragment.newInstance("0", tabFragmentList.get(position).getTabTitle());
                case 1:
                    return GhostLoreFragment.newInstance("1", tabFragmentList.get(position).getTabTitle());
                case 2:
                    return JournalsFragment.newInstance("2", tabFragmentList.get(position).getTabTitle());
                case 3:
                    return SleeperNodesFragment.newInstance("3", tabFragmentList.get(position).getTabTitle());
                default:
                    return null;
            }
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

    private void setupViewpager() {
        mTabLayout = getActivity().findViewById(R.id.checklist_tab_layout);

        mTabLayout.setVisibility(View.VISIBLE);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(getContext(), tabFragmentList.get(tab.getPosition()).getTabTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
//
    private void getChecklistData() {
        mViewModel.loadChecklistData();

    }

    private void showSnackbar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", view -> mViewModel.loadChecklistData())
                .show();
    }

}
