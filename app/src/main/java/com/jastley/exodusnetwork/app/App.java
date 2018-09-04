package com.jastley.exodusnetwork.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.jastley.exodusnetwork.components.AppComponent;


import com.jastley.exodusnetwork.components.DaggerAppComponent;
import com.jastley.exodusnetwork.modules.AppModule;
import com.jastley.exodusnetwork.modules.ContextModule;
import com.jastley.exodusnetwork.modules.RetrofitModule;
import com.jastley.exodusnetwork.modules.RoomModule;
import com.jastley.exodusnetwork.modules.SharedPrefsModule;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

    private static App app;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        app = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule())
                .roomModule(new RoomModule(this))
                .sharedPrefsModule(new SharedPrefsModule(getApplicationContext()))
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

//        DaggerAppComponent.builder().application(this).build.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static App getApp() {
        return app;
    }
}
