package com.jastley.warmindfordestiny2.Inventory.fragments;

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
import com.jastley.warmindfordestiny2.Inventory.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.api.models.Response_GetAllCharacters;
import com.jastley.warmindfordestiny2.database.AccountDAO;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.models.Account;
import com.jastley.warmindfordestiny2.database.models.DestinyInventoryItemDefinition;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParentInventoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParentInventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParentInventoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.parent_inventory_viewpager) ViewPager mViewPager;
//    @BindView((getActivity())R.id.inventory_sliding_tabs)
    TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private OnFragmentInteractionListener mListener;
    private ArrayList<CharacterDatabaseModel> charactersList = new ArrayList<>();
    private ArrayList<DestinyInventoryItemDefinition> destinyInventoryItemDefinitionManifest = new ArrayList<>();

    private int tabIndexCount;

    public ParentInventoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParentInventoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParentInventoryFragment newInstance(String param1, String param2) {
        ParentInventoryFragment fragment = new ParentInventoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        ButterKnife.bind(this, rootView);

        mTabLayout = getActivity().findViewById(R.id.inventory_sliding_tabs);

        mTabLayout.setVisibility(View.VISIBLE);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        getAccountCharacters();

        return rootView;
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

        compositeDisposable.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity)getActivity();
        if(activity != null) {
            activity.setActionBarTitle(getString(R.string.item_transfer));
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            PlaceholderFragment fragment = PlaceholderFragment.newInstance(position);
//            return PlaceholderFragment.newInstance(position + 1);

//            CharacterInventoryFragment fragment = CharacterInventoryFragment.newInstance(position, charactersList.get(position), destinyInventoryItemDefinitionManifest);
            return CharacterInventoryFragment.newInstance(position,
                                                        charactersList.get(position),
                                                        charactersList,
                    destinyInventoryItemDefinitionManifest);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
//            return 5;
//            notifyDataSetChanged();
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

    public void getAccountCharacters() {

        AccountDAO mAccountDAO = AppDatabase.getAppDatabase(getContext()).getAccountDAO();
        Disposable disposable = mAccountDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accounts -> {

                    String tempMembershipId = "0";
                    String tempMembershipType = "0";
                    SharedPreferences savedPrefs = getActivity().getSharedPreferences("saved_prefs", MODE_PRIVATE);
                    String selectedPlatform = savedPrefs.getString("selectedPlatform", "");

                    for (Account account : accounts) {

                        Gson gson = new GsonBuilder().create();
                        Response_GetAllCharacters.CharacterData accountCharacter = gson.fromJson(account.getValue(), Response_GetAllCharacters.CharacterData.class);

                        //only get characters for selectedPlatform
                        if (accountCharacter.getMembershipType().equals(selectedPlatform)) {
                            System.out.println("forLoop " + account.getKey());
                            CharacterDatabaseModel character = new CharacterDatabaseModel(
                                    accountCharacter.getMembershipId(),
                                    accountCharacter.getCharacterId(),
                                    accountCharacter.getMembershipType(),
                                    accountCharacter.getClassType(),
                                    accountCharacter.getEmblemPath(),
                                    accountCharacter.getEmblemBackgroundPath(),
                                    accountCharacter.getBaseCharacterLevel(),
                                    accountCharacter.getLight()
                            );
                            tempMembershipId = accountCharacter.getMembershipId();
                            tempMembershipType = accountCharacter.getMembershipType();
                            charactersList.add(character);
                        }

                    }

                    //Add vault fragment on the end
                    CharacterDatabaseModel vault = new CharacterDatabaseModel();
                    vault.setMembershipId(tempMembershipId);
                    vault.setMembershipType(tempMembershipType);
                    vault.setClassType("vault");

                    charactersList.add(vault);

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
                }, throwable -> {
                    Log.d("GET_ACCOUNT_CHARACTERS", throwable.getLocalizedMessage());
                    Snackbar.make(getView(), "Unable to read account data!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", v -> getAccountCharacters())
                            .show();
                });

        compositeDisposable.add(disposable);
    }

}
