package com.jastley.warmindfordestiny2.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.database.GetItemDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jamie on 7/4/18.
 */

public class SplashScreenActivity extends AppCompatActivity {

//    implements GetItemDatabase.AsyncResponse

    @BindView(R.id.splash_icon) ImageView splashIcon;
    @BindView(R.id.splash_text) TextView splashText;
    private static final int SPLASH_TIME = 2000;
//    SharedPreferences savedPrefs;
//    Boolean firstRun;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

//        savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
//        firstRun = savedPrefs.getBoolean("firstRun", true);
        intent = new Intent(SplashScreenActivity.this, MainActivity.class);

        //set flags so pressing back won't trigger launching splash screen again
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //Animate splash logo
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(splashIcon, View.ALPHA, 0f, 1f);
        alphaAnimator.setDuration(2000);
        alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
        alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
        alphaAnimator.start();

//        if(firstRun){
            splashText.setText(R.string.gettingItemDatabase);
//            asyncGetItemDatabase();
//        }
//        else {
//            try {
//                Thread.sleep(SPLASH_TIME);
////                startActivity(intent);
////                finish();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        asyncBackground();
//        asyncGetItemDatabase();

        GetItemDatabase items = new GetItemDatabase(new GetItemDatabase.AsyncResponse() {
            @Override
            public void onAsyncDone() {
                startActivity(intent);
                finish();
            }
        });

        items.execute(this);

    }

//    private void asyncGetItemDatabase() {
//        new GetItemDatabase(this).execute(this);
//    }
//
////    private void test() {
////        new GetItemDatabase().execute(this);
////    }
//
//    @Override
//    public void onAsyncDone(Boolean mBool) {
//
//
//
//
////        getSharedPreferences("saved_prefs", Activity.MODE_PRIVATE);
////        SharedPreferences.Editor editor = savedPrefs.edit();
////
////        try{
////            editor.putBoolean("firstRun", false);
////            editor.apply();
////        }
////        catch(Exception e){
////            System.out.println("onCompleteSplash: " + e);
////        }
//    }
}
