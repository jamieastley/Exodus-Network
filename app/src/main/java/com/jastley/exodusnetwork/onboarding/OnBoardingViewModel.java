package com.jastley.exodusnetwork.onboarding;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.OnBoardingRepository;

import javax.inject.Inject;

public class OnBoardingViewModel extends AndroidViewModel {

    @Inject
    OnBoardingRepository onBoardingRepository;

    public OnBoardingViewModel(@NonNull Application application) {
        super(application);

        App.getApp().getAppComponent().inject(this);
    }

    public LiveData<DownloadProgressModel> checkManifest() {
        onBoardingRepository.checkManifestVersion();
        return onBoardingRepository.getDownloadProgressModel();
    }

    public void dispose() {
        onBoardingRepository.dispose();
    }
}
