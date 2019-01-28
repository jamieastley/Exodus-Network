package com.jastley.exodusnetwork.onboarding;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Utils.fragments.ErrorDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jamie on 7/4/18.
 */

public class OnBoardingActivity extends AppCompatActivity {


    OnBoardingViewModel mViewModel;

    @BindView(R.id.splash_text) TextView splashText;
    @BindView(R.id.manifest_progress_bar) ProgressBar progressBar;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(OnBoardingViewModel.class);

        //Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        splashText.setText(R.string.checkingManifest);

        checkManifests();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mViewModel.dispose();
    }

    private void checkManifests() {

        mViewModel.checkManifest().observe(this, downloadProgressModel -> {
            if(downloadProgressModel.getThrowable() != null) {
                showErrorDialog("Error", downloadProgressModel.getThrowable().getLocalizedMessage());
            }
            else if(downloadProgressModel.getErrorMessage() != null) {
                showErrorDialog("Network Error", downloadProgressModel.getErrorMessage());
            }
            else if(downloadProgressModel.getMessage() != null && downloadProgressModel.getMessage().contains("Extracting")) {
                progressBar.setIndeterminate(true);
                splashText.setText(downloadProgressModel.getMessage());
            }
            else if(downloadProgressModel.getProgress() != 0) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(downloadProgressModel.getFileSize());
                progressBar.setProgress(downloadProgressModel.getProgress());
                splashText.setText(downloadProgressModel.getMessage());
            }
            else if(downloadProgressModel.isComplete()) {
                intent = new Intent(OnBoardingActivity.this, MainActivity.class);

                mViewModel.dispose();

//                //set flags so pressing back won't trigger launching splash screen again
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showErrorDialog(String title, String message) {
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(title, message, (dialog, which) -> mViewModel.checkManifest());
        fragment.show(getFragmentManager(), "errorDialog");
    }
}
