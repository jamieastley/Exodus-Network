package com.jastley.exodusnetwork.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Utils.SingleLiveEvent;
import com.jastley.exodusnetwork.Utils.SnackbarMessage;
import com.jastley.exodusnetwork.api.BungieAPI;
import com.jastley.exodusnetwork.api.models.Response_GetProfileOverview.ProgressionsData;
import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.database.AppManifestDatabase;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.jastley.exodusnetwork.Definitions.gloryProgressionHash;
import static com.jastley.exodusnetwork.Definitions.infamyProgressionHash;
import static com.jastley.exodusnetwork.Definitions.valorProgressionHash;

@Singleton
public class OverviewRepository {

    private MutableLiveData<ProgressionsData> gloryProgression = new MutableLiveData<>();
    private MutableLiveData<ProgressionsData> valorProgression = new MutableLiveData<>();
    private MutableLiveData<ProgressionsData> infamyProgression = new MutableLiveData<>();

    private SingleLiveEvent<SnackbarMessage> snackbarMessage = new SingleLiveEvent<>();

    @Inject
    @Named("bungieRetrofit")
    Retrofit retrofit;

    @Inject
    @Named("savedPrefs")
    SharedPreferences sharedPreferences;

    @Inject
    Context context;

    @Inject
    public OverviewRepository() {
        App.getApp().getAppComponent().inject(this);
    }

    public void getProfileOverview() {

        String mType = sharedPreferences.getString("selectedPlatform", "");
        String mId = sharedPreferences.getString("membershipId" + mType, "");
        String cId = sharedPreferences.getString(mType+"characterId0", "");

        if(!mId.equals("")) {

            Disposable disposable = retrofit.create(BungieAPI.class).getProfileProgressions(mType, mId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(progressions -> {

                        if(!progressions.getErrorCode().equals("1")) {
                            snackbarMessage.postValue(new SnackbarMessage(progressions.getMessage()));
                        }
                        else {
                            //These progression stats are the same on each character (account-wide), just grab from first character in list
                            String key = progressions.getResponse().getCharacterProgressions().getData().keySet().iterator().next();
                            gloryProgression.postValue(progressions.getResponse().getCharacterProgressions().getData().get(key).getProgressionsData().get(gloryProgressionHash));
                            valorProgression.postValue(progressions.getResponse().getCharacterProgressions().getData().get(key).getProgressionsData().get(valorProgressionHash));
                            infamyProgression.postValue(progressions.getResponse().getCharacterProgressions().getData().get(key).getProgressionsData().get(infamyProgressionHash));
                        }

                    }, throwable -> snackbarMessage.postValue(new SnackbarMessage(throwable)));

        } else {
            snackbarMessage.postValue(new SnackbarMessage(context.getResources().getString(R.string.profileProgressionError)));
        }
    }

    public MutableLiveData<ProgressionsData> getGloryProgression() {
        return gloryProgression;
    }

    public MutableLiveData<ProgressionsData> getValorProgression() {
        return valorProgression;
    }

    public MutableLiveData<ProgressionsData> getInfamyProgression() {
        return infamyProgression;
    }

    public SingleLiveEvent<SnackbarMessage> getSnackbarMessage() {
        return snackbarMessage;
    }
}
