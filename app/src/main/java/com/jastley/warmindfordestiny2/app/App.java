package com.jastley.warmindfordestiny2.app;

import android.app.Application;


import com.jastley.warmindfordestiny2.components.DaggerRetrofitComponent;
import com.jastley.warmindfordestiny2.components.RetrofitComponent;
import com.jastley.warmindfordestiny2.modules.AppModule;
import com.jastley.warmindfordestiny2.modules.RetrofitModule;

public class App extends Application {

    private RetrofitComponent mRetrofitComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofitComponent = DaggerRetrofitComponent.builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public RetrofitComponent getRetrofitComponent() {
        return mRetrofitComponent;
    }
}
