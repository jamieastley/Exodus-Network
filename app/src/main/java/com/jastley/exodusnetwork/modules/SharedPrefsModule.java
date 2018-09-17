package com.jastley.exodusnetwork.modules;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModule {

    private Context context;

    public SharedPrefsModule(Context context) {
        this.context = context;
    }

    @Provides
    @Named("savedPrefs")
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences("saved_prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Named("savedManifest")
    SharedPreferences provideSharedPreferencesManifest() {
        return context.getSharedPreferences("saved_manifest", Context.MODE_PRIVATE);
    }


}
