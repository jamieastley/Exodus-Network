package com.jastley.warmindfordestiny2.Characters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.database.AccountDAO;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.CollectablesDAO;
import com.jastley.warmindfordestiny2.database.models.Account;
import com.jastley.warmindfordestiny2.database.models.Collectables;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CharacterInventoryActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        CharacterInventoryFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private List<CharacterDatabaseModel> charactersList = new ArrayList<>();
    private ArrayList<Collectables> collectablesManifest = new ArrayList<>();
    private JsonObject collectablesObject = new JsonObject();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
//    private ViewPager mViewPager;
//    private TabLayout mTabLayout;

    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.sliding_tabs) TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_inventory);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

//        JsonParser parser = new JsonParser();

//        CollectablesDAO mCollectablesDAO = AppDatabase.getAppDatabase(this).getCollectablesDAO();
//        mCollectablesDAO.getAllCollectables()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(collectables -> {
//                    for(int i = 0; i < collectables.size(); i++){
//
////                        String key = collectables.get(i).getKey();
////                        JsonElement value = (JsonElement) parser.parse(collectables.get(i).getValue());
//////                        JsonObject obj = (JsonObject) parser.parse(collectables.get(i).getValue());
//
////                        collectablesObject.add(key, value);
//                        Collectables collectableData = new Collectables();
//                        collectableData.setKey(collectables.get(i).getKey());
//                        collectableData.setValue(collectables.get(i).getValue());
//                        collectablesManifest.add(collectableData);
//                    }

                    getAccountCharacters();
//                });


//        AccountDAO mAccountDAO = AppDatabase.getAppDatabase(this).getAccountDAO();
//        mAccountDAO.getAll()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(accounts -> {
//
//                    for(int i = 0; i < accounts.size(); i++){
//                        System.out.println(accounts.get(i).getKey());
//                        JsonObject charObj = (JsonObject) parser.parse(accounts.get(i).getValue());
//                        CharacterDatabaseModel character = new CharacterDatabaseModel(
//                                charObj.get("membershipId").getAsString(),
//                                charObj.get("characterId").getAsString(),
//                                charObj.get("membershipType").getAsString(),
//                                charObj.get("classType").getAsString()
//                        );
//                        charactersList.add(character);
//                    }
//
//                    mViewPager.setAdapter(mSectionsPagerAdapter);
//                    mTabLayout.setupWithViewPager(mViewPager);
//        });

//        // Set up the ViewPager with the sections adapter.
//        mViewPager = findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//
//        mTabLayout = findViewById(R.id.sliding_tabs);
//        mTabLayout.setupWithViewPager(mViewPager);





//        //Nav drawer
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            fragment.setRetainInstance(true);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_character_inventory, container, false);
//            TextView textView = rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
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

            CharacterInventoryFragment fragment = CharacterInventoryFragment.newInstance(position, charactersList.get(position), collectablesManifest);
            return CharacterInventoryFragment.newInstance(position, charactersList.get(position), collectablesManifest);
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

            switch(charactersList.get(position).getClassType()){
                case "0":
                    return "Titan";
                case "1":
                    return "Hunter";
                case "2":
                    return "Warlock";
            }
            return super.getPageTitle(position);
        }
    }

    public void getAccountCharacters() {
        JsonParser parser = new JsonParser();

        AccountDAO mAccountDAO = AppDatabase.getAppDatabase(this).getAccountDAO();
        mAccountDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accounts -> {

                    for(int i = 0; i < accounts.size(); i++){
                        System.out.println(accounts.get(i).getKey());
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
                        charactersList.add(character);
                    }

                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    mTabLayout.setupWithViewPager(mViewPager);
                });
    }

    public List<CharacterDatabaseModel> getCharactersList(){
        return charactersList;
    }
}
