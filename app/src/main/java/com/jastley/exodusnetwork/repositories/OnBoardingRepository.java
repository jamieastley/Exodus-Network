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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class OnBoardingRepository {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<DownloadProgressModel> downloadProgressModel = new MutableLiveData<>();

    @Inject
    @Named("bungieRetrofit")
    Retrofit retrofit;

    @Inject
    Gson gson;

    @Inject
    @Named("savedManifest")
    SharedPreferences sharedPreferences;

    @Inject
    Context context;

    @Inject
    public OnBoardingRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<DownloadProgressModel> getDownloadProgressModel() {
        return downloadProgressModel;
    }

    public void checkManifestVersion(){

        Disposable disposable = retrofit.create(BungieAPI.class).getBungieManifests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_getBungieManifest -> {

                    if(!response_getBungieManifest.getErrorCode().equals("1")){
                        downloadProgressModel.postValue(new DownloadProgressModel(response_getBungieManifest.getMessage()));
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
                            String contentUrl = response_getBungieManifest.getResponse().getMobileWorldContentPaths().getEnglishPath();
                            getUpdateManifests(contentUrl);
                        } else { //already have the latest manifest
                            downloadProgressModel.postValue(new DownloadProgressModel(true));
                        }
                    }
                }, throwable -> downloadProgressModel.postValue(new DownloadProgressModel(throwable)));
        compositeDisposable.add(disposable);
    }

    private void getUpdateManifests(String url){

        Disposable disposable = retrofit.create(BungieAPI.class).downloadUrlContent(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(responseBody -> {

                    try {

                        //dynamically retrieve the /data/databases path for the device, database filename here is irrelevant
                        String databasePath = context.getDatabasePath("bungieAccount.db").getParent();
                        File manifestFile = new File(databasePath, "manifest.zip");

                        InputStream inputStream = null;
                        OutputStream outputStream = null;

                        try {
                            byte[] fileReader = new byte[4096];

                            long fileSize = responseBody.contentLength();
                            long fileSizeDownloaded = 0;

                            inputStream = responseBody.byteStream();
                            outputStream = new FileOutputStream(manifestFile);

                            boolean downloading = true;
                            while (downloading) {
                                int read = inputStream.read(fileReader);

                                if (read == -1) {
                                    break;
                                }

                                outputStream.write(fileReader, 0, read);
                                fileSizeDownloaded += read;

                                DecimalFormat df = new DecimalFormat("###");
                                df.setRoundingMode(RoundingMode.CEILING);
                                int downloaded = Integer.parseInt(df.format(fileSizeDownloaded));
                                int size = Integer.parseInt(df.format(fileSize));
                                downloadProgressModel.postValue(new DownloadProgressModel(downloaded, size, "Downloading manifest..."));
                                Log.d("Manifest Download", fileSizeDownloaded + " of " + fileSize);
                                outputStream.flush();
                            }

                        } catch (IOException e) {
                            Log.d("Content download: ", e.getLocalizedMessage());
                            downloadProgressModel.postValue(new DownloadProgressModel(e.getCause()));
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
        compositeDisposable.add(disposable);
    }

    private void unzipManifest(String path){

        downloadProgressModel.postValue(new DownloadProgressModel(100,100, "Extracting manifest data..."));

        InputStream is;
        ZipInputStream zis;
        try
        {
            String zipname = "manifest.zip";
            String filename;
            File bungieDB = new File(path, "bungieManifest.db");
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
            downloadProgressModel.postValue(new DownloadProgressModel(e.getCause()));
        }
        finally {
            downloadProgressModel.postValue(new DownloadProgressModel(true));
        }
    }

    public void dispose() {
        compositeDisposable.dispose();
    }
}
