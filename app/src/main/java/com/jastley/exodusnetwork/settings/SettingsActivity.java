package com.jastley.exodusnetwork.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.jastley.exodusnetwork.MainActivityViewModel;
import com.jastley.exodusnetwork.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.settings_debug_recycler) RecyclerView mRecyclerView;

    private SettingsRecyclerAdapter mRecyclerAdapter;
    MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialiseRecyclerViews();
    }



    private void initialiseRecyclerViews() {
        mRecyclerAdapter = new SettingsRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setSettingsList(populateSettingsList());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());

        Disposable disposable = mRecyclerAdapter.getOnClickSubject()
                .subscribe(settingsModel -> {
                    Log.e("Settings_Activity", String.valueOf(settingsModel.getItemId()));

                    String text = settingsModel.getTitle();

                    switch(text) {

                        case "Update Bungie Manifest":
                            //TODO
                            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                            break;
                        case "View stored account data":
                            Toast.makeText(this, settingsModel.getTitle(), Toast.LENGTH_SHORT).show();
                            break;
                        case "Refresh account data":
                            refreshAccountData();
                            break;
                        case "Log Out":
                            Toast.makeText(this, settingsModel.getTitle(), Toast.LENGTH_SHORT).show();
                            break;
                    }

                });
    }

    private void refreshAccountData() {
//        mViewModel.refreshAccountData();
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this);
    }

    private List<SettingsModel> populateSettingsList() {
        List<SettingsModel> settingsList = new ArrayList<>();
        settingsList.add(new SettingsModel("Update Bungie Manifest", getString(R.string.settings_redownload_subtext)));
        settingsList.add(new SettingsModel("View stored account data", null));
        settingsList.add(new SettingsModel("Refresh account data", null));
        settingsList.add(new SettingsModel("Log out", null));

        return settingsList;
    }
}
