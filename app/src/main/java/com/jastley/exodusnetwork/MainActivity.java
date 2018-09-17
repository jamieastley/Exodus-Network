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
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.jastley.exodusnetwork.Account.fragments.AccountStatsFragment;
import com.jastley.exodusnetwork.Inventory.fragments.CharacterInventoryFragment;
import com.jastley.exodusnetwork.Inventory.fragments.ParentInventoryFragment;
import com.jastley.exodusnetwork.Dialogs.LoadingDialogFragment;
import com.jastley.exodusnetwork.Interfaces.PlatformSelectionListener;
import com.jastley.exodusnetwork.checklists.fragments.ChecklistsParentFragment;
import com.jastley.exodusnetwork.lfg.fragments.LFGDetailsFragment;
import com.jastley.exodusnetwork.lfg.fragments.LFGPostsFragment;
import com.jastley.exodusnetwork.lfg.models.SelectedPlayerModel;
import com.jastley.exodusnetwork.Dialogs.holders.PlatformRVHolder;
import com.jastley.exodusnetwork.Dialogs.PlatformSelectionFragment;
import com.jastley.exodusnetwork.Milestones.fragments.MilestonesFragment;
import com.jastley.exodusnetwork.Vendors.XurFragment;
import com.jastley.exodusnetwork.settings.SettingsActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    NavigationView navigationView;
    DrawerLayout mDrawer;

    MainActivityViewModel mViewModel;

    @BindView(R.id.nav_log_in_container) RelativeLayout mLogInContainer;
    @BindView(R.id.nav_log_out_container) RelativeLayout mLogOutContainer;

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

        mDrawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);


        //get Intent bundle from NewLFGPost (if exists)
        Intent intent = getIntent();
        boolean isLFGPost = intent.getBooleanExtra("lfgPost", false);

        postsFragment = LFGPostsFragment.newInstance(isLFGPost);
        setFragment(postsFragment);

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
            if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                toggle.setDrawerIndicatorEnabled(false);
                toggle.syncState();
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);


            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(true);
                toggle.setDrawerIndicatorEnabled(true);
                toggle.syncState();
            }
        });

        /** because toolbar is passed into ActionBarDrawerToggle, onOptionsItemSelected won't register so this is required as workaround **/
        toggle.setToolbarNavigationClickListener(view -> {
            int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
            if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                getSupportFragmentManager().popBackStack();

                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(true);
                toggle.setDrawerIndicatorEnabled(true);
                toggle.syncState();

            } else {
                toggle.setDrawerIndicatorEnabled(false);
                toggle.syncState();
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }

        });

        updateNavUI(hView);
        hideShowMenuItems();
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void hideShowMenuItems() {

        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String membershipType = savedPrefs.getString("selectedPlatform", "");
        String displayName = savedPrefs.getString("displayName"+membershipType, "");

        //Battle.Net tags
        if(displayName.contains("%23")){
            displayName = displayName.replace("%23", "#");
        }
        navigationView = findViewById(R.id.nav_view);
        Menu navMenu = navigationView.getMenu();


        if((displayName != "") && (membershipType != "")) {


            mLogInContainer.setVisibility(View.GONE);

            navMenu.findItem(R.id.nav_refresh_account).setVisible(true).setEnabled(true);
            navMenu.findItem(R.id.nav_account_stats).setVisible(true).setEnabled(true);

        }
        else { //not logged in
            mLogInContainer.setVisibility(View.VISIBLE);
            mLogOutContainer.setVisibility(View.GONE);

            navMenu.findItem(R.id.nav_account_stats).setVisible(false).setEnabled(false);
            navMenu.findItem(R.id.nav_inventory_fragment).setVisible(false).setEnabled(false);
            navMenu.findItem(R.id.nav_refresh_auth).setVisible(false).setEnabled(false);
            navMenu.findItem(R.id.nav_refresh_account).setVisible(false).setEnabled(false);

        }

    }

    private void setFragment(LFGPostsFragment postsFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.parent_fragment_frame, postsFragment, "postsFragment");

        fragmentTransaction.commit();
    }

    private void updateNavUI(View hView) {
        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String selectedPlatform = savedPrefs.getString("selectedPlatform", "");

        if(selectedPlatform != "") {
            String name = savedPrefs.getString("displayName"+selectedPlatform, "");

            //Battle.Net tags
            if(name.contains("%23")){
                name = name.replace("%23", "#");
            }
            TextView displayName = hView.findViewById(R.id.nav_displayName);
            displayName.setText(name);

            for(int i = 0; i < 3; i++){

                int resID = getResources().getIdentifier("character_header_icon_"+i, "id", getPackageName());
                String emblem = savedPrefs.getString(selectedPlatform+"emblemIcon"+i, "");
                ImageView emblemIcon = hView.findViewById(resID);

                //if switching between accounts, reset drawables (required if other account has less characters than previous account)
                emblemIcon.setImageDrawable(null);

                if(emblem != ""){

                    File dir = getDir("emblems", MODE_PRIVATE);
                    File path = new File(dir, i+".jpeg");
                    if(path.exists()){

                        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), path.getAbsolutePath());
                        roundedDrawable.setCircular(true);
                        emblemIcon.setImageDrawable(roundedDrawable);
                    }
                }
            }
            navigationView = findViewById(R.id.nav_view);
            Menu navMenu = navigationView.getMenu();

            mLogInContainer.setVisibility(View.GONE);
            mLogOutContainer.setVisibility(View.VISIBLE);

            navMenu.findItem(R.id.nav_account_stats).setVisible(true).setEnabled(true);
            navMenu.findItem(R.id.nav_inventory_fragment).setVisible(true).setEnabled(true);
            navMenu.findItem(R.id.nav_refresh_auth).setVisible(true).setEnabled(true);
            navMenu.findItem(R.id.nav_refresh_account).setVisible(true).setEnabled(true);
        }
    }

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
                fragment = ChecklistsParentFragment.newInstance();

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
                fragment = XurFragment.newInstance("something", "else");

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
