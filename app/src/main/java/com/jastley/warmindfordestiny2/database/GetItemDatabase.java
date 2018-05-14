package com.jastley.warmindfordestiny2.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.models.Collectables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jastley.warmindfordestiny2.database.models.Factions;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;
import static com.jastley.warmindfordestiny2.api.BungieAPI.plumbingURL;

/**
 * Created by jamie1192 on 6/4/18.
 */

public class GetItemDatabase extends AsyncTask<Context, Void, Boolean> {

    private List<Collectables> collectables = new ArrayList<>();

    public interface AsyncResponse {
        void onAsyncDone();
    }

    public AsyncResponse delegate = null;

    public GetItemDatabase(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Boolean doInBackground(Context... contexts) {

        final Context context = contexts[0];

        SharedPreferences savedPrefs;
        savedPrefs = context.getSharedPreferences("saved_prefs", Context.MODE_PRIVATE);
        Boolean firstRun = savedPrefs.getBoolean("firstRun", true);



//        if(!firstRun){

            //get/check manifest version data
        BungieAPI mBungieAPI = new RetrofitHelper().getBungieAPI(baseURL);

            mBungieAPI.getDestinyPlumbing()
                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response_destinyPlumbing -> {

                        String manifestVersion = response_destinyPlumbing.getBungieManifestVersion();
                        String savedManifestVersion = savedPrefs.getString("manifestVersion", "");

                        if(!manifestVersion.equals(savedManifestVersion)){

                            //save new manifestVersion
                            SharedPreferences.Editor editor = savedPrefs.edit();

                            try{
                                editor.putString("manifestVersion", manifestVersion);
                                editor.apply();
                            }
                            catch(Exception e){
                                System.out.println("ManifestVersion error: " + e);
                            }

                            final CollectablesDAO mCollectibleDAO = AppDatabase.getAppDatabase(context).getCollectablesDAO();

                            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


                            Retrofit.Builder builder = new Retrofit.Builder()
                                    .baseUrl("https://destiny.plumbing/en/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(httpClient.build());

                            Retrofit retrofit = builder.build();

                            BungieAPI bungieClient = retrofit.create(BungieAPI.class);
                            Call<JsonElement> getCollectablesManifest = bungieClient.getCollectablesDatabase();

                            getCollectablesManifest.enqueue(new Callback<JsonElement>() {
                                @Override
                                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                                    JsonElement json = response.body();
                                    JsonObject responseObj = (JsonObject) json;

                                    try{
                                        for(Iterator iterator = responseObj.keySet().iterator(); iterator.hasNext();) {

                                            final String key = (String)iterator.next();
                                            JsonObject collectableObj = (JsonObject) responseObj.get(key);

                                            //store item definition object as string
                                            final String currentItemDefinition = collectableObj.toString();

                                            Collectables mCollectable = new Collectables();
                                            mCollectable.setKey(key);
                                            mCollectable.setValue(currentItemDefinition);
                                            collectables.add(mCollectable);

                                            //onResponse is on UI thread, move back onto worker thread for Room
//                                AsyncTask.execute(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Collectables collectable = new Collectables();
//                                        collectable.setKey(key);
//                                        collectable.setValue(currentItemDefinition);
//
//                                        mCollectibleDAO.insert(collectable);
//
//                                    }
//                                });
                                        }

                                        AsyncTask.execute(() -> mCollectibleDAO.insertAll(collectables));
                                    }
                                    catch(Exception e){
                                        System.out.println("Error getting manifest: " + e);
                                    }
                                    finally {
//                                        delegate.onAsyncDone();

                                        BungieAPI mBungieFactionAPI = new RetrofitHelper().getBungieAPI(plumbingURL);

                                        mBungieFactionAPI.getFactionDefinitions()
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(factionResponse -> {

                                                    List<Factions> factionList = new ArrayList<>();

                                                    JsonObject factionResponseObj = (JsonObject) factionResponse;



                                                    for(Iterator iterator = factionResponseObj.keySet().iterator(); iterator.hasNext();){

                                                        final String key = (String)iterator.next();

//                                                        Response_FactionDefinitions.FactionObject faction = gson.fromJson(responseObj.get(key).getAsJsonObject(), Response_FactionDefinitions.FactionObject.class);
                                                        JsonObject singleFactionObj = (JsonObject) factionResponseObj.get(key);

                                                        String factionDetails = singleFactionObj.toString();

                                                        Factions factionModel = new Factions();

                                                        factionModel.setKey(key);
                                                        factionModel.setValue(factionDetails);
                                                        factionList.add(factionModel);
                                                    }
//                                                    final CollectablesDAO mCollectibleDAO = AppDatabase.getAppDatabase(context).getCollectablesDAO();
                                                    System.out.println(response);
                                                    FactionsDAO mFactionsDAO = AppDatabase.getAppDatabase(context).getFactionsDAO();

                                                    mFactionsDAO.insertAll(factionList);
                                                    delegate.onAsyncDone();

                                                }, error -> {
                                                    System.out.println("something");
                                                });
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonElement> call, Throwable t) {
                                    Toast.makeText(context, "Couldn't update manifest database.", Toast.LENGTH_SHORT).show();
                                    delegate.onAsyncDone();
                                }
                            });

//            getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = savedPrefs.edit();
//
//                            try{
//                                editor.putBoolean("firstRun", false);
//                                editor.apply(); //TODO: UNCOMMENT THIS AFTER DEBUG
//                            }
//                            catch(Exception e){
//                                System.out.println("onCompleteSplash: " + e);
//                            }

                        }
                        else{
                            delegate.onAsyncDone();
                        }
                    });


//        }

        //if not first run
//        else{
//
//            //TODO: try pass intent jsonArray of character args to MainActivity
//            delegate.onAsyncDone();
//        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    //    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//
//        delegate.onComplete();
//    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

//          delegate.onAsyncDone(aBoolean);
    }

    public void getCollectables() {

    }

    public void getFactions() {

    }

}
