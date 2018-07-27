package com.jastley.exodusnetwork.modules;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModule {

    private Context context;

    public SharedPrefsModule(Context context) {
        this.context = context;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences("saved_prefs", Context.MODE_PRIVATE);
    }
}
