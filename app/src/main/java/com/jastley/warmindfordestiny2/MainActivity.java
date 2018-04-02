package com.jastley.warmindfordestiny2;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jastley.warmindfordestiny2.Dialogs.LoadingDialogFragment;
import com.jastley.warmindfordestiny2.Dialogs.RecyclerTouchListener;
import com.jastley.warmindfordestiny2.Interfaces.PlatformSelectionListener;
import com.jastley.warmindfordestiny2.LFG.LFGPost;
import com.jastley.warmindfordestiny2.LFG.LFGPostRecyclerAdapter;
import com.jastley.warmindfordestiny2.LFG.LFGPostViewHolder;
import com.jastley.warmindfordestiny2.LFG.NewLFGPostActivity;
import com.jastley.warmindfordestiny2.LFG.RecyclerViewClickListener;
import com.jastley.warmindfordestiny2.User.FetchUserDetails;
import com.jastley.warmindfordestiny2.User.PlatformRVHolder;
import com.jastley.warmindfordestiny2.User.PlatformSelectionFragment;
import com.jastley.warmindfordestiny2.api.AccessToken;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.Response_GetCurrentUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jastley.warmindfordestiny2.api.apiKey.apiKey;
import static com.jastley.warmindfordestiny2.api.clientKeys.clientId;
import static com.jastley.warmindfordestiny2.api.clientKeys.clientSecret;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    PlatformSelectionFragment.PlatformSelectionListener2{

    private RecyclerView mLFGRecyclerView;
    private FirebaseRecyclerAdapter mLFGPostAdapter;
    SharedPreferences savedPrefs;
    Context context;

//    private LFGPost lfgPost = new LFGPost();

//    SwipeRefreshLayout swipeRefreshLayout;

    private String redirectUri = "warmindfordestiny://callback";
    private String baseURL = "https://www.bungie.net";

    View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.lfg_feed);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .show();
                Intent intent = new Intent(getApplicationContext(), NewLFGPostActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
//        TextView nav_user = (TextView)hView.findViewById(R.id.nav_name);
//        nav_user.setText(user);

        TextView displayName = hView.findViewById(R.id.nav_displayName);

        mLFGRecyclerView = findViewById(R.id.lfg_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
//        mLinearLayoutManager.setStackFromEnd(true);
        mLFGRecyclerView.setLayoutManager(mLinearLayoutManager);

//        mLFGRecyclerView.setAdapter(mLFGPostAdapter); //TODO: may need to remove?

                //Load LFG posts from Firebase
                loadLFGPosts();
                mLFGPostAdapter.startListening();

        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String selectedPlatform = "2"; //tODO: remove hardcoded and replace with stored value retrieved from AlertDialog
        String name = savedPrefs.getString("displayName"+selectedPlatform, "");
        displayName.setText(name);

        for(int i = 0; i < 3; i++){

            int resID = getResources().getIdentifier("character_header_icon_"+i, "id", getPackageName());
            String emblem = savedPrefs.getString("emblemIcon"+i, "");
            if(emblem != ""){

                ImageView emblemIcon = hView.findViewById(resID);

                Picasso.with(this)
                    .load(baseURL+emblem)
                    .fit()
                    .transform(new CropCircleTransformation())
                    .into(emblemIcon);
            }
        }

        asyncTest();
    }

    private void asyncTest() {
        new FetchUserDetails().execute(this);
    }

    private void loadLFGPosts() {

        FirebaseDatabase.getInstance(); //.setPersistenceEnabled(true);

        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference();
        //        DatabaseReference datetimeQuery = postRef.orderByChild("dateTime");
        DatabaseReference dataRef = postRef.child("lfg");
        dataRef.keepSynced(true);
        Query query = dataRef.orderByChild("dateTime").limitToLast(20);
//        dataRef.keepSynced(true);
        //            TODO: query options, sort by dateTime

        final FirebaseRecyclerOptions lfgOptions =
                new FirebaseRecyclerOptions.Builder<LFGPost>()
//                        .setQuery(query, LFGPost.class)
                        .setIndexedQuery(query, dataRef, LFGPost.class)
                        .build();


        mLFGPostAdapter = new LFGPostRecyclerAdapter(MainActivity.this, lfgOptions, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position, LFGPostViewHolder holder) {
                Toast.makeText(MainActivity.this, holder.getDisplayName().getText() + " clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        mLFGRecyclerView.setAdapter(mLFGPostAdapter);


//        mLFGRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mLFGRecyclerView, new RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Toast.makeText(MainActivity.this,   " clicked!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        mLFGPostAdapter.startListening();

    }

    private void getPlayerProfile() {
//        TODO: shared_prefs(membershipType, membershipId, characterIds[],

        //Interceptor to add Authorization token
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                //Get access_token from shared_prefs
                SharedPreferences settings = getSharedPreferences("saved_prefs", MODE_PRIVATE);
                String accessToken = settings.getString("access_token", "");

                //add request header
                Request.Builder requestBuilder = original.newBuilder()
                        .header("X-API-Key", apiKey)
                        .header("Authorization", "Bearer " + accessToken);

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }

        });

        httpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        Retrofit retrofit = builder.build();

        BungieAPI bungieClient = retrofit.create(BungieAPI.class);
        Call<Response_GetCurrentUser> getCurrentUserCall = bungieClient.getCurrentUser();

        getCurrentUserCall.enqueue(new Callback<Response_GetCurrentUser>() {
            @Override
            public void onResponse(Call<Response_GetCurrentUser> call, Response<Response_GetCurrentUser> response) {

                try {
                    //Store specific user/character ids for reference later
                    SharedPreferences savedPrefs = getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = savedPrefs.edit();

                    //              TODO: move below membership counting and fragment to onResume after OAuth callback
                    int count = response.body().getResponse().getDestinyMemberships().size();

                    String[] memberships = new String[count];

                    if (count > 1) { //get membershipType to pass to dialogFragment

                        for (int i = 0; i < count; i++) {
                            memberships[i] = String.valueOf(response.body().getResponse().getDestinyMemberships().get(i).getMembershipType());
                        }
                    }

                    DialogFragment platformDialog = new PlatformSelectionFragment();
                    Bundle args = new Bundle();
                    args.putStringArray("platforms", memberships);

                    platformDialog.setArguments(args);
                    //                platformDialog.setCancelable(false); TODO uncomment later when onClicks work

                    platformDialog.show(getFragmentManager(), "platformSelectDialog");

                    //                platformDialog.onDismiss();
                    //                platformDialog.dismiss();

                    //TODO: get tapped recyclerView row and store as preferred character, write to sharedPrefs/sqlite

                    //write all found character details to sharedPrefs
                    for (int i = 0; i < count; i++) {

                        try {
                            editor.putInt("membershipType" + String.valueOf(response.body().getResponse().getDestinyMemberships().get(i).getMembershipType()),
                                    response.body().getResponse().getDestinyMemberships().get(i).getMembershipType());

                            editor.putString("membershipId" + String.valueOf(response.body().getResponse().getDestinyMemberships().get(i).getMembershipType()),
                                    response.body().getResponse().getDestinyMemberships().get(i).getMembershipId());

                            editor.putString("displayName" + String.valueOf(response.body().getResponse().getDestinyMemberships().get(i).getMembershipType()),
                                    response.body().getResponse().getDestinyMemberships().get(i).getDisplayName());

                            editor.commit();

                            //                    TODO: implement ProgressBar and hide it here
                            Snackbar.make(findViewById(R.id.activity_main_content), "Account database updated.", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null)
                                    .show();
                        } catch (Exception e) {
                            Snackbar.make(findViewById(R.id.activity_main_content), "Couldn't update account database.", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null)
                                    .show();
                        }

                    }
                }
                catch(Exception e){
                    Snackbar.make(findViewById(R.id.activity_main_content), "Couldn't retrieve account.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<Response_GetCurrentUser> call, Throwable t) {

            }
        });
    }

    public void showDialog(String[] memberships) {
        DialogFragment platformDialog = new PlatformSelectionFragment();
        Bundle args = new Bundle();
        args.putStringArray("platforms", memberships);

        platformDialog.setArguments(args);
        //                platformDialog.setCancelable(false); TODO uncomment later when onClicks work

        platformDialog.show(getFragmentManager(), "platformSelectDialog");
    }



    @Override
    protected void onStart() {
        super.onStart();
//        mLFGPostAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mLFGPostAdapter.stopListening();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
//            return true;

            DialogFragment loadingDialog = new LoadingDialogFragment();
            loadingDialog.setCancelable(false);
            loadingDialog.show(getFragmentManager(), "loadingDialog");


        }

        if (id == R.id.pause_live_lfg_feed) {
            mLFGPostAdapter.stopListening();
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
        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(redirectUri)) {
            final String code = uri.getQueryParameter("code");

            //Network logging - debug
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(logging);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build());

            Retrofit retrofit = builder.build();

            BungieAPI bungieClient = retrofit.create(BungieAPI.class);
            Call<AccessToken> accessTokenCall = bungieClient.getAccessToken(
                    clientId,
                    clientSecret,
                    "authorization_code",
                    code
            );

            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
//                    TODO: fullscreen loadingDialog here


                    SharedPreferences savedPrefs = getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = savedPrefs.edit();
//                    editor.putInt("your_int_key", yourIntValue);
//                    editor.commit();

                    editor.putString("access_token", response.body().getAccessToken());
                    editor.putString("refresh_token", response.body().getRefreshToken());
                    editor.putLong("token_age", System.currentTimeMillis());
                    editor.commit();
//                    System.out.println("accessToken: " + response.body().getAccessToken());
                    Toast.makeText(MainActivity.this, "Acquired access_token!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Couldn't acquire access token!", Toast.LENGTH_SHORT).show();
                }
            });

//            TODO: Account picker if user is active on >1 platform


        } //callback from browser

        else { //not an OAuth callback

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
                    savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
                    String refreshToken = savedPrefs.getString("refresh_token", "");

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

                    httpClient.addInterceptor(logging);

                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(baseURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient.build());

                    Retrofit retrofit = builder.build();

                    BungieAPI bungieClient = retrofit.create(BungieAPI.class);
                    Call<AccessToken> renewTokenCall = bungieClient.renewAccessToken(
                            clientId,
                            clientSecret,
                            "refresh_token",
                            refreshToken
                    );

                    renewTokenCall.enqueue(new Callback<AccessToken>() {

                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = savedPrefs.edit();

                            try {
                                editor.putString("access_token", response.body().getAccessToken());
                                editor.putLong("token_age", System.currentTimeMillis());
                                editor.commit();
                                Snackbar.make(findViewById(R.id.activity_main_content), "OAuth access refreshed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null)
                                        .show();

                            } catch (Exception e) {
                                Snackbar.make(findViewById(R.id.activity_main_content), "Couldn't re-authorise.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null)
                                        .show();
                            }

                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            Snackbar.make(findViewById(R.id.activity_main_content), "Couldn't re-authorise", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                        }
                    });
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lfg) {
            Intent lfgFeed = new Intent(this, MainActivity.class);
            startActivity(lfgFeed);
        }
        else if (id == R.id.nav_characters) {

            Intent accountCharacters = new Intent(this, UserCharactersActivity.class);
            startActivity(accountCharacters);
        }
        else if (id == R.id.nav_refresh_account) {
            getPlayerProfile();
        }
        else if (id == R.id.nav_log_in) {
            Intent oauthIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bungie.net/en/OAuth/Authorize" + "?client_id=" + clientId + "&response_type=code&redirect_uri=" +redirectUri));
            startActivity(oauthIntent);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view, int position, PlatformRVHolder holder) {
        Toast.makeText(this, holder.getPlatformName().getText() + " selected", Toast.LENGTH_SHORT).show();
    }
}
