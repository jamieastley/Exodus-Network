package com.jastley.warmindfordestiny2.app;

import android.app.Application;

import com.jastley.warmindfordestiny2.components.AppComponent;


import com.jastley.warmindfordestiny2.components.DaggerAppComponent;
import com.jastley.warmindfordestiny2.modules.AppModule;
import com.jastley.warmindfordestiny2.modules.ContextModule;
import com.jastley.warmindfordestiny2.modules.RetrofitModule;
import com.jastley.warmindfordestiny2.modules.RoomModule;
import com.jastley.warmindfordestiny2.modules.SharedPrefsModule;

public class App extends Application {

    private static App app;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

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
