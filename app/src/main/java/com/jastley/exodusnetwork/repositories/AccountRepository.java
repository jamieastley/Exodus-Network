package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.jastley.exodusnetwork.BuildConfig;
import com.jastley.exodusnetwork.Utils.SnackbarMessage;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.AppDatabase;
import com.jastley.exodusnetwork.database.dao.AccountDAO;
import com.jastley.exodusnetwork.database.models.Account;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.single.SingleToObservable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;
import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

@Singleton
public class AccountRepository {

    private MutableLiveData<SnackbarMessage> snackbarMessage = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> platformSelector = new MutableLiveData<>();
    private MutableLiveData<EmblemIconDownload> emblemDownloadProgress = new MutableLiveData<>();

    @Inject
    @Named("OAuthRetrofit")
    Retrofit tokenRetrofit;

    @Inject
    @Named("bungieAuthRetrofit")
    Retrofit authRetrofit;

    @Inject
    Gson gson;

    @Inject
    Context context;

    @Inject
    @Named("savedPrefs")
    SharedPreferences sharedPreferences;

    @Inject
    @Named("Account")
    AppDatabase accountDatabase;

    @Inject
    public AccountRepository() {
        App.getApp().getAppComponent().inject(this);
    }


    public void getAccessToken(String code) {

        Disposable disposable = tokenRetrofit.create(BungieAPI.class).getAccessToken(
                BuildConfig.clientId,
                BuildConfig.clientSecret,
                "authorization_code",
                code)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(accessToken -> {

                    try {
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("access_token", accessToken.getAccessToken());
                        editor.putString("refresh_token", accessToken.getRefreshToken());
                        editor.putLong("token_age", System.currentTimeMillis());
                        editor.apply();

                        //TODO delete this line
                        Log.d(AccountRepository.class.getSimpleName(), accessToken.getAccessToken());

                        getAccountData();
                    }
                    catch(Exception e) {
                        Log.e("ACCESS_TOKEN", e.getLocalizedMessage());
                        snackbarMessage.postValue(new SnackbarMessage(e.getLocalizedMessage()));
                    }

                }, throwable -> snackbarMessage.postValue(new SnackbarMessage(throwable)));
    }

    public void refreshAccessToken() {

        String refreshToken = sharedPreferences.getString("refresh_token", "");

        if(!refreshToken.equals("")){

            snackbarMessage.postValue(new SnackbarMessage("Refreshing Authorization.."));

            Disposable disposable = tokenRetrofit.create(BungieAPI.class).renewAccessToken(
                BuildConfig.clientId,
                BuildConfig.clientSecret,
                "refresh_token",
                refreshToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(accessToken -> {

                    try {
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("access_token", accessToken.getAccessToken());
                        editor.putString("refresh_token", accessToken.getRefreshToken());
                        editor.putLong("token_age", System.currentTimeMillis());
                        editor.apply();

                        snackbarMessage.postValue(new SnackbarMessage("Authorization refreshed"));
                    }
                    catch(Exception e) {
                        Log.e("ACCESS_TOKEN", e.getLocalizedMessage());
                        snackbarMessage.postValue(new SnackbarMessage(e.getLocalizedMessage()));
                    }

                }, throwable -> snackbarMessage.postValue(new SnackbarMessage(throwable)));
        }

    }

    public boolean checkIsTokenExpired() {

        Long timestamp = sharedPreferences.getLong("token_age", 0);

        try {
            timestamp = sharedPreferences.getLong("token_age", 0);
        }
        catch (Exception e) {
            Log.e("CHECK_TOKEN_EXPIRED", e.getLocalizedMessage());
        }
        if (timestamp != 0) {
            Long hour = 60L * 60L * 1000L;
            Long now = System.currentTimeMillis();
            Long timespan = now - timestamp;

            if (timespan > hour) {

                return true;

            }
            else {
                return false;
            }
        }
        return true;
    }

    public void getAccountData() {

        Disposable disposable = authRetrofit.create(BungieAPI.class).getMembershipsCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(currentUser -> {

                    if(!currentUser.getErrorCode().equals("1")) {
                        //TODO error handling
                        snackbarMessage.postValue(new SnackbarMessage(currentUser.getMessage()));
                    }
                    else {

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int membershipsCount = currentUser.getResponse().getDestinyMemberships().size();

                        //initialise string array based on number of memberships
//                        String[] memberships = new String[membershipsCount];
                        ArrayList<String> membershipTypes = new ArrayList<>();

                        //store all found membership details
                        for (int i = 0; i < membershipsCount; i++) {

                            try {

                                //current membershipType within arrayList (eg. 2 == PSN)
                                String mId = currentUser.getResponse().getDestinyMemberships().get(i).getMembershipType();
                                membershipTypes.add(mId);

//                                memberships[i] = currentUser.getResponse().getDestinyMemberships().get(i).getMembershipType();

                                editor.putString("membershipType" + mId, mId);
                                editor.putString("membershipId" + mId, currentUser.getResponse().getDestinyMemberships().get(i).getMembershipId());

                                //sanitise BattleNet displayName for Firebase
                                if(currentUser.getResponse().getDestinyMemberships().get(i).getMembershipType().equals("4")){

                                    String displayName = currentUser.getResponse().getBungieNetUser().getBlizzardDisplayName();
                                    if(displayName.contains("#")){
                                        displayName = displayName.replace("#", "%23");
                                        editor.putString("displayName" + mId, displayName);
                                    }
                                }
                                //PlayStation/Xbox players
                                else {
                                    editor.putString("displayName" + mId, currentUser.getResponse().getDestinyMemberships().get(i).getDisplayName());
                                }
                                editor.apply();
                            }
                            catch(Exception e){
                                Log.d("GET_CURRENT_USER_PARSE", e.getLocalizedMessage());

                                //TODO error handling
                                snackbarMessage.postValue(new SnackbarMessage(e.getLocalizedMessage()));
                            }
                        }


                        //ask user to select which platform they want to use
                        if(membershipsCount > 1) {

//                            for (int i = 0; i < membershipsCount; i++) {
//                                memberships[i] = currentUser.getResponse().getDestinyMemberships().get(i).getMembershipType();
//                            }

                            //notify Activity/fragment to show platformDialogFragment with these options
                            platformSelector.postValue(membershipTypes);
                        }

                        //only one active platform
                        else {
                            String membershipType = membershipTypes.get(0);
//                            editor = savedPrefs.edit();
                            editor.putString("selectedPlatform", membershipType);
                            editor.apply();

                            getAllCharacters(membershipType);
                        }

                    }

                }, throwable -> snackbarMessage.postValue(new SnackbarMessage(throwable)));
    }

    public void getAllCharacters(String mType) {

        String mId = sharedPreferences.getString("membershipId" + mType, "");

        Disposable disposable = authRetrofit.create(BungieAPI.class).getAllCharactersGson(
                mType,
                mId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(allCharacters -> {

                    if(!allCharacters.getErrorCode().equals("1")) {
                        //TODO
                    }
                    else {

                        List<Account> accountList = new ArrayList<>();
                        List<String> emblemIconList = new ArrayList<>();
                        int counter = 0;

                        try{
                            //iterate through each character found
                            for (String key : allCharacters.getResponse().getCharacters().getData().keySet()) {

                                String characterData = gson.toJson(allCharacters.getResponse().getCharacters().getData().get(key));

                                //CharacterId
                                //Create Room object for each character under selected platform
                                Account account = new Account();
                                account.setKey(mType + "character" + counter);
                                account.setValue(characterData);
                                accountList.add(account);

                                try {
                                    String emblemIcon = allCharacters.getResponse().getCharacters().getData().get(key).getEmblemPath();
                                    String emblemBackground = allCharacters.getResponse().getCharacters().getData().get(key).getEmblemBackgroundPath();
                                    emblemIconList.add(emblemIcon);

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(mType + "emblemIcon" + counter, emblemIcon);
                                    editor.putString(mType + "emblemBackgroundPath" + counter, emblemBackground);
                                    editor.apply();

                                } catch (Exception e) {
                                    Crashlytics.log(1, "GET_ALL_CHARS_EMBLEMS", e.getLocalizedMessage());
                                    snackbarMessage.postValue(new SnackbarMessage("Couldn't retrieve character data"));
                                }

                                counter++;
                            }

                            //write all characters' data to Room
                            accountDatabase.getAccountDAO().insertAll(accountList);

//                            //Download each icon to storage for use later
//                            downloadEmblems(emblemIconList);
                        } catch(Exception e) {
                            Crashlytics.log(1, "GET_ALL_CHARACTERS", e.getLocalizedMessage());
                            snackbarMessage.postValue(new SnackbarMessage("Couldn't retrieve character data"));
                        }

                        //Download each icon to storage for use later
                        //Get back onto mainThread for Picasso
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        Runnable mRunnable = () -> {
                            downloadEmblems(emblemIconList);
                        };
                        mainHandler.post(mRunnable);
//                        downloadEmblems(emblemIconList);
                    }

                }, throwable -> snackbarMessage.postValue(new SnackbarMessage(throwable.getMessage())));
    }

    private void downloadEmblems(List<String> urls) {

        for(String iconUrl : urls) {

            int pos = urls.indexOf(iconUrl);

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try{
                        File dir = context.getDir("emblems", MODE_PRIVATE);
                        if(!dir.exists()){
                            dir.mkdir();
                        }

                        File path = new File(dir, pos +".jpeg");
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

                        Log.d("EMBLEM_DOWNLOADED", String.valueOf(pos));

                        //All icons have been downloaded
                        if(pos == urls.size() -1){
//                                    targets = null;

//                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
////
//                                    //set flags so pressing back won't trigger previous state of MainActivity
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                    finish();
                            emblemDownloadProgress.postValue(new EmblemIconDownload(true));

                        }
                    }
                    catch(Exception e){
                        Log.d("EMBLEM_DOWNLOAD", e.getLocalizedMessage());
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.d("PICASSO_EMBLEM_DOWNLOAD", e.getLocalizedMessage());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.get()
                    .load(baseURL + iconUrl)
                    .into(target);
        }
    }

    public void logOut() {

        AsyncTask.execute(accountDatabase.getAccountDAO()::deleteAccount);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public class EmblemIconDownload {

        private boolean isComplete;

        public EmblemIconDownload(boolean isComplete) {
            this.isComplete = isComplete;
        }

        public boolean isComplete() {
            return isComplete;
        }
    }

    public MutableLiveData<SnackbarMessage> getSnackbarMessage() {
        return snackbarMessage;
    }

    public MutableLiveData<ArrayList<String>> getPlatformSelector() {
        return platformSelector;
    }

    public LiveData<EmblemIconDownload> getEmblemDownloadProgress() {
        return emblemDownloadProgress;
    }

    public void writeToSharedPrefs(String key, String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.apply();
    }
}
