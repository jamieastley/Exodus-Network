package com.jastley.warmindfordestiny2.Characters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.database.AccountDAO;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.models.Collectables;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InventoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.parent_inventory_viewpager) ViewPager mViewPager;
    @BindView(R.id.sliding_tabs) TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private OnFragmentInteractionListener mListener;
    private ArrayList<CharacterDatabaseModel> charactersList = new ArrayList<>();
    private ArrayList<Collectables> collectablesManifest = new ArrayList<>();

    private int tabIndexCount;

    public InventoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InventoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InventoryFragment newInstance(String param1, String param2) {
        InventoryFragment fragment = new InventoryFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        ButterKnife.bind(this, rootView);

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
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

//            CharacterInventoryFragment fragment = CharacterInventoryFragment.newInstance(position, charactersList.get(position), collectablesManifest);
            return CharacterInventoryFragment.newInstance(position,
                                                        charactersList.get(position),
                                                        charactersList,
                                                        collectablesManifest);
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
        JsonParser parser = new JsonParser();

        AccountDAO mAccountDAO = AppDatabase.getAppDatabase(getContext()).getAccountDAO();
        mAccountDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accounts -> {

                    String tempMembershipId = "0";
                    String tempMembershipType = "0";

                    for(int i = 0; i < accounts.size(); i++){
                        System.out.println("forLoop " + accounts.get(i).getKey());
                        JsonObject charObj = (JsonObject) parser.parse(accounts.get(i).getValue());
                        CharacterDatabaseModel character = new CharacterDatabaseModel(
                                charObj.get("membershipId").getAsString(),
                                charObj.get("characterId").getAsString(),
                                charObj.get("membershipType").getAsString(),
                                charObj.get("classType").getAsString(),
                                charObj.get("emblemPath").getAsString(),
                                charObj.get("emblemBackgroundPath").getAsString(),
                                charObj.get("baseCharacterLevel").getAsString(),
                                charObj.get("light").getAsString()
                        );
                        tempMembershipId = charObj.get("membershipId").getAsString();
                        tempMembershipType = charObj.get("membershipType").getAsString();
                        charactersList.add(character);
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
                });
    }

}
