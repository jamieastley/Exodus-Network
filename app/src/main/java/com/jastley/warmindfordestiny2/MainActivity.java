package com.jastley.warmindfordestiny2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jastley.warmindfordestiny2.LFG.LFGPost;
import com.jastley.warmindfordestiny2.LFG.LFGPostViewHolder;
import com.jastley.warmindfordestiny2.LFG.NewLFGPostActivity;
import com.jastley.warmindfordestiny2.User.LogInActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mLFGRecyclerView;
    private FirebaseRecyclerAdapter<LFGPost, LFGPostViewHolder> mLFGPostAdapter;

    View view;

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

        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference datetimeQuery = postRef.orderByChild("dateTime");
        Query query = postRef.child("lfg").orderByChild("dateTime");
//            TODO: query options, sort by dateTime

        FirebaseRecyclerOptions lfgOptions =
                new FirebaseRecyclerOptions.Builder<LFGPost>()
                        .setQuery(query, LFGPost.class)
                        .build();

        mLFGPostAdapter = new FirebaseRecyclerAdapter<LFGPost, LFGPostViewHolder>(lfgOptions) {
            @Override
            protected void onBindViewHolder(LFGPostViewHolder holder, int position, @NonNull LFGPost model) {
                holder.setActivityTitle(model.getActivityTitle());
                holder.setActivityCheckpoint(model.getActivityCheckpoint());
                holder.setPlatformIcon(model.getMembershipType(), getApplicationContext());
                holder.setClassType(model.getClassType());
                holder.setDisplayName(model.getDisplayName());
                holder.setLightLevel(model.getLightLevel());
                holder.setMicIcon(model.isHasMic(), getApplicationContext());
                holder.setDateTime(model.getDateTime());
            }

            @Override
            public LFGPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.lfg_list_item, parent, false);

                return new LFGPostViewHolder(view);
            }
        };

        mLFGRecyclerView.setAdapter(mLFGPostAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLFGPostAdapter.startListening();
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

        return super.onOptionsItemSelected(item);
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
