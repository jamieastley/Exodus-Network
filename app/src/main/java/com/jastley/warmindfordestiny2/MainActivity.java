package com.jastley.warmindfordestiny2;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
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

import com.google.gson.JsonObject;
import com.jastley.warmindfordestiny2.Account.fragments.AccountStatsFragment;
import com.jastley.warmindfordestiny2.Inventory.fragments.CharacterInventoryFragment;
import com.jastley.warmindfordestiny2.Inventory.fragments.ParentInventoryFragment;
import com.jastley.warmindfordestiny2.Inventory.fragments.ItemTransferDialogFragment;
import com.jastley.warmindfordestiny2.Dialogs.LoadingDialogFragment;
import com.jastley.warmindfordestiny2.Interfaces.PlatformSelectionListener;
import com.jastley.warmindfordestiny2.LFG.fragments.LFGDetailsFragment;
import com.jastley.warmindfordestiny2.LFG.fragments.LFGPostsFragment;
import com.jastley.warmindfordestiny2.LFG.models.SelectedPlayerModel;
import com.jastley.warmindfordestiny2.Dialogs.holders.PlatformRVHolder;
import com.jastley.warmindfordestiny2.Dialogs.PlatformSelectionFragment;
import com.jastley.warmindfordestiny2.Milestones.fragments.MilestonesFragment;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.Vendors.XurFragment;
import com.jastley.warmindfordestiny2.api.*;
import com.jastley.warmindfordestiny2.database.AccountDAO;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.models.Account;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.squareup.picasso.Target;

import butterknife.OnClick;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;
import static com.jastley.warmindfordestiny2.api.clientKeys.clientId;
import static com.jastley.warmindfordestiny2.api.clientKeys.clientSecret;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    PlatformSelectionListener,
                    LFGDetailsFragment.OnDetailsFragmentInteraction,
                    LFGPostsFragment.OnFragmentInteractionListener,
                    XurFragment.OnFragmentInteractionListener,
                    AccountStatsFragment.OnAccountStatsInteractionListener,
                    MilestonesFragment.OnMilestoneFragmentInteractionListener,
                    CharacterInventoryFragment.OnFragmentInteractionListener,
                    ItemTransferDialogFragment.OnFragmentInteractionListener,
                    ParentInventoryFragment.OnFragmentInteractionListener {


    private LFGPostsFragment postsFragment;
    SharedPreferences savedPrefs;
    DialogFragment platformDialog;
    ActionBarDrawerToggle toggle;

    NavigationView navigationView;
    DrawerLayout mDrawer;

    @BindView(R.id.nav_log_in_container) RelativeLayout mLogInContainer;
    @BindView(R.id.nav_log_out_container) RelativeLayout mLogOutContainer;

    List<Target> targets = new ArrayList<>();
    private String redirectUri = "warmindfordestiny://callback";
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.lfg_feed);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDrawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

        postsFragment = new LFGPostsFragment();
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

//        Bundle args = new Bundle();

        if((displayName != "") && (membershipType != "")) {


            mLogInContainer.setVisibility(View.GONE);
//            navMenu.findItem(R.id.nav_log_in).setVisible(false).setEnabled(false);
            navMenu.findItem(R.id.nav_refresh_account).setVisible(true).setEnabled(true);

//            postsFragment.isFabVisible(true);
//            args.putString("fab", "show");
//            faButton.show();
        }
        else { //not logged in
            mLogInContainer.setVisibility(View.VISIBLE);
            mLogOutContainer.setVisibility(View.GONE);
//            navMenu.findItem(R.id.nav_log_in).setVisible(true).setEnabled(true);
            navMenu.findItem(R.id.nav_inventory_fragment).setVisible(false).setEnabled(false);
            navMenu.findItem(R.id.nav_refresh_auth).setVisible(false).setEnabled(false);
            navMenu.findItem(R.id.nav_refresh_account).setVisible(false).setEnabled(false);
//            faButton.hide();
//            args.putString("fab", "hide");
//            postsFragment.isFabVisible(false);
        }

//        postsFragment.putArguments(args);

    }

    private void setFragment(LFGPostsFragment postsFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.lfg_content_frame, postsFragment, "postsFragment");
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
//            navMenu.findItem(R.id.nav_log_in).setVisible(false).setEnabled(false);
            navMenu.findItem(R.id.nav_inventory_fragment).setVisible(true).setEnabled(true);
            navMenu.findItem(R.id.nav_refresh_auth).setVisible(true).setEnabled(true);
            navMenu.findItem(R.id.nav_refresh_account).setVisible(true).setEnabled(true);
//            faButton.show();
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
//        mLFGPostAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
//        mLFGPostAdapter.stopListening();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
        switch(item.getItemId()){

            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        TODO: modify pause icon state here

        return super.onPrepareOptionsMenu(menu);
    }

    //catch OAuth token callback
    @Override
    protected void onResume() {
        super.onResume();

        //callback for OAuth
        final Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(redirectUri)) {
            final String code = uri.getQueryParameter("code");

            getAccessToken(code);

        } //callback from browser

        else { //not an OAuth callback, check token_age and refresh

            savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
            Long timestamp = savedPrefs.getLong("token_age", 0);

            try {
                timestamp = savedPrefs.getLong("token_age", 0);
            }
            catch (Exception e) {
                System.out.println("No timestamp: " + e);
            }
            if (timestamp != 0) {
                Long hour = 60L * 60L * 1000L;
                Long now = System.currentTimeMillis();
                Long timespan = now - timestamp;

                if (timespan > hour) {

                    refreshAccessToken();

                }
            }
        }
    }

    private void getAccessToken(String code){
        BungieAPI mBungieAPI = RetrofitHelper.getOAuthRequestBungieAPI(baseURL, this);

        Disposable disposable = mBungieAPI.getAccessToken(
                clientId,
                clientSecret,
                "authorization_code",
                code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accessToken -> {

                    try{
                        SharedPreferences savedPrefs = getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = savedPrefs.edit();

                        editor.putString("access_token", accessToken.getAccessToken());
                        editor.putString("refresh_token", accessToken.getRefreshToken());
                        editor.putLong("token_age", System.currentTimeMillis());
                        editor.apply();

                        Log.d("ACCESS_TOKEN", accessToken.getAccessToken());


                        //We're authorised, now get currentUser player profile
                        getMembershipsForCurrentUser();
                    }
                    catch(Exception e){
                        Log.d("ACCESS_TOKEN(CALLBACK?)", e.getLocalizedMessage());
                        Snackbar.make(findViewById(R.id.activity_main_content), "An error occurred while trying to authorize.", Snackbar.LENGTH_LONG)
                                .show();
                    }
                }, throwable -> {
                    if(throwable instanceof NoNetworkException) {
                        Log.d("OAUTH_REFRESH", throwable.getLocalizedMessage());
                        Snackbar.make(findViewById(R.id.activity_main_content), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", v -> refreshAccessToken())
                                .show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void refreshAccessToken() {

        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String refreshToken = savedPrefs.getString("refresh_token", "");

        BungieAPI mBungieAPI = RetrofitHelper.getOAuthRequestBungieAPI( baseURL, this);

        Disposable disposable = mBungieAPI.renewAccessToken(
                    clientId,
                    clientSecret,
                    "refresh_token",
                    refreshToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accessToken -> {

                    SharedPreferences.Editor editor = savedPrefs.edit();

                    try {
                        editor.putString("access_token", accessToken.getAccessToken());
                        editor.putLong("token_age", System.currentTimeMillis());
                        editor.apply();
                        Snackbar.make(findViewById(R.id.activity_main_content), "Authorization refreshed.", Snackbar.LENGTH_LONG)
                                .show();

                    } catch (Exception e) {
                        Snackbar.make(findViewById(R.id.activity_main_content), "Couldn't re-authorise.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null) //TODO: Retry button here
                                .show();
                    }
                }, throwable -> {
                    if(throwable instanceof NoNetworkException) {
                        Log.d("OAUTH_REFRESH", throwable.getLocalizedMessage());
                        Snackbar.make(findViewById(R.id.activity_main_content), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", v -> refreshAccessToken())
                                .show();
                    }
                    else {
                        Snackbar.make(findViewById(R.id.activity_main_content), "Couldn't re-authorise.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null) //TODO: Retry button here
                                .show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    //AKA getPlayerProfile()
    private void getMembershipsForCurrentUser() {

        //block UI interaction and show loader while membershipData is being retrieved
        showLoadingDialog("Loading...", "Please wait");

        BungieAPI mBungieAPI = RetrofitHelper.getAuthBungieAPI(this, baseURL);

        Disposable disposable = mBungieAPI.getMembershipsCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentUser -> {

                    if(!currentUser.getErrorCode().equals("1")){
                        Log.d("GET_CURRENT_USER", currentUser.getMessage());
                        Snackbar.make(findViewById(R.id.coordinator_lfg_posts), currentUser.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null) //TODO: Retry button here
                                .show();
                    }
                    else {

                        try {
                            SharedPreferences savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = savedPrefs.edit();

                            int membershipsCount = currentUser.getResponse().getDestinyMemberships().size();

                            String[] memberships = new String[membershipsCount];

                            //store all found membership details
                            for (int i = 0; i < membershipsCount; i++) {

                                try {
                                    memberships[i] = currentUser.getResponse().getDestinyMemberships().get(i).getMembershipType();
                                    editor.putString("membershipType" + memberships[i], memberships[i]);
                                    editor.putString("membershipId" + memberships[i], currentUser.getResponse().getDestinyMemberships().get(i).getMembershipId());

                                    //sanitise BattleNet displayName for Firebase
                                    if(currentUser.getResponse().getDestinyMemberships().get(i).getMembershipType().equals("4")){

                                        String displayName = currentUser.getResponse().getBungieNetUser().getBlizzardDisplayName();
                                        if(displayName.contains("#")){
                                            displayName = displayName.replace("#", "%23");
                                            editor.putString("displayName" + memberships[i], displayName);
                                        }
                                    }
                                    //PlayStation/Xbox players
                                    else {
                                        editor.putString("displayName" + memberships[i], currentUser.getResponse().getDestinyMemberships().get(i).getDisplayName());
                                    }
                                    editor.apply();
                                }
                                catch(Exception e){
                                    Log.d("GET_CURRENT_USER_PARSE", e.getLocalizedMessage());
                                    Snackbar.make(findViewById(R.id.coordinator_lfg_posts), "Couldn't update account database.", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null)
                                            .show();
                                    dismissLoadingFragment();
                                }
                            }

                            //ask user to select which platform they want to use
                            if(membershipsCount > 1) {

                                for (int i = 0; i < membershipsCount; i++) {
                                    memberships[i] = currentUser.getResponse().getDestinyMemberships().get(i).getMembershipType();
                                }

                                platformDialog = new PlatformSelectionFragment();
                                Bundle args = new Bundle();
                                args.putStringArray("platforms", memberships);

                                platformDialog.setArguments(args);
                                platformDialog.setCancelable(false);
                                platformDialog.show(getFragmentManager(), "platformSelectDialog");
                            }

                            //only one active platform
                            else {
                                String membershipType = memberships[0];
                                editor = savedPrefs.edit();
                                editor.putString("selectedPlatform", membershipType);
                                editor.apply();

                                getAllCharacters(membershipType);
                            }

                        }
                        catch(Exception e){
                            Log.d("GET_CURRENT_USER", e.getLocalizedMessage());
                            Snackbar.make(findViewById(R.id.coordinator_lfg_posts), "Couldn't retrieve membership data.", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null)
                                    .show();
                            dismissLoadingFragment();
                        }
                    }
                }, throwable -> {
                    if(throwable instanceof NoNetworkException) {
                        Log.d("GET_CURRENT_USER", throwable.getLocalizedMessage());
                        Snackbar.make(findViewById(R.id.coordinator_lfg_posts), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", v -> refreshAccessToken())
                                .show();
                    }
                    else {

                        Log.d("GET_CURRENT_USER", throwable.getLocalizedMessage());
                        Snackbar.make(findViewById(R.id.coordinator_lfg_posts), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", v -> getMembershipsForCurrentUser())
                                .show();
                    }
                    dismissLoadingFragment();
                });
        compositeDisposable.add(disposable);
    }

    //PlatformSelection dialog clickListener
    @Override
    public void onPlatformSelection(View view, int position, PlatformRVHolder holder) {

        String selectedPlatform = holder.getPlatformType().getText().toString();

        platformDialog.dismiss();

        getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = savedPrefs.edit();

        editor.putString("selectedPlatform", selectedPlatform);
        editor.apply();

        getAllCharacters(selectedPlatform);
    }

    private void getAllCharacters(String platform) {

        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String membershipId = savedPrefs.getString("membershipId"+platform, "");

        BungieAPI mBungieAPI = RetrofitHelper.getAuthBungieAPI(this, baseURL);

        Disposable disposable = mBungieAPI.getAllCharacters(platform, membershipId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(allCharacters -> {

                    //can't use gson because we need the whole characterData obj as a string
                    JsonObject charactersObj = (JsonObject) allCharacters;

                    if(!charactersObj.get("ErrorCode").getAsString().equals("1")) {
                        Log.d("GET_ALL_CHARACTERS", charactersObj.get("Message").getAsString());
                        Snackbar.make(findViewById(R.id.coordinator_lfg_posts), charactersObj.get("Message").getAsString(), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null) //TODO: retry button
                                .show();
                    }
                    else {

                        List<Account> mAccountList = new ArrayList<>();
                        int count = 0;

                        List<String> emblemIconList = new ArrayList<>();

                        for(Iterator iterator = charactersObj.get("Response").getAsJsonObject().get("characters").getAsJsonObject().get("data").getAsJsonObject().keySet().iterator(); iterator.hasNext(); ) {

                            String key = (String) iterator.next();

                            //create list of characters to store in Room
                            Account mAccount = new Account();
                            mAccount.setKey(platform+"character"+count);
                            mAccount.setValue(charactersObj.get("Response").getAsJsonObject().get("characters").getAsJsonObject().get("data").getAsJsonObject().get(key).getAsJsonObject().toString());
                            mAccountList.add(mAccount);

                            try{
                                SharedPreferences.Editor editor = savedPrefs.edit();
                                String emblemIcon = charactersObj.get("Response").getAsJsonObject().get("characters").getAsJsonObject().get("data").getAsJsonObject().get(key).getAsJsonObject().get("emblemPath").getAsString();
                                String currentMembershipType = charactersObj.get("Response").getAsJsonObject().get("characters").getAsJsonObject().get("data").getAsJsonObject().get(key).getAsJsonObject().get("membershipType").getAsString();

                                if(platform.equals(currentMembershipType)){
                                    emblemIconList.add(emblemIcon);
                                }
                                editor.putString(platform+"emblemIcon"+count, emblemIcon);
                                editor.putString(platform+"emblemBackgroundPath"+count, charactersObj.get("Response").getAsJsonObject().get("characters").getAsJsonObject().get("data").getAsJsonObject().get(key).getAsJsonObject().get("emblemBackgroundPath").getAsString());
                                editor.apply();
                            }
                            catch(Exception e){
                                Log.d("EMBLEM_PREFS_INSERT", e.getLocalizedMessage());
                            }

                            //update character count
                            /** used counter instead of classType in keys because players can have more than one of the same classType **/
                            count++;
                        }

                        //Insert into Room
                        AccountDAO mAccountDAO = AppDatabase.getAppDatabase(this).getAccountDAO();
                        mAccountDAO.insertAll(mAccountList);

                        //Get back onto mainThread to do UI stuff
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        Runnable mRunnable = () -> {
                            downloadEmblems(emblemIconList);
                        };
                        mainHandler.post(mRunnable);

                    }

                }, throwable -> {
                    if(throwable instanceof NoNetworkException) {
                        Log.d("GET_ALL_CHARACTERS_ERR", throwable.getLocalizedMessage());
                        Snackbar.make(findViewById(R.id.coordinator_lfg_posts), "No network detected!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", v -> getAllCharacters(platform))
                                .show();
                    }
                    else {
                        Log.d("GET_ALL_CHARACTERS_ERR", throwable.getLocalizedMessage());
                        Snackbar.make(findViewById(R.id.coordinator_lfg_posts), throwable.getLocalizedMessage(), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null)
                                .show();
                    }
                    dismissLoadingFragment();
                });
        compositeDisposable.add(disposable);
    }

    private void downloadEmblems(List<String> emblems) {

        View hView =  navigationView.getHeaderView(0);

        for (int i = 0; i < emblems.size(); i++) {

            final int finalI = i;

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    try{
                        File dir = getDir("emblems", MODE_PRIVATE);
                        if(!dir.exists()){
                            dir.mkdir();
                        }

                        File path = new File(dir, finalI +".jpeg");
                        //delete file if already exists, player may have updated their emblem so we need the new one
                        if(path.exists()){
                            if(path.delete()){
                                Log.d("EMBLEM_DOWNLOAD_DELETE", path.toString() + " deleted.");
                            }
                        }
                        FileOutputStream fos = new FileOutputStream(path);
                        int quality = 100;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                        fos.flush();
                        fos.close();

                        Log.d("EMBLEM_DOWNLOADED", String.valueOf(finalI));

                        if(finalI == emblems.size() -1){
                            targets = null;

                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//
                            //set flags so pressing back won't trigger previous state of MainActivity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                    catch(Exception e){
                        Log.d("EMBLEM_DOWNLOAD", e.getLocalizedMessage());
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.d("PICASSO_EMBLEM_DOWNLOAD", e.getLocalizedMessage());
                    int resID = getResources().getIdentifier("character_header_icon_"+finalI, "id", getPackageName());

                    ImageView emblemIcon = hView.findViewById(resID);
                    Bitmap missingPlaceholder = BitmapFactory.decodeResource(getResources(), R.drawable.missing_icon_d2);

                    RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), missingPlaceholder);
                    roundedDrawable.setCircular(true);
                    emblemIcon.setImageDrawable(roundedDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    int resID = getResources().getIdentifier("character_header_icon_"+finalI, "id", getPackageName());

                    ImageView emblemIcon = hView.findViewById(resID);
                    Bitmap missingPlaceholder = BitmapFactory.decodeResource(getResources(), R.drawable.missing_icon_d2);

                    RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), missingPlaceholder);
                    roundedDrawable.setCircular(true);
                    emblemIcon.setImageDrawable(roundedDrawable);
                }
            };

            targets.add(target);

            Picasso.get()
                    .load(baseURL + emblems.get(finalI))
                    .into(target);
        }

        toggleFab();
//        updateNavUI(hView);
        dismissLoadingFragment();

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

        int id = item.getItemId();

        if(id == android.R.id.home) {
            Log.d("you idiot", "hurdur");
        }

        if (id == R.id.nav_lfg) {

            Fragment fragment = LFGPostsFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.lfg_content_frame, fragment)
                    .commit();
        }

        else if (id == R.id.nav_account_stats) {

            Fragment fragment = AccountStatsFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.lfg_content_frame, fragment)
                    .commit();
        }

        else if (id == R.id.nav_milestones) {
            Fragment fragment = MilestonesFragment.newInstance();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.lfg_content_frame, fragment)
                    .commit();
        }

        else if (id == R.id.xur) {
            Fragment fragment = XurFragment.newInstance("something", "else");

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.lfg_content_frame, fragment)
//                    .addToBackStack("xurStack")
                    .commit();
        }
        else if (id == R.id.nav_inventory_fragment) {
            Fragment fragment = ParentInventoryFragment.newInstance("string", "string2");

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.lfg_content_frame, fragment)
//                    .addToBackStack("inventoryFragment")
                    .commit();
        }

        else if (id == R.id.nav_refresh_auth) {
            refreshAccessToken();
        }

        else if (id == R.id.nav_refresh_account) {
//            getPlayerProfile();
            getMembershipsForCurrentUser();
        }
//        else if (id == R.id.nav_log_in) {
//            Intent oauthIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bungie.net/en/OAuth/Authorize" + "?client_id=" + clientId + "&response_type=code&redirect_uri=" +redirectUri));
//            startActivity(oauthIntent);
//        }
        else if (id == R.id.nav_log_out_container) {
            Log.d("log_out_click", "works");
        }
        else if (id == R.id.nav_log_out_text) {
            Log.d("log_out_click", "TEXT");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(SelectedPlayerModel playerModel) {
//        LFGDetailsFragment.OnDetailsFragmentInteraction.onFra;
    }

//    public class retryListener implements View.OnClickListener{
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }

    public void setDrawerHomeIcon() {
//        toggle.setHomeAsUpIndicator(R.drawable.ic_back_button);
        toggle.setDrawerIndicatorEnabled(true);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String uri) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 1){

            LFGPostsFragment postsFragment = (LFGPostsFragment) getSupportFragmentManager().findFragmentByTag("postsFragment");

            postsFragment.loadLFGPosts(null);

            Snackbar.make(findViewById(R.id.coordinator_lfg_posts), "Post submitted!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show();
        }
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

        AccountDAO mAccountDAO = AppDatabase.getAppDatabase(this).getAccountDAO();

        AsyncTask.execute(mAccountDAO::deleteAccount);

        SharedPreferences.Editor editor = savedPrefs.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//
        //set flags so pressing back won't trigger previous state of MainActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }


}
