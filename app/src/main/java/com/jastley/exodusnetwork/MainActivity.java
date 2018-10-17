package com.jastley.exodusnetwork;

import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.jastley.exodusnetwork.Account.fragments.AccountStatsFragment;
import com.jastley.exodusnetwork.Inventory.fragments.CharacterInventoryFragment;
import com.jastley.exodusnetwork.Inventory.fragments.ParentInventoryFragment;
import com.jastley.exodusnetwork.Dialogs.LoadingDialogFragment;
import com.jastley.exodusnetwork.Interfaces.PlatformSelectionListener;
import com.jastley.exodusnetwork.checklists.fragments.AccountChecklistsParentFragment;
import com.jastley.exodusnetwork.collections.AccountCollectionFragment;
import com.jastley.exodusnetwork.lfg.fragments.LFGDetailsFragment;
import com.jastley.exodusnetwork.lfg.fragments.LFGPostsFragment;
import com.jastley.exodusnetwork.lfg.models.SelectedPlayerModel;
import com.jastley.exodusnetwork.Dialogs.holders.PlatformRVHolder;
import com.jastley.exodusnetwork.Dialogs.PlatformSelectionFragment;
import com.jastley.exodusnetwork.Milestones.fragments.MilestonesFragment;
import com.jastley.exodusnetwork.Vendors.XurFragment;
import com.jastley.exodusnetwork.overview.fragments.OverviewFragment;
import com.jastley.exodusnetwork.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.squareup.picasso.Target;

import butterknife.OnClick;

import io.reactivex.disposables.CompositeDisposable;

import static com.jastley.exodusnetwork.api.clientKeys.clientId;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    PlatformSelectionListener,
                    LFGDetailsFragment.OnDetailsFragmentInteraction,
                    LFGPostsFragment.OnFragmentInteractionListener,
                    XurFragment.OnFragmentInteractionListener,
                    AccountStatsFragment.OnAccountStatsInteractionListener,
                    MilestonesFragment.OnMilestoneFragmentInteractionListener,
                    CharacterInventoryFragment.OnFragmentInteractionListener,
//                    ItemTransferDialogFragment.OnFragmentInteractionListener,
                    ParentInventoryFragment.OnFragmentInteractionListener {


    private LFGPostsFragment postsFragment;
    SharedPreferences savedPrefs;
    DialogFragment platformDialog;
    ActionBarDrawerToggle toggle;

//    NavigationView navigationView;
//    DrawerLayout mDrawer;

    MainActivityViewModel mViewModel;

    @BindView(R.id.nav_log_in_container) RelativeLayout mLogInContainer;
    @BindView(R.id.nav_log_out_container) RelativeLayout mLogOutContainer;

    Drawer drawer;

    List<Target> targets = new ArrayList<>();
    private String redirectUri = "warmindfordestiny://callback";
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.lfg_feed);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        getSnackbarMessage();

        setupDrawer();

        //get Intent bundle from NewLFGPost (if exists)
        Intent intent = getIntent();
        boolean isLFGPost = intent.getBooleanExtra("lfgPost", false);

        postsFragment = LFGPostsFragment.newInstance(isLFGPost);
        setFragment(postsFragment);

        //hacky workaround to toggle between drawer/home depending on back-stack
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
            if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

                LFGPostsFragment fragment = (LFGPostsFragment) getSupportFragmentManager().findFragmentByTag("LFG_FRAGMENT");
                if(fragment != null && fragment.isVisible()) {
                    drawer.setSelection(5);
                }
            }
        });

    }



    private void setupDrawer() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.bungieCard)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withEmail("Not signed in")
                                .withIcon(getResources().getDrawable(R.drawable.exodusnetwork_alpha_icon_small))
                )
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener((view, profile, currentProfile) -> {

                    //TODO: update account data on switch and store in DB

                    if(!currentProfile) {
                        Toast.makeText(MainActivity.this, String.valueOf(profile.getIdentifier()), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = savedPrefs.edit();
                        editor.putString("selectedPlatform", String.valueOf(profile.getIdentifier()));
                        editor.apply();

                        getAllCharacterData(String.valueOf(profile.getIdentifier()));
                    }

                    return false;
                })
                .build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        // identifiers < 5 reserved for membershipTypes in ProfileDrawer
                        new PrimaryDrawerItem().withIdentifier(5).withName(getResources().getString(R.string.lfg_feed))
                                .withIcon(getResources().getDrawable(R.drawable.icon_lfg)),

                        //Dynamically items are inserted here if account is logged in

                        new PrimaryDrawerItem().withIdentifier(11).withName(getResources().getString(R.string.settings))
                                .withIcon(getResources().getDrawable(R.drawable.icon_settings)),

                        new PrimaryDrawerItem().withIdentifier(99).withName("Log In")
                                .withIcon(getResources().getDrawable(R.drawable.icon_log_in))
                                .withIconColorRes(R.color.colorWhite)
                )
//                .addStickyDrawerItems(new PrimaryDrawerItem().withIdentifier(99).withName("Log In")
//                        .withIcon(getResources().getDrawable(R.drawable.icon_log_in))
//                        .withIconColorRes(R.color.colorWhite))
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    //Top-level items
                    if(drawerItem instanceof PrimaryDrawerItem) {
//                        Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();

                        Fragment fragment;
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        //lfg
                        if(drawerItem.getIdentifier() == 5) {
                            fragment = LFGPostsFragment.newInstance(false);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "LFG_FRAGMENT")
                                    .commit();
                        }
                        //Item Transfer
                        else if(drawerItem.getIdentifier() == 6) {
                            fragment = ParentInventoryFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "INVENTORY_FRAGMENT")
                                    .commit();
                        }
                        //Account stats
                        else if(drawerItem.getIdentifier() == 7) {
                            fragment = AccountStatsFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "ACCT_STATS_FRAGMENT")
                                    .commit();
                        }
                        //Xur (9 of course ;) )
                        else if(drawerItem.getIdentifier() == 9) {
                            fragment = XurFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "ACCT_STATS_FRAGMENT")
                                    .commit();
                        }
                        //Milestones
                        else if(drawerItem.getIdentifier() == 10) {
                            fragment = MilestonesFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "MILESTONES_FRAGMENT")
                                    .commit();
                        }
                        //Settings
                        else if(drawerItem.getIdentifier() == 11) {
                            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                            startActivity(intent);
                        }
                        else if(drawerItem.getIdentifier() == 12) {
                            fragment = AccountCollectionFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "MILESTONES_FRAGMENT")
                                    .commit();
                        }
                        //Refresh account
                        else if(drawerItem.getIdentifier() == 14) {
                            String mType = savedPrefs.getString("selectedPlatform", "");
                            if(mType != "") {

                                getAllCharacterData(mType);
                            }
                        }
                        //Overview
                        else if(drawerItem.getIdentifier() == 15) {
                            fragment = OverviewFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "MILESTONES_FRAGMENT")
                                    .commit();
                        }

                        //Log In
                        else if(drawerItem.getIdentifier() == 99) {
                            Intent oauthIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bungie.net/en/OAuth/Authorize" + "?client_id=" + clientId + "&response_type=code&redirect_uri=" +redirectUri));
                            startActivity(oauthIntent);
                        }

                    }
                    //Items within ExpandableListView
                    else if(drawerItem instanceof SecondaryDrawerItem) {

                        Fragment fragment;
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        if(drawerItem.getIdentifier() == 81) {
                            fragment = AccountChecklistsParentFragment.newInstance();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.parent_fragment_frame, fragment, "CHECKLIST_PARENT_FRAGMENT")
                                    .commit();
                        }
                        else if(drawerItem.getIdentifier() == 82) {
                            Toast.makeText(MainActivity.this, "TODO: Character checklists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                })
                .build();
//        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        //Set initially selected account as active
        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String selectedPlatform = savedPrefs.getString("selectedPlatform", "");

        if(selectedPlatform != "") {
            headerResult.removeProfile(0);
            String displayName = savedPrefs.getString("displayName"+selectedPlatform, "");
            String dNameFixed = displayName.replace("%23", "#");
            switch(selectedPlatform) {
                case "1": //Xbox
                    headerResult.addProfiles(new ProfileDrawerItem()
                            .withEmail(dNameFixed)
                            .withNameShown(false)
                            .withIdentifier(1)
                            .withIcon(getResources().getDrawable(R.drawable.icon_xbl)));
//                            .withIdentifier());
                    break;
                case "2": //PSN
                    headerResult.addProfiles(new ProfileDrawerItem()
                            .withEmail(dNameFixed)
                            .withNameShown(false)
                            .withIdentifier(2)
                            .withIcon(getResources().getDrawable(R.drawable.icon_psn_classic)));
                    break;
                case "4": //BNet
                    headerResult.addProfiles(new ProfileDrawerItem()
                            .withEmail(dNameFixed)
                            .withNameShown(false)
                            .withIdentifier(4)
                            .withIcon(getResources().getDrawable(R.drawable.icon_blizzard)));
                    break;
            }

            //remove log-in button
            drawer.removeItem(99);
            drawer.removeAllStickyFooterItems();

            drawer.addItemsAtPosition(2,
                    new DividerDrawerItem(),

                    new PrimaryDrawerItem().withIdentifier(15).withName(getResources().getString(R.string.overview))
                            .withIcon(getResources().getDrawable(R.drawable.icon_destiny)),
                    new PrimaryDrawerItem().withIdentifier(6).withName(getResources().getString(R.string.item_transfer))
                            .withIcon(getResources().getDrawable(R.drawable.icon_vault_small)),
                    new PrimaryDrawerItem().withIdentifier(7).withName(getResources().getString(R.string.account_stats))
                            .withIcon(getResources().getDrawable(R.drawable.icon_stats)),
                    new ExpandableDrawerItem().withName("Checklists")
                            .withIcon(getResources().getDrawable(R.drawable.icon_ticks))
                            .withIdentifier(8)
                            .withSelectable(false)
                            .withSubItems(
                                    new SecondaryDrawerItem().withName(R.string.accountChecklists)
                                            .withLevel(2)
                                            .withIdentifier(81),
                                    new SecondaryDrawerItem().withName(R.string.characterChecklists)
                                            .withLevel(2)
                                            .withIdentifier(82)
                            ),
                    new PrimaryDrawerItem().withName(getResources().getString(R.string.collectibles))
                    .withIcon(getResources().getDrawable(R.drawable.icon_collection))
                    .withIdentifier(12),

                    new PrimaryDrawerItem().withIdentifier(10).withName(getResources().getString(R.string.milestones))
                            .withIcon(getResources().getDrawable(R.drawable.milestone_icon)),
                    new PrimaryDrawerItem().withIdentifier(9).withName(getResources().getString(R.string.xur))
                            .withIcon(getResources().getDrawable(R.drawable.ic_xur_icon_svg)),

                    new DividerDrawerItem(),

                    new PrimaryDrawerItem().withIdentifier(14).withName(getResources().getString(R.string.refresh_account))
                            .withIcon(getResources().getDrawable(R.drawable.icon_refresh))
            );

            //if user has accounts for other platforms
            for(int i = 0; i <= 4; i++) {
                //loop through saved account data, ignore already selectedPlatform
                if (!String.valueOf(i).equals(selectedPlatform)) {

                    String name = savedPrefs.getString("displayName" + i, "");
                    if (name != "") {
                        String nameFix = name.replace("%23", "#");
                        switch (i) {
                            case 1: //Xbox
                                headerResult.addProfiles(new ProfileDrawerItem()
                                        .withEmail(nameFix)
                                        .withNameShown(false)
                                        .withIdentifier(1)
                                        .withIcon(getResources().getDrawable(R.drawable.icon_xbl)));
                                break;
                            case 2: //PSN
                                headerResult.addProfiles(new ProfileDrawerItem()
                                        .withEmail(nameFix)
                                        .withNameShown(false)
                                        .withIdentifier(2)
                                        .withIcon(getResources().getDrawable(R.drawable.icon_psn_classic)));
                                break;
                            case 4: //BNet
                                headerResult.addProfiles(new ProfileDrawerItem()
                                        .withEmail(nameFix)
                                        .withNameShown(false)
                                        .withIdentifier(4)
                                        .withIcon(getResources().getDrawable(R.drawable.icon_blizzard)));
                                break;
                        }
                    }

                }
            }
        }
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    private void setFragment(LFGPostsFragment postsFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.parent_fragment_frame, postsFragment, "postsFragment");

        fragmentTransaction.commit();
    }

//    private void updateNavUI(View hView) {
//        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
//        String selectedPlatform = savedPrefs.getString("selectedPlatform", "");
//
//        if(selectedPlatform != "") {
//            String name = savedPrefs.getString("displayName"+selectedPlatform, "");
//
//            //Battle.Net tags
//            if(name.contains("%23")){
//                name = name.replace("%23", "#");
//            }
////            TextView displayName = hView.findViewById(R.id.nav_displayName);
////            displayName.setText(name);
//
//            for(int i = 0; i < 3; i++){
//
//                int resID = getResources().getIdentifier("character_header_icon_"+i, "id", getPackageName());
//                String emblem = savedPrefs.getString(selectedPlatform+"emblemIcon"+i, "");
//                ImageView emblemIcon = hView.findViewById(resID);
//
//                //if switching between accounts, reset drawables (required if other account has less characters than previous account)
//                emblemIcon.setImageDrawable(null);
//
//                if(emblem != ""){
//
//                    File dir = getDir("emblems", MODE_PRIVATE);
//                    File path = new File(dir, i+".jpeg");
//                    if(path.exists()){
//
//                        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), path.getAbsolutePath());
//                        roundedDrawable.setCircular(true);
//                        emblemIcon.setImageDrawable(roundedDrawable);
//                    }
//                }
//            }
//            navigationView = findViewById(R.id.nav_view);
//            Menu navMenu = navigationView.getMenu();
//
//            mLogInContainer.setVisibility(View.GONE);
//            mLogOutContainer.setVisibility(View.VISIBLE);
//
//            navMenu.findItem(R.id.nav_account_stats).setVisible(true).setEnabled(true);
//            navMenu.findItem(R.id.nav_inventory_fragment).setVisible(true).setEnabled(true);
//            navMenu.findItem(R.id.nav_refresh_auth).setVisible(true).setEnabled(true);
//            navMenu.findItem(R.id.nav_refresh_account).setVisible(true).setEnabled(true);
//        }
//    }

    public void showLoadingDialog(String title, String message) {
        LoadingDialogFragment fragment = LoadingDialogFragment.newInstance(title, message);
        fragment.setCancelable(false);
        fragment.show(getFragmentManager(), "loadingDialog");
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen()) {
//            drawer.closeDrawer(GravityCompat.START);
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //catch OAuth token callback
    @Override
    protected void onResume() {
        super.onResume();

        //callback for OAuth
        final Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(redirectUri)) {
            final String code = uri.getQueryParameter("code");

//            getAccessToken(code);

            startAuthFlow(code);
        } //callback from browser

        else { //not an OAuth callback, check token_age and refresh

            checkTokenTime();

        }
    }

    //1. OAuth starting point after intent callback from browser
    private void startAuthFlow(String code) {

        showLoadingDialog("Loading...", "Please wait");

        mViewModel.getAccessToken(code);

        //2. if Bungie account has >1 platform linked (PSN/Xbox/Battle.Net), select which one
        mViewModel.getPlatformSelector().observe(this, platforms -> {

            if(!platforms.isEmpty()){
                dismissLoadingFragment();

                platformDialog = new PlatformSelectionFragment();
                Bundle args = new Bundle();

                args.putStringArrayList("platforms", platforms);

                platformDialog.setArguments(args);
                platformDialog.setCancelable(false);
                platformDialog.show(getFragmentManager(), "platformSelectDialog");
            }
        });
    }

    private void getSnackbarMessage() {
        //Snackbar for any errors during OAuth flow
        mViewModel.getSnackbarMessage().observe(this, snackbarMessage -> {

            dismissLoadingFragment();

            if(snackbarMessage.getMessage() != null) {
                Snackbar.make(findViewById(R.id.activity_main_content), snackbarMessage.getMessage(), Snackbar.LENGTH_LONG)
                        .show();
            }
            else if(snackbarMessage.getThrowable() != null) {
                Snackbar.make(findViewById(R.id.activity_main_content), snackbarMessage.getThrowable().getLocalizedMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", v -> onClickLogIn())
                        .show();
            }
        });
    }

    //3. PlatformSelection dialog clickListener
    @Override
    public void onPlatformSelection(View view, int position, PlatformRVHolder holder) {

        String selectedPlatform = holder.getPlatformType().getText().toString();

        platformDialog.dismiss();

//        getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = savedPrefs.edit();
//
//        editor.putString("selectedPlatform", selectedPlatform);
//        editor.apply();
        mViewModel.writeToSharedPrefs("selectedPlatform", selectedPlatform);

        //4.
        getAllCharacterData(selectedPlatform);
    }

    private void checkTokenTime() {
        if(mViewModel.checkIsTokenExpired()) {
            mViewModel.refreshAccessToken();
        }
    }

    //4. Get all character data for selected platform
    private void getAllCharacterData(String mType) {

        showLoadingDialog("Loading...", "Getting character data");

        mViewModel.getAllCharacters(mType);

        //5. Download emblem icons and finalise OAuth/account retrieval flow
        mViewModel.getEmblemDownload().observe(this, emblems -> {
            if(emblems.isComplete()) {

                dismissLoadingFragment();

                //Restart activity to update UI state
                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                //set flags so pressing back won't trigger previous state of MainActivity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }



    private void dismissLoadingFragment() {
        DialogFragment loadingFragment = (DialogFragment)getFragmentManager().findFragmentByTag("loadingDialog");
        if (loadingFragment != null){
            loadingFragment.dismiss();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        int id = item.getItemId();

        switch(id) {

            case android.R.id.home:
                Log.d("you idiot", "hurdur");
                break;

            case R.id.nav_lfg:
                LFGPostsFragment lfgFragment = LFGPostsFragment.newInstance(false);

                setFragment(lfgFragment);
                break;

            case R.id.nav_account_stats:
                fragment = AccountStatsFragment.newInstance();

                fragmentManager.beginTransaction()
                        .replace(R.id.parent_fragment_frame, fragment, "accountStatsFrag")
                        .commit();
                break;

            case R.id.nav_checklists:
                fragment = AccountChecklistsParentFragment.newInstance();

                fragmentManager.beginTransaction()
                        .replace(R.id.parent_fragment_frame, fragment, "checklistsFrag")
                        .commit();
//                Intent checklistIntent = new Intent(this, ChecklistActivity.class);
//                startActivity(checklistIntent);
                break;

            case R.id.nav_milestones:
                fragment = MilestonesFragment.newInstance();

                fragmentManager.beginTransaction()
                        .replace(R.id.parent_fragment_frame, fragment, "milestonesFrag")
                        .commit();
                break;

            case R.id.xur:
                fragment = XurFragment.newInstance();

                fragmentManager.beginTransaction()
                        .replace(R.id.parent_fragment_frame, fragment)
//                    .addToBackStack("xurStack")
                        .commit();
                break;

            case R.id.nav_inventory_fragment:
                fragment = ParentInventoryFragment.newInstance();

                fragmentManager.beginTransaction()
                        .replace(R.id.parent_fragment_frame, fragment, "inventoryFrag")
//                    .addToBackStack("inventoryFragment")
                        .commit();
                break;

//            case R.id.nav_refresh_auth:
//                refreshAccessToken();
//                break;
//
//            case R.id.nav_refresh_account:
//                getMembershipsForCurrentUser();
//                break;

            case R.id.nav_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

//        else if (id == R.id.nav_log_out_container) {
//            Log.d("log_out_click", "works");
//        }
//        else if (id == R.id.nav_log_out_text) {
//            Log.d("log_out_click", "TEXT");
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(SelectedPlayerModel playerModel) {
//        LFGDetailsFragment.OnDetailsFragmentInteraction.onFra;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String uri) {

    }


    @Override
    public void LFGDetailsListener() {
//        faButton.hide();
    }

   public void toggleFab() {
        LFGPostsFragment fragment = (LFGPostsFragment) getSupportFragmentManager().findFragmentByTag("postsFragment");
        if(fragment != null){
            fragment.toggleFAButton();
        }

   }



    @Override
    public void OnMilestoneFragmentInteractionListener(Uri uri) {

    }

    @Override
    public void onAccountStatsInteraction(Uri uri) {

    }


    @OnClick(R.id.nav_log_in_container)
    public void onClickLogIn() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        Intent oauthIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bungie.net/en/OAuth/Authorize" + "?client_id=" + clientId + "&response_type=code&redirect_uri=" +redirectUri));
        startActivity(oauthIntent);



    }

    @OnClick(R.id.nav_log_out_container)
    public void onClickLogOut() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        mViewModel.logOut();

        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        //set flags so pressing back won't trigger previous state of MainActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }


}
