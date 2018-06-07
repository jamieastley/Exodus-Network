package com.jastley.warmindfordestiny2.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Utils.NoNetworkException;
import com.jastley.warmindfordestiny2.Utils.fragments.ErrorDialogFragment;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

/**
 * Created by jamie on 7/4/18.
 */

public class SplashScreenActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposables = new CompositeDisposable();

    @BindView(R.id.splash_icon) ImageView splashIcon;
    @BindView(R.id.splash_text) TextView splashText;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);


        //Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //Animate splash logo
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(splashIcon, View.ALPHA, 0f, 1f);
        alphaAnimator.setDuration(2000);
        alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
        alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
        alphaAnimator.start();

        splashText.setText(R.string.checkingManifest);

        checkManifestsVersion();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkManifestsVersion() {

        SharedPreferences savedPrefs;
        savedPrefs = this.getSharedPreferences("saved_prefs", Context.MODE_PRIVATE);

        BungieAPI mBungieAPI = RetrofitHelper.getBungieAPI(baseURL, this);
        Disposable manifestDisposable =  mBungieAPI.getBungieManifests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_getBungieManifest -> {

                    if(!response_getBungieManifest.getErrorCode().equals("1")){
                        showErrorDialog("An error occurred", response_getBungieManifest.getMessage());
                    }
                    else {
                        String manifestVersion = response_getBungieManifest.getResponse().getVersion();
                        String savedManifestVersion = savedPrefs.getString("manifestVersion", "");

                        //Download/update stored manifests
                        if (!manifestVersion.equals(savedManifestVersion)) {

                            SharedPreferences.Editor editor = savedPrefs.edit();

                            try {
                                editor.putString("manifestVersion", manifestVersion);
                                editor.apply();
                            } catch (Exception e) {
                                Log.e("MANIFEST_PREFS_ERR", e.getLocalizedMessage());
                            }
                            splashText.setText(R.string.gettingItemDatabase);
                            String contentUrl = response_getBungieManifest.getResponse().getMobileWorldContentPaths().getEnglishPath();
                            getUpdateManifests(contentUrl);
                        } else { //already have the latest manifest

                            intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                            //set flags so pressing back won't trigger launching splash screen again
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            //dispose of observables
                            compositeDisposables.dispose();
                            startActivity(intent);
                            finish();
                        }
                    }
                }, error -> {
                    Log.e("CHECK_MANIFEST_ERROR", error.getLocalizedMessage());
                    if(error instanceof NoNetworkException){
                        showErrorDialog("No network detected", "An active internet connection is required!");
                    }
                });
        compositeDisposables.add(manifestDisposable);
    }

    private void getUpdateManifests(String url){

        BungieAPI mBungieAPI = RetrofitHelper.getBungieAPI(baseURL, this);

        Disposable disposable = mBungieAPI.downloadUrlContent(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(responseBody -> {

                    try {

                        //dynamically retrieve the /data/databases path for the device, database filename here is irrelevant
                        String databasePath = this.getDatabasePath("bungieAccount.db").getParent();
                        File manifestFile = new File(databasePath, "manifest.zip");

                        InputStream inputStream = null;
                        OutputStream outputStream = null;

                        try {
                            byte[] fileReader = new byte[4096];

                            long fileSize = responseBody.contentLength();
                            long fileSizeDownloaded = 0;

                            inputStream = responseBody.byteStream();
                            outputStream = new FileOutputStream(manifestFile);

                            boolean test = true;
                            while (test) {
                                int read = inputStream.read(fileReader);

                                if (read == -1) {
                                    break;
                                }

                                outputStream.write(fileReader, 0, read);
                                fileSizeDownloaded += read;
                                Log.d("Manifest Download", fileSizeDownloaded + " of " + fileSize);
                                outputStream.flush();
                            }

                        } catch (IOException e) {
                            Log.d("Content download: ", e.getLocalizedMessage());
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }

                            if (outputStream != null) {
                                outputStream.close();
                            }
                            unzipManifest(databasePath);
                        }
                    }
                    catch(IOException e){
                        Log.d("OUTER_CATCH", e.getLocalizedMessage());
                    }

                    }, throwable -> {
                        Log.e("GET_UPDATE_MANIFESTS_ER", throwable.getLocalizedMessage());

                    });

        compositeDisposables.add(disposable);

    }

    private void unzipManifest(String path){

        InputStream is;
        ZipInputStream zis;
        try
        {
            String zipname = "manifest.zip";
            String filename;
            File bungieDB = new File(path, "bungieAccount.db");
            is = new FileInputStream(path +"/"+ zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                filename = ze.getName();

                // Create directory if it doesn't exist
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(bungieDB);

                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally {
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);

            //dispose of observables
            compositeDisposables.dispose();

            //set flags so pressing back won't trigger launching splash screen again
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void showErrorDialog(String title, String message) {
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(title, message, (dialog, which) -> checkManifestsVersion());
        fragment.show(getFragmentManager(), "errorDialog");
    }
}
