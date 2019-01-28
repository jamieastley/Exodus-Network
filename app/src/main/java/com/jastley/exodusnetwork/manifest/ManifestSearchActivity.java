package com.jastley.exodusnetwork.manifest;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jastley.exodusnetwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class ManifestSearchActivity extends AppCompatActivity {

    @BindView(R.id.manifest_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.manifest_activity_loading) ProgressBar progressBar;
    ManifestItemAdapter mRecyclerAdapter;
    ManifestViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(ManifestViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialiseRecycler();
    }

    private void initialiseRecycler() {
        mRecyclerAdapter = new ManifestItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mViewModel.itemList.observe(this, submitList -> {
            progressBar.setVisibility(View.GONE);
            mRecyclerAdapter.submitList(submitList);
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);

        Disposable disposable = mRecyclerAdapter.getOnClickSubject()
                .subscribe(itemHash -> {
                    Toast.makeText(this, itemHash, Toast.LENGTH_SHORT).show();
        });
    }

}
