package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.onboarding.DownloadProgressModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

//@Singleton
public class OnBoardingRepository {

    private MutableLiveData<DownloadProgressModel> downloadProgressModel = new MutableLiveData<>();

    @Inject
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Context context;

//    @Inject
//    public OnBoardingRepository() {
//        App.getApp().getAppComponent().inject(this);
//    }

    public LiveData<DownloadProgressModel> getDownloadProgressModel() {
        return downloadProgressModel;
    }

    public void checkManifestVersion(){

        sharedPreferences = context.getSharedPreferences("saved_manifest", Context.MODE_PRIVATE);

        Disposable disposable = retrofit.create(BungieAPI.class).getBungieManifests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_getBungieManifest -> {

                    if(!response_getBungieManifest.getErrorCode().equals("1")){
//                        showErrorDialog("An error occurred", response_getBungieManifest.getMessage());
                        downloadProgressModel.postValue(new DownloadProgressModel(null, null, response_getBungieManifest.getMessage()));
                    }
                    else {
                        String manifestVersion = response_getBungieManifest.getResponse().getMobileWorldContentPaths().getEnglishPath();
                        String savedManifestVersion = sharedPreferences.getString("manifestVersion", "");

                        //Download/update stored manifests
                        if (!manifestVersion.equals(savedManifestVersion)) {

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            try {
                                editor.putString("manifestVersion", manifestVersion);
                                editor.apply();
                            } catch (Exception e) {
                                Log.e("MANIFEST_PREFS_ERR", e.getLocalizedMessage());
                            }
//                            splashText.setText(R.string.gettingItemDatabase);
//                            String contentUrl = response_getBungieManifest.getResponse().getMobileWorldContentPaths().getEnglishPath();
//                            getUpdateManifests(contentUrl);
                        } else { //already have the latest manifest

//                            intent = new Intent(OnBoardingActivity.this, MainActivity.class);
//
//                            //set flags so pressing back won't trigger launching splash screen again
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                            //dispose of observables
//                            compositeDisposables.dispose();
//                            startActivity(intent);
//                            finish();
                        }
                    }
                });
    }
}
