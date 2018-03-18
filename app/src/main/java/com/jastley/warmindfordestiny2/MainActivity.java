package com.jastley.warmindfordestiny2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jastley.warmindfordestiny2.LFG.LFGPost;
import com.jastley.warmindfordestiny2.LFG.LFGPostRecyclerAdapter;
import com.jastley.warmindfordestiny2.LFG.NewLFGPostActivity;
import com.jastley.warmindfordestiny2.api.AccessToken;
import com.jastley.warmindfordestiny2.api.BungieAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jastley.warmindfordestiny2.api.clientKeys.clientId;
import static com.jastley.warmindfordestiny2.api.clientKeys.clientSecret;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mLFGRecyclerView;
    private FirebaseRecyclerAdapter mLFGPostAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private String redirectUri = "warmindfordestiny://callback";
    private String baseURL = "https://www.bungie.net/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .show();
                Intent intent = new Intent(getApplicationContext(), NewLFGPostActivity.class);
//                startActivityForResult(intent, 1);
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

        mLFGRecyclerView = findViewById(R.id.lfg_recycler_view);
        mLFGRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLFGRecyclerView.setAdapter(mLFGPostAdapter); //TODO: may need to remove?

        //SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.lfg_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                //Load LFG posts from Firebase
                loadLFGPosts();
                mLFGPostAdapter.startListening();
            }
        });


    }

    private void loadLFGPosts() {

        swipeRefreshLayout.setRefreshing(true);

        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference();
        //        DatabaseReference datetimeQuery = postRef.orderByChild("dateTime");
        DatabaseReference dataRef = postRef.child("lfg");
        Query query = dataRef.orderByChild("dateTime");
        //            TODO: query options, sort by dateTime

        FirebaseRecyclerOptions lfgOptions =
                new FirebaseRecyclerOptions.Builder<LFGPost>()
//                        .setQuery(query, LFGPost.class)
                        .setIndexedQuery(query, dataRef, LFGPost.class)
                        .build();

        mLFGPostAdapter = new LFGPostRecyclerAdapter(MainActivity.this, lfgOptions);
        mLFGRecyclerView.setAdapter(mLFGPostAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mLFGPostAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLFGPostAdapter.stopListening();
    }

    //    @Override
//    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1) {
//            if(resultCode == NewLFGPostActivity.RESULT_OK){
////                String result=data.getStringExtra("result");
//                Snackbar.make(view, "Post submitted!", Snackbar.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }

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
            return true;
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
        //TODO: refresh access_token if expired
        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(redirectUri)) {
            final String code = uri.getQueryParameter("code");

            //Network logging - debug
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …


            // add logging as last interceptor
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
//                    TODO: store accessToken/refreshToken

                    System.out.println("accessToken: " + response.body().getAccessToken());
                    Toast.makeText(MainActivity.this, "Acquired access_token!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Couldn't acquire access token!", Toast.LENGTH_SHORT).show();
                }
            });

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
        } else if (id == R.id.nav_characters) {
            Intent accountCharacters = new Intent(this, UserCharactersActivity.class);
            startActivity(accountCharacters);
        }
// else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        else if (id == R.id.nav_log_in) {
            Intent oauthIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bungie.net/en/OAuth/Authorize" + "?client_id=" + clientId + "&response_type=code&redirect_uri=" +redirectUri));
            startActivity(oauthIntent);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {

//        mLFGPostAdapter.stopListening();
//        mLFGPostAdapter.notifyDataSetChanged();
        loadLFGPosts();
    }
}
